package src.persistence;

import src.controller.Tarea;
import java.io.*;
import java.util.*;

public class GestorPersistencia {
    private static final String ARCHIVO_TAREAS = "tareas.csv";
    private static final String BACKUP_TAREAS = "tareas_backup.csv";

    public static void guardarTareas(List<Tarea> tareas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_TAREAS))) {
            for (Tarea tarea : tareas) {
                writer.write(tarea.getId() + "," +
                        tarea.getTitulo().replace(",", " ") + "," +
                        tarea.getDescripcion().replace(",", " ") + "," +
                        tarea.isCompletada());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar tareas: " + e.getMessage());
        }
    }

    public static List<Tarea> cargarTareas() {
        List<Tarea> tareas = new ArrayList<>();
        File archivo = new File(ARCHIVO_TAREAS);
        if (!archivo.exists()) return tareas;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", -1);
                if (partes.length == 4) {
                    int id = Integer.parseInt(partes[0]);
                    String titulo = partes[1];
                    String descripcion = partes[2];
                    boolean completada = Boolean.parseBoolean(partes[3]);
                    Tarea tarea = new Tarea(id, titulo, descripcion);
                    tarea.setCompletada(completada);
                    tareas.add(tarea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar tareas: " + e.getMessage());
        }
        return tareas;
    }

    public static void backupTareas() {
        File archivo = new File(ARCHIVO_TAREAS);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_TAREAS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al crear backup de tareas: " + e.getMessage());
        }
    }


    public static void registrarHistorial(String s) {
    }
}
