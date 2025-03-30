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
}
