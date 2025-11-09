package src.controller;

import src.persistence.DatabaseManager;
import src.persistence.GestorPersistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

/**
 * Pequeño runner de pruebas sin dependencias externas.
 * Ejecuta tres casos críticos y finaliza con código 0 si todo va bien.
 */
public class GestorTareasTest {

    private Path tempDir;

    private interface TestCase {
        void execute() throws Exception;
    }

    public static void main(String[] args) {
        GestorTareasTest runner = new GestorTareasTest();

        runner.ejecutar("Agregar tarea actualiza persistencia", () -> runner.probarAgregarTarea());
        runner.ejecutar("Editar estado por ID persiste cambios", () -> runner.probarEditarEstado());
        runner.ejecutar("Registrar empleado evita duplicados", () -> runner.probarRegistrarEmpleado());

        System.out.println("Todas las pruebas han pasado correctamente.");
    }

    private void ejecutar(String nombre, TestCase caso) {
        try {
            setUp();
            caso.execute();
            System.out.println("✔ " + nombre);
        } catch (AssertionError e) {
            System.err.println("✘ " + nombre + ": " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("✘ " + nombre + ": " + e);
            e.printStackTrace(System.err);
            System.exit(1);
        } finally {
            try {
                tearDown();
            } catch (IOException ignored) {
            }
        }
    }

    private void probarAgregarTarea() {
        GestorTareas gestor = new GestorTareas();
        assertTrue(gestor.obtenerTareas().isEmpty(), "La lista inicial debe estar vacía");

        int nuevoId = gestor.generarNuevoId();
        gestor.agregarTarea(new Tarea(nuevoId, "Preparar informe", "Revisar métricas trimestrales"));

        List<Tarea> tareasEnMemoria = gestor.obtenerTareas();
        assertEquals(1, tareasEnMemoria.size(), "Debe haber una tarea en memoria");
        assertEquals("Preparar informe", tareasEnMemoria.get(0).getTitulo(), "El título en memoria no coincide");

        List<Tarea> tareasPersistidas = GestorPersistencia.cargarTareas();
        assertEquals(1, tareasPersistidas.size(), "Debe haber una tarea persistida");
        assertEquals("Preparar informe", tareasPersistidas.get(0).getTitulo(), "El título persistido no coincide");
    }

    private void probarEditarEstado() {
        GestorTareas gestor = new GestorTareas();
        int nuevoId = gestor.generarNuevoId();
        gestor.agregarTarea(new Tarea(nuevoId, "Actualizar dependencias", "Aplicar parches de seguridad"));

        assertTrue(gestor.editarEstadoPorId(nuevoId, true), "Debe actualizar el estado de la tarea");
        assertTrue(gestor.obtenerTareas().get(0).isCompletada(), "La tarea en memoria debe quedar completada");

        GestorTareas nuevoGestor = new GestorTareas();
        assertTrue(nuevoGestor.obtenerTareas().get(0).isCompletada(), "La tarea nueva instancia debe seguir completada");
    }

    private void probarRegistrarEmpleado() {
        GestorEmpleados gestorEmpleados = new GestorEmpleados();
        assertTrue(gestorEmpleados.registrarEmpleado(101, "admin"), "Debe registrar un nuevo empleado");
        assertTrue(gestorEmpleados.verificarEmpleado(101), "El empleado debe existir tras registrarse");

        assertFalse(gestorEmpleados.registrarEmpleado(101, "empleado"), "No debe permitir registrar IDs duplicados");

        GestorEmpleados nuevoGestor = new GestorEmpleados();
        assertTrue(nuevoGestor.verificarEmpleado(101), "La persistencia debe conservar el empleado registrado");
        assertEquals("admin", nuevoGestor.obtenerEmpleado(101).getRol(), "El rol debe mantenerse tras recargar");
    }

    private void setUp() throws IOException {
        tempDir = Files.createTempDirectory("todolist-test-");
        Path tareas = tempDir.resolve("tareas.csv");
        Path tareasBackup = tempDir.resolve("tareas_backup.csv");
        Path historial = tempDir.resolve("historial.log");
        Path empleados = tempDir.resolve("empleados.csv");
        Path empleadosBackup = tempDir.resolve("empleados_backup.csv");
        Path dbFile = tempDir.resolve("todolist.db");

        System.setProperty("todolist.tareas.csv", tareas.toString());
        System.setProperty("todolist.tareas.backup.csv", tareasBackup.toString());
        System.setProperty("todolist.historial.log", historial.toString());
        System.setProperty("todolist.empleados.csv", empleados.toString());
        System.setProperty("todolist.empleados.backup.csv", empleadosBackup.toString());

        String dbUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath().toString().replace("\\", "/");
        DatabaseManager.configure(dbUrl);
    }

    private void tearDown() throws IOException {
        System.clearProperty("todolist.tareas.csv");
        System.clearProperty("todolist.tareas.backup.csv");
        System.clearProperty("todolist.historial.log");
        System.clearProperty("todolist.empleados.csv");
        System.clearProperty("todolist.empleados.backup.csv");
        if (tempDir != null) {
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(java.io.File::delete);
        }
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        throw new AssertionError("%s (esperado: %s, actual: %s)".formatted(message, expected, actual));
    }
}
