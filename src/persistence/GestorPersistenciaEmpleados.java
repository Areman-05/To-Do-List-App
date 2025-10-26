package src.persistence;

import src.controller.Empleado;
import java.io.*;
import java.util.*;

public class GestorPersistenciaEmpleados {
    private static final String ARCHIVO_EMPLEADOS = "empleados.csv";
    private static final String BACKUP_EMPLEADOS = "empleados_backup.csv";

    public static void guardarEmpleados(List<Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_EMPLEADOS))) {
            for (Empleado empleado : empleados) {
                writer.write(empleado.getId() + "," + empleado.getRol());
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
                String[] partes = linea.split(",", -1);
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0].trim());
                    String rol = partes[1].trim();
                    empleados.add(new Empleado(id, rol));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar empleados: " + e.getMessage());
        }
        return empleados;
    }

    public static void backupEmpleados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_EMPLEADOS));
             BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_EMPLEADOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al crear backup de empleados: " + e.getMessage());
        }
    }
}
