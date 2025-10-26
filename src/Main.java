package src;

import java.util.Scanner;
import src.controller.GestorTareas;
import src.controller.GestorEmpleados;
import src.controller.Tarea;
import src.controller.Empleado;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorEmpleados gestorEmpleados = new GestorEmpleados();
        int idEmpleado = -1;
        Empleado empleadoActual = null;

        while (true) {
            System.out.println("\n--- BIENVENIDO ---");
            System.out.println("1. Iniciar sesión como empleado");
            System.out.println("2. Registrarse como nuevo empleado");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcionLogin = scanner.nextInt();
            scanner.nextLine();

            if (opcionLogin == 1) {
                System.out.print("Ingrese su ID de empleado: ");
                idEmpleado = scanner.nextInt();
                scanner.nextLine();
                if (gestorEmpleados.verificarEmpleado(idEmpleado)) {
                    empleadoActual = gestorEmpleados.obtenerEmpleado(idEmpleado);
                    System.out.println("Acceso concedido. Bienvenido, " + empleadoActual.getRol() + " " + idEmpleado);
                    break;
                } else {
                    System.out.println("ID no registrado. Intente nuevamente.");
                }
            } else if (opcionLogin == 2) {
                System.out.print("Ingrese un nuevo ID para registrarse: ");
                int nuevoId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Ingrese rol (admin/empleado): ");
                String rolInput = scanner.nextLine().toLowerCase();

                if (!rolInput.equals("admin") && !rolInput.equals("empleado")) {
                    System.out.println("Rol inválido. Use 'admin' o 'empleado'.");
                    break;
                }

                if (gestorEmpleados.registrarEmpleado(nuevoId, rolInput)) {
                    System.out.println("Empleado registrado exitosamente como " + rolInput + ". Ahora puede iniciar sesión.");
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

        GestorTareas gestor = new GestorTareas();
        int opcion;

        do {
            System.out.println("\n--- GESTOR DE TAREAS ---");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Eliminar tarea");
            System.out.println("3. Editar tarea");
            System.out.println("4. Listar tareas");
            System.out.println("5. Buscar tareas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese título de la tarea: ");
                    String titulo = scanner.nextLine();

                    if (!gestor.tituloValido(titulo)) {
                        System.out.println("El título no puede estar vacío.");
                        break;
                    }
                    if (gestor.existeTitulo(titulo)) {
                        System.out.println("Ya existe una tarea con ese título.");
                        break;
                    }

                    System.out.print("Ingrese descripción de la tarea (opcional): ");
                    String descripcion = scanner.nextLine();

                    int nuevoId = gestor.generarNuevoId();
                    Tarea nuevaTarea = new Tarea(nuevoId, titulo + " [ID:" + idEmpleado + "]", descripcion);
                    gestor.agregarTarea(nuevaTarea);
                    System.out.println("Tarea agregada con ID " + nuevoId + ".");
                    break;

                case 2:
                    if (!empleadoActual.getRol().equals("admin")) {
                        System.out.println("Acceso denegado. Solo los administradores pueden eliminar tareas.");
                        break;
                    }

                    int opcionEliminar;
                    do {
                        System.out.println("\n--- ELIMINAR TAREA ---");
                        System.out.println("1. Eliminar por ID");
                        System.out.println("2. Eliminar por título de tarea");
                        System.out.println("3. Volver al menú principal");
                        System.out.print("Seleccione una opción: ");

                        opcionEliminar = scanner.nextInt();
                        scanner.nextLine();

                        if (opcionEliminar == 1) {
                            System.out.print("Ingrese el ID de la tarea a eliminar: ");
                            int idEliminar = scanner.nextInt();
                            scanner.nextLine();
                            gestor.eliminarTarea(idEliminar);
                            System.out.println("Tarea eliminada con éxito.");
                        } else if (opcionEliminar == 2) {
                            System.out.print("Ingrese el título de la tarea a eliminar: ");
                            String tituloEliminar = scanner.nextLine();
                            boolean eliminada = gestor.eliminarTareaPorTitulo(tituloEliminar);
                            if (eliminada) {
                                System.out.println("Tarea eliminada con éxito.");
                            } else {
                                System.out.println("No se encontró esa tarea.");
                            }
                        } else if (opcionEliminar != 3) {
                            System.out.println("Opción no válida.");
                        }
                    } while (opcionEliminar != 3);
                    break;

                case 3:
                    int subOpcionEditar;
                    do {
                        System.out.println("\n--- EDITAR TAREA ---");
                        System.out.println("1. Cambiar título");
                        System.out.println("2. Cambiar descripción");
                        System.out.println("3. Editar estado");
                        System.out.println("4. Volver al menú principal");
                        System.out.print("Seleccione una opción: ");
                        subOpcionEditar = scanner.nextInt();
                        scanner.nextLine();

                        if (subOpcionEditar == 1 || subOpcionEditar == 2 || subOpcionEditar == 3) {
                            System.out.print("Ingrese el título de la tarea a editar: ");
                            String tituloEditar = scanner.nextLine();

                            if (!empleadoActual.getRol().equals("admin") &&
                                    !tituloEditar.contains("[ID:" + idEmpleado + "]")) {
                                System.out.println("No tienes permiso para editar esta tarea.");
                                break;
                            }

                            if (subOpcionEditar == 1) {
                                System.out.print("Nuevo título: ");
                                String nuevoTitulo = scanner.nextLine();
                                gestor.editarTituloPorNombre(tituloEditar, nuevoTitulo + " [ID:" + idEmpleado + "]");
                                System.out.println("Título actualizado.");
                            } else if (subOpcionEditar == 2) {
                                System.out.print("Nueva descripción: ");
                                String nuevaDesc = scanner.nextLine();
                                gestor.editarDescripcionPorNombre(tituloEditar, nuevaDesc);
                                System.out.println("Descripción actualizada.");
                            } else {
                                System.out.print("¿Marcar como completada? (s/n): ");
                                String estado = scanner.nextLine().toLowerCase();
                                boolean completada = estado.equals("s");
                                boolean resultado = gestor.editarEstadoPorTitulo(tituloEditar, completada);
                                if (resultado) {
                                    System.out.println("Estado actualizado correctamente.");
                                } else {
                                    System.out.println("No se pudo actualizar el estado.");
                                }
                            }
                        }
                    } while (subOpcionEditar != 4);
                    break;

                case 4:
                    System.out.println("\n=== LISTA DE TAREAS ===");
                    System.out.println("1. Listar todas");
                    System.out.println("2. Ordenadas por estado");
                    System.out.println("3. Ordenadas por título");
                    System.out.print("Seleccione una opción: ");
                    int subOpcionListar = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcionListar) {
                        case 1 -> gestor.listarTareas();
                        case 2 -> gestor.listarTareasOrdenadasPorEstado();
                        case 3 -> gestor.listarTareasOrdenadasPorTitulo();
                        default -> System.out.println("Opción no válida.");
                    }
                    System.out.println("=======================\n");
                    break;

                case 5:
                    System.out.print("Ingrese palabra clave para buscar: ");
                    String clave = scanner.nextLine();
                    gestor.buscarTareas(clave);
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
