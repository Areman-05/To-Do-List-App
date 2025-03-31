package src;
import java.util.Scanner;
import src.controller.GestorTareas;
import src.controller.Tarea;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorTareas gestor = new GestorTareas();
        int opcion;

        do {
            System.out.println("\n--- GESTOR DE TAREAS ---");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Eliminar tarea");
            System.out.println("3. Editar tarea");
            System.out.println("4. Listar tareas");
            System.out.println("5. Salir");
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
                    System.out.println("Opción para eliminar tarea seleccionada.");
                    break;
                case 3:
                    System.out.println("Opción para editar tarea seleccionada.");
                    break;
                case 4:
                    System.out.println("Opción para listar tareas seleccionada.");
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}
