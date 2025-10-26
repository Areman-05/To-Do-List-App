package src.controller;

public class Empleado {
    private int id;
    private String rol;

    public Empleado(int id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return "Empleado[ID=" + id + ", Rol=" + rol + "]";
    }
}
