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