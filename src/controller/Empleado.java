package src.controller;

public class Empleado {
    private int id;

    public Empleado(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Empleado[ID=" + id + "]";
    }
}