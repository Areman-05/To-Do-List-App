package src.controller;

import src.persistence.GestorPersistencia;
import java.util.List;

public class GestorTareas {
    private List<Tarea> tareas;

    // Constructor
    public GestorTareas() {
        this.tareas = GestorPersistencia.cargarTareas();
    }

    // Metodo para agregar una tarea
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        GestorPersistencia.guardarTareas(tareas);
    }

    // Metodo para eliminar una tarea por ID
    public void eliminarTarea(int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        GestorPersistencia.guardarTareas(tareas);
    }

    // Metodo para editar una tarea existente
    public void editarTarea(int id, String nuevoTitulo, String nuevaDescripcion) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tarea.setTitulo(nuevoTitulo);
                tarea.setDescripcion(nuevaDescripcion);
                break;
            }
        }
    }

    // Metodo para listar todas las tareas
    public void listarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas disponibles.");
        } else {
            for (Tarea tarea : tareas) {
                System.out.println(tarea);
            }
        }
    }

    // Metodo para marcar una tarea como completada
    public void marcarTareaComoCompletada(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tarea.setCompletada(true);
                System.out.println("Tarea " + id + " marcada como completada.");
                break;
            }
        }
    }
}
