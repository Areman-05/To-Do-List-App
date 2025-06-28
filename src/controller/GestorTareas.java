package src.controller;

import src.persistence.GestorPersistencia;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorTareas {
    private List<Tarea> tareas;

    public GestorTareas() {
        this.tareas = GestorPersistencia.cargarTareas();
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        GestorPersistencia.guardarTareas(tareas);
    }

    public void eliminarTarea(int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        GestorPersistencia.guardarTareas(tareas);
    }

    public boolean eliminarTareaPorTitulo(String titulo) {
        Iterator<Tarea> iterator = tareas.iterator();
        while (iterator.hasNext()) {
            Tarea tarea = iterator.next();
            if (tarea.getTitulo().equalsIgnoreCase(titulo)) {
                iterator.remove();
                GestorPersistencia.guardarTareas(tareas);
                return true;
            }
        }
        return false;
    }

    public boolean editarTituloPorNombre(String nombreActual, String nuevoTitulo) {
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equalsIgnoreCase(nombreActual)) {
                System.out.println("Título actual: " + tarea.getTitulo());
                tarea.setTitulo(nuevoTitulo);
                GestorPersistencia.guardarTareas(tareas);
                return true;
            }
        }
        return false;
    }

    public boolean editarDescripcionPorNombre(String nombreActual, String nuevaDescripcion) {
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equalsIgnoreCase(nombreActual)) {
                System.out.println("Descripción actual: " + tarea.getDescripcion());
                tarea.setDescripcion(nuevaDescripcion);
                GestorPersistencia.guardarTareas(tareas);
                return true;
            }
        }
        return false;
    }

    public void editarTarea(int id, String nuevoTitulo, String nuevaDescripcion) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (nuevoTitulo != null) {
                    System.out.println("Título actual: " + tarea.getTitulo());
                    tarea.setTitulo(nuevoTitulo);
                }
                if (nuevaDescripcion != null) {
                    System.out.println("Descripción actual: " + tarea.getDescripcion());
                    tarea.setDescripcion(nuevaDescripcion);
                }
                GestorPersistencia.guardarTareas(tareas);
                break;
            }
        }
    }

    public boolean editarEstadoPorId(int id, boolean completada) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (tarea.isCompletada() == completada) {
                    return false; // Ya está en el estado deseado
                }
                tarea.setCompletada(completada);
                GestorPersistencia.guardarTareas(tareas);
                return true;
            }
        }
        return false; // No encontrada
    }

    public boolean editarEstadoPorTitulo(String titulo, boolean completada) {
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equalsIgnoreCase(titulo)) {
                if (tarea.isCompletada() == completada) {
                    return false; // Ya está en el estado deseado
                }
                tarea.setCompletada(completada);
                GestorPersistencia.guardarTareas(tareas);
                return true;
            }
        }
        return false; // No encontrada
    }
    public void listarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas disponibles.");
        } else {
            for (Tarea tarea : tareas) {
                System.out.println(tarea);
            }
        }
    }

    public int generarNuevoId() {
        int maxId = 0;
        for (Tarea tarea : tareas) {
            if (tarea.getId() > maxId) {
                maxId = tarea.getId();
            }
        }
        return maxId + 1;
    }
}