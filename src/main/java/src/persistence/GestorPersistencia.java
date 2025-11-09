package src.persistence;

import src.controller.Tarea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {
    private static final String PROPERTY_TAREAS = "todolist.tareas.csv";
    private static final String PROPERTY_TAREAS_BACKUP = "todolist.tareas.backup.csv";
    private static final String PROPERTY_HISTORIAL = "todolist.historial.log";
    private static final String DEFAULT_TAREAS = "tareas.csv";
    private static final String DEFAULT_TAREAS_BACKUP = "tareas_backup.csv";
    private static final String DEFAULT_HISTORIAL = "historial.log";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private GestorPersistencia() {
    }

    public static void guardarTareas(List<Tarea> tareas) {
        guardarTareasEnBaseDeDatos(tareas);
        guardarTareasEnArchivo(tareas, obtenerRutaArchivoPrincipal());
    }

    public static List<Tarea> cargarTareas() {
        List<Tarea> tareas = cargarTareasDesdeBaseDeDatos();
        if (tareas.isEmpty()) {
            tareas = cargarTareasDesdeArchivo();
            if (!tareas.isEmpty()) {
                guardarTareasEnBaseDeDatos(tareas);
            }
        } else {
            guardarTareasEnArchivo(tareas, obtenerRutaArchivoPrincipal());
        }
        return tareas;
    }

    public static void backupTareas() {
        List<Tarea> tareas = cargarTareasDesdeBaseDeDatos();
        if (tareas.isEmpty()) {
            tareas = cargarTareasDesdeArchivo();
        }
        guardarTareasEnArchivo(tareas, obtenerRutaArchivoBackup());
    }

    public static void registrarHistorial(String mensaje) {
        String linea = "%s - %s%n".formatted(LocalDateTime.now().format(FORMATO), mensaje);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(obtenerRutaHistorial(), true))) {
            writer.write(linea);
        } catch (IOException e) {
            System.out.println("Error al registrar historial: " + e.getMessage());
        }
    }

    private static void guardarTareasEnBaseDeDatos(List<Tarea> tareas) {
        String deleteSql = "DELETE FROM tareas";
        String insertSql = "INSERT INTO tareas (id, titulo, descripcion, completada) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement delete = connection.createStatement();
                 PreparedStatement insert = connection.prepareStatement(insertSql)) {
                delete.executeUpdate(deleteSql);
                for (Tarea tarea : tareas) {
                    insert.setInt(1, tarea.getId());
                    insert.setString(2, tarea.getTitulo());
                    insert.setString(3, tarea.getDescripcion());
                    insert.setInt(4, tarea.isCompletada() ? 1 : 0);
                    insert.addBatch();
                }
                insert.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar tareas en la base de datos: " + e.getMessage());
        }
    }

    private static List<Tarea> cargarTareasDesdeBaseDeDatos() {
        List<Tarea> tareas = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, titulo, descripcion, completada FROM tareas ORDER BY id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String descripcion = resultSet.getString("descripcion");
                boolean completada = resultSet.getInt("completada") == 1;
                Tarea tarea = new Tarea(id, titulo, descripcion);
                tarea.setCompletada(completada);
                tareas.add(tarea);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar tareas desde la base de datos: " + e.getMessage());
        }
        return tareas;
    }

    private static List<Tarea> cargarTareasDesdeArchivo() {
        List<Tarea> tareas = new ArrayList<>();
        File archivo = new File(obtenerRutaArchivoPrincipal());
        if (!archivo.exists()) {
            return tareas;
        }
        try {
            List<String> lineas = Files.readAllLines(archivo.toPath());
            for (String linea : lineas) {
                String[] partes = linea.split(",", -1);
                if (partes.length == 4) {
                    try {
                        int id = Integer.parseInt(partes[0].trim());
                        String titulo = partes[1].trim();
                        String descripcion = partes[2].trim();
                        boolean completada = Boolean.parseBoolean(partes[3].trim());
                        Tarea tarea = new Tarea(id, titulo, descripcion);
                        tarea.setCompletada(completada);
                        tareas.add(tarea);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar tareas desde archivo: " + e.getMessage());
        }
        return tareas;
    }

    private static void guardarTareasEnArchivo(List<Tarea> tareas, String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Tarea tarea : tareas) {
                String titulo = tarea.getTitulo() == null ? "" : tarea.getTitulo();
                String descripcion = tarea.getDescripcion() == null ? "" : tarea.getDescripcion();
                writer.write(tarea.getId() + "," +
                        titulo.replace(",", " ") + "," +
                        descripcion.replace(",", " ") + "," +
                        tarea.isCompletada());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar tareas en archivo: " + e.getMessage());
        }
    }

    private static String obtenerRutaArchivoPrincipal() {
        return System.getProperty(PROPERTY_TAREAS, DEFAULT_TAREAS);
    }

    private static String obtenerRutaArchivoBackup() {
        return System.getProperty(PROPERTY_TAREAS_BACKUP, DEFAULT_TAREAS_BACKUP);
    }

    private static String obtenerRutaHistorial() {
        return System.getProperty(PROPERTY_HISTORIAL, DEFAULT_HISTORIAL);
    }
}
