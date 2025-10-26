package src.controller;

import src.persistence.GestorPersistenciaEmpleados;
import java.util.List;

public class GestorEmpleados {
    private List<Empleado> empleados;

    public GestorEmpleados() {
        this.empleados = GestorPersistenciaEmpleados.cargarEmpleados();
    }

    public boolean registrarEmpleado(int id, String rolUsuario) {
        if (verificarEmpleado(id)) return false;
        empleados.add(new Empleado(id, rolUsuario));
        GestorPersistenciaEmpleados.guardarEmpleados(empleados);
        return true;
    }

    public Empleado obtenerEmpleado(int id) {
        for (Empleado e : empleados) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public boolean verificarEmpleado(int id) {
        for (Empleado e : empleados) {
            if (e.getId() == id) return true;
        }
        return false;
    }
}
