package src.controller;

public class Empleado {
    private final int id;
    private final String rol;

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
        return "Empleado[ID=" + id + ", Rol='" + rol + "']";
    }
}