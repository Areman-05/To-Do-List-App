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
}