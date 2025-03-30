public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private boolean completada;

    // Constructor
    public Tarea(int id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = false;
    }
}
