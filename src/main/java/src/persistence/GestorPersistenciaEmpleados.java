package src.persistence;

import src.controller.Empleado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistenciaEmpleados {
    private static final String PROPERTY_EMPLEADOS = "todolist.empleados.csv";
    private static final String PROPERTY_EMPLEADOS_BACKUP = "todolist.empleados.backup.csv";
    private static final String DEFAULT_EMPLEADOS = "empleados.csv";
    private static final String DEFAULT_EMPLEADOS_BACKUP = "empleados_backup.csv";

    private GestorPersistenciaEmpleados() {
    }

    public static void guardarEmpleados(List<Empleado> empleados) {
        String deleteSql = "DELETE FROM empleados";
        String insertSql = "INSERT INTO empleados (id, rol) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement delete = connection.createStatement();
                 PreparedStatement insert = connection.prepareStatement(insertSql)) {
                delete.executeUpdate(deleteSql);
                for (Empleado empleado : empleados) {
                    insert.setInt(1, empleado.getId());
                    insert.setString(2, empleado.getRol());
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
            System.out.println("Error al guardar empleados en la base de datos: " + e.getMessage());
        }
        guardarEmpleadosEnArchivo(empleados, obtenerRutaArchivoPrincipal());
    }

    public static List<Empleado> cargarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, rol FROM empleados ORDER BY id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String rol = resultSet.getString("rol");
                empleados.add(new Empleado(id, rol));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar empleados desde la base de datos: " + e.getMessage());
        }

        if (empleados.isEmpty()) {
            empleados = cargarEmpleadosDesdeArchivo();
            if (!empleados.isEmpty()) {
                guardarEmpleados(empleados);
            }
        } else {
            guardarEmpleadosEnArchivo(empleados, obtenerRutaArchivoPrincipal());
        }
        return empleados;
    }

    private static List<Empleado> cargarEmpleadosDesdeArchivo() {
        List<Empleado> empleados = new ArrayList<>();
        File archivo = new File(obtenerRutaArchivoPrincipal());
        if (!archivo.exists()) {
            return empleados;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", -1);
                if (partes.length >= 2) {
                    try {
                        int id = Integer.parseInt(partes[0].trim());
                        String rol = partes[1].trim().isEmpty() ? "empleado" : partes[1].trim();
                        empleados.add(new Empleado(id, rol));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar empleados desde archivo: " + e.getMessage());
        }
        return empleados;
    }

    public static void backupEmpleados() {
        List<Empleado> empleados = cargarEmpleados();
        guardarEmpleadosEnArchivo(empleados, obtenerRutaArchivoBackup());
    }

    private static void guardarEmpleadosEnArchivo(List<Empleado> empleados, String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Empleado empleado : empleados) {
                writer.write(empleado.getId() + "," + empleado.getRol());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar empleados en archivo: " + e.getMessage());
        }
    }

    private static String obtenerRutaArchivoPrincipal() {
        return System.getProperty(PROPERTY_EMPLEADOS, DEFAULT_EMPLEADOS);
    }

    private static String obtenerRutaArchivoBackup() {
        return System.getProperty(PROPERTY_EMPLEADOS_BACKUP, DEFAULT_EMPLEADOS_BACKUP);
    }
}
