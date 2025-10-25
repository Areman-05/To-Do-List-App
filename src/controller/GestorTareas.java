package src.controller;

import src.persistence.GestorPersistencia;
import java.util.Comparator;
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
        GestorPersistencia.registrarHistorial("Tarea agregada: " + tarea.getTitulo());
    }

    public void eliminarTarea(int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        GestorPersistencia.guardarTareas(tareas);
        GestorPersistencia.registrarHistorial("Tarea eliminada por ID: " + id);
    }

    public boolean eliminarTareaPorTitulo(String titulo) {
        Iterator<Tarea> iterator = tareas.iterator();
        while (iterator.hasNext()) {
            Tarea tarea = iterator.next();
            if (tarea.getTitulo().equalsIgnoreCase(titulo)) {
                iterator.remove();
                GestorPersistencia.guardarTareas(tareas);
                GestorPersistencia.registrarHistorial("Tarea eliminada por título: " + titulo);
                return true;
            }
        }
        return false;
    }

    public boolean editarTituloPorNombre(String nombreActual, String nuevoTitulo) {
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equalsIgnoreCase(nombreActual)) {
                tarea.setTitulo(nuevoTitulo);
                GestorPersistencia.guardarTareas(tareas);
                GestorPersistencia.registrarHistorial("Título editado: '" + nombreActual + "' → '" + nuevoTitulo + "'");
                return true;
            }
        }
        return false;
    }

    public boolean editarDescripcionPorNombre(String nombreActual, String nuevaDescripcion) {
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equalsIgnoreCase(nombreActual)) {
                tarea.setDescripcion(nuevaDescripcion);
                GestorPersistencia.guardarTareas(tareas);
                GestorPersistencia.registrarHistorial("Descripción editada para tarea: " + nombreActual);
                return true;
            }
        }
        return false;
    }

    public void editarTarea(int id, String nuevoTitulo, String nuevaDescripcion) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (nuevoTitulo != null) {
                    tarea.setTitulo(nuevoTitulo);
                }
                if (nuevaDescripcion != null) {
                    tarea.setDescripcion(nuevaDescripcion);
                }
                GestorPersistencia.guardarTareas(tareas);
                GestorPersistencia.registrarHistorial("Tarea editada (ID " + id + ")");
                break;
            }
        }
    }

    public boolean editarEstadoPorId(int id, boolean completada) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (tarea.isCompletada() == completada) {
                    return false;
                }
                tarea.setCompletada(completada);
                GestorPersistencia.guardarTareas(tareas);
                GestorPersistencia.registrarHistorial("Estado actualizado para tarea ID " + id + " → " + (completada ? "✔ Completada" : "✘ No completada"));
                return true;
            }
        }
        return false;
    }

    public boolean editarEstadoPorTitulo(String titulo, boolean completada) {
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equalsIgnoreCase(titulo)) {
                if (tarea.isCompletada() == completada) {
                    return false;
                }
                tarea.setCompletada(completada);
                GestorPersistencia.guardarTareas(tareas);
                GestorPersistencia.registrarHistorial("Estado actualizado para tarea '" + titulo + "' → " + (completada ? "✔ Completada" : "✘ No completada"));
                return true;
            }
        }
        return false;
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

    public boolean tituloValido(String titulo) {
        return titulo != null && !titulo.trim().isEmpty();
    }

    public boolean existeTitulo(String titulo) {
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equalsIgnoreCase(titulo)) {
                return true;
            }
        }
        return false;
    }

    public void listarTareasOrdenadasPorEstado() {
        tareas.stream()
                .sorted(Comparator.comparing(Tarea::isCompletada))
                .forEach(System.out::println);
    }

    public void listarTareasOrdenadasPorTitulo() {
        tareas.stream()
                .sorted(Comparator.comparing(Tarea::getTitulo))
                .forEach(System.out::println);
    }

    public void buscarTareas(String palabraClave) {
        boolean encontrada = false;
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().toLowerCase().contains(palabraClave.toLowerCase()) ||
                    tarea.getDescripcion().toLowerCase().contains(palabraClave.toLowerCase())) {
                System.out.println(tarea);
                encontrada = true;
            }
        }
        if (!encontrada) {
            System.out.println("No se encontraron tareas con esa palabra clave.");
        }
    }
}
