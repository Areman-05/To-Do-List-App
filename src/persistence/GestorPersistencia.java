package src.persistence;

import src.controller.Tarea;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {
    private static final String ARCHIVO_TAREAS = "tareas.txt";

    // Método para guardar las tareas en un archivo
    public static void guardarTareas(List<Tarea> tareas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_TAREAS))) {
            for (Tarea tarea : tareas) {
                writer.write(tarea.getId() + "," + tarea.getTitulo() + "," + tarea.getDescripcion() + "," + tarea.isCompletada());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las tareas: " + e.getMessage());
        }
    }

    // Método para cargar las tareas desde un archivo
    public static List<Tarea> cargarTareas() {
        List<Tarea> tareas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_TAREAS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
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
            System.out.println("Error al cargar las tareas: " + e.getMessage());
        }
        return tareas;
    }
}