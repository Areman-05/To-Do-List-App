package src.view;

import src.controller.GestorTareas;
import src.controller.Tarea;

import java.util.Scanner;

public class InterfazConsola {
    private static GestorTareas gestor = new GestorTareas();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== GESTOR DE TAREAS ===");
            System.out.println("1. Listar tareas");
            System.out.println("2. Agregar tarea");
            System.out.println("3. Editar tarea");
            System.out.println("4. Eliminar tarea");
            System.out.println("5. Marcar tarea como completada");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    gestor.listarTareas();
                    break;
                case 2:
                    agregarTarea();
                    break;
                case 3:
                    editarTarea();
                    break;
                case 4:
                    eliminarTarea();
                    break;
                case 5:
                    marcarComoCompletada();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
    private static void agregarTarea() {
        System.out.print("Ingrese ID de la tarea: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese título: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese descripción: ");
        String descripcion = scanner.nextLine();

        Tarea tarea = new Tarea(id, titulo, descripcion);
        gestor.agregarTarea(tarea);
    }
    private static void editarTarea() {
        System.out.print("Ingrese ID de la tarea a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nuevo título: ");
        String nuevoTitulo = scanner.nextLine();
        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = scanner.nextLine();

        gestor.editarTarea(id, nuevoTitulo, nuevaDescripcion);
    }
    private static void eliminarTarea() {
        System.out.print("Ingrese ID de la tarea a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        gestor.eliminarTarea(id);
    }
    private static void marcarComoCompletada() {
        System.out.print("Ingrese ID de la tarea a marcar como completada: ");
        int id = Integer.parseInt(scanner.nextLine());
        gestor.marcarTareaComoCompletada(id);
    }
}
