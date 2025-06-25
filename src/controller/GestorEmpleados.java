package src.controller;

import src.persistence.GestorPersistenciaEmpleados;
import java.util.List;

public class GestorEmpleados {
    private List<Empleado> empleados;

    public GestorEmpleados() {
        this.empleados = GestorPersistenciaEmpleados.cargarEmpleados();
    }

    public boolean registrarEmpleado(int id) {
        if (verificarEmpleado(id)) return false;
        empleados.add(new Empleado(id));
        GestorPersistenciaEmpleados.guardarEmpleados(empleados);
        return true;
    }

    public boolean verificarEmpleado(int id) {
        for (Empleado e : empleados) {
            if (e.getId() == id) return true;
        }
        return false;
    }
}
