package src.persistence;

import src.controller.Empleado;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistenciaEmpleados {
    private static final String ARCHIVO_EMPLEADOS = "empleados.txt";

    public static void guardarEmpleados(List<Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_EMPLEADOS))) {
            for (Empleado empleado : empleados) {
                writer.write(String.valueOf(empleado.getId()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    public static List<Empleado> cargarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        File archivo = new File(ARCHIVO_EMPLEADOS);
        if (!archivo.exists()) return empleados;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                try {
                    int id = Integer.parseInt(linea.trim());
                    empleados.add(new Empleado(id));
                } catch (NumberFormatException ignored) {}
            }
        } catch (IOException e) {
            System.out.println("Error al cargar empleados: " + e.getMessage());
        }
        return empleados;
    }
}
