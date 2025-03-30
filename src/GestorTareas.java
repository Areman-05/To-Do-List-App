package src;
import java.util.ArrayList;
import java.util.List;

public class GestorTareas {
    private List<Tarea> tareas;

    // Constructor
    public GestorTareas() {
        this.tareas = new ArrayList<>();
    }

    // Metodo para agregar una tarea
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    // Metodo para eliminar una tarea por ID
    public void eliminarTarea(int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
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
}
