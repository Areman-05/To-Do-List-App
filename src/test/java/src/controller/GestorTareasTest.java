package src.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.persistence.DatabaseManager;
import src.persistence.GestorPersistencia;
import src.persistence.GestorPersistenciaEmpleados;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestorTareasTest {

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
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

    @AfterEach
    void tearDown() throws IOException {
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

    @Test
    void agregarTareaActualizaPersistencia() {
        GestorTareas gestor = new GestorTareas();
        assertTrue(gestor.obtenerTareas().isEmpty(), "La lista inicial debe estar vacía");

        int nuevoId = gestor.generarNuevoId();
        gestor.agregarTarea(new Tarea(nuevoId, "Preparar informe", "Revisar métricas trimestrales"));

        List<Tarea> tareasEnMemoria = gestor.obtenerTareas();
        assertEquals(1, tareasEnMemoria.size(), "Debe haber una tarea en memoria");
        assertEquals("Preparar informe", tareasEnMemoria.get(0).getTitulo());

        List<Tarea> tareasPersistidas = GestorPersistencia.cargarTareas();
        assertEquals(1, tareasPersistidas.size(), "Debe haber una tarea persistida");
        assertEquals("Preparar informe", tareasPersistidas.get(0).getTitulo());
    }

    @Test
    void editarEstadoPorIdPersisteCambios() {
        GestorTareas gestor = new GestorTareas();
        int nuevoId = gestor.generarNuevoId();
        gestor.agregarTarea(new Tarea(nuevoId, "Actualizar dependencias", "Aplicar parches de seguridad"));

        assertTrue(gestor.editarEstadoPorId(nuevoId, true), "Debe actualizar el estado de la tarea");
        assertTrue(gestor.obtenerTareas().get(0).isCompletada(), "La tarea debe estar marcada como completada");

        GestorTareas nuevoGestor = new GestorTareas();
        assertTrue(nuevoGestor.obtenerTareas().get(0).isCompletada(), "La nueva instancia debe reflejar el estado completado");
    }

    @Test
    void registrarEmpleadoEvitaDuplicados() {
        GestorEmpleados gestorEmpleados = new GestorEmpleados();
        assertTrue(gestorEmpleados.registrarEmpleado(101, "admin"), "Debe registrar un nuevo empleado");
        assertTrue(gestorEmpleados.verificarEmpleado(101), "El empleado debe existir tras registrarse");

        assertFalse(gestorEmpleados.registrarEmpleado(101, "empleado"), "No debe permitir registrar IDs duplicados");

        GestorEmpleados nuevoGestor = new GestorEmpleados();
        assertTrue(nuevoGestor.verificarEmpleado(101), "La persistencia debe conservar el empleado registrado");
        assertEquals("admin", nuevoGestor.obtenerEmpleado(101).getRol(), "El rol debe mantenerse tras recargar");
    }
}

