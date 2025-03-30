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

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }
    
    // Metodo para marcar la tarea como completada
    public void marcarComoCompletada() {
        this.completada = true;
    }
}
