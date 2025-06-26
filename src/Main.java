package src;

import java.util.Scanner;
import src.controller.GestorTareas;
import src.controller.GestorEmpleados;
import src.controller.Tarea;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorEmpleados gestorEmpleados = new GestorEmpleados();
        int idEmpleado = -1;

        while (true) {
            System.out.println("\n--- BIENVENIDO ---");
            System.out.println("1. Iniciar sesión como empleado");
            System.out.println("2. Registrarse como nuevo empleado");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcionLogin = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            if (opcionLogin == 1) {
                System.out.print("Ingrese su ID de empleado: ");
                idEmpleado = scanner.nextInt();
                scanner.nextLine();
                if (gestorEmpleados.verificarEmpleado(idEmpleado)) {
                    System.out.println("Acceso concedido. Bienvenido, empleado " + idEmpleado);
                    break;
                } else {
                    System.out.println("ID no registrado. Intente nuevamente.");
                }
            } else if (opcionLogin == 2) {
                System.out.print("Ingrese un nuevo ID para registrarse: ");
                int nuevoId = scanner.nextInt();
                scanner.nextLine();
                if (gestorEmpleados.registrarEmpleado(nuevoId)) {
                    System.out.println("Empleado registrado exitosamente. Ahora puede iniciar sesión.");
                } else {
                    System.out.println("Ese ID ya está registrado. Intente con otro.");
                }
            } else if (opcionLogin == 3) {
                System.out.println("Saliendo del programa...");
                scanner.close();
                return;
            } else {
                System.out.println("Opción no válida.");
            }
        }

        // Acceso concedido, se entra al gestor de tareas
        GestorTareas gestor = new GestorTareas();
        int opcion;

        do {
            System.out.println("\n--- GESTOR DE TAREAS ---");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Eliminar tarea");
            System.out.println("3. Editar tarea");
            System.out.println("4. Listar tareas");
            System.out.println("5. Marcar tarea como completada");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ID de la tarea: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese título de la tarea: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese descripción de la tarea: ");
                    String descripcion = scanner.nextLine();

                    Tarea nuevaTarea = new Tarea(id, titulo, descripcion);
                    gestor.agregarTarea(nuevaTarea);
                    System.out.println("Tarea agregada con éxito.");
                    break;
                case 2:
                    System.out.print("Ingrese el ID de la tarea a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    gestor.eliminarTarea(idEliminar);
                    System.out.println("Tarea eliminada con éxito.");
                    break;
                case 3:
                    System.out.print("Ingrese el ID de la tarea a editar: ");
                    int idEditar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese el nuevo título de la tarea: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Ingrese la nueva descripción de la tarea: ");
                    String nuevaDescripcion = scanner.nextLine();

                    gestor.editarTarea(idEditar, nuevoTitulo, nuevaDescripcion);
                    System.out.println("Tarea editada con éxito.");
                    break;
                case 4:
                    System.out.println("\n=== LISTA DE TAREAS ===");
                    gestor.listarTareas();
                    System.out.println("=======================\n");
                    break;
                case 5:
                    System.out.print("Ingrese el ID de la tarea a marcar como completada: ");
                    int idCompletar = scanner.nextInt();
                    gestor.marcarTareaComoCompletada(idCompletar);
                    System.out.println("Tarea marcada como completada.");
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        } while (opcion != 6);
        scanner.close();
    }
}
