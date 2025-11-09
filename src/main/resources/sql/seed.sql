-- Script opcional para poblar datos iniciales
INSERT OR IGNORE INTO empleados (id, rol) VALUES (1, 'admin');
INSERT OR IGNORE INTO tareas (id, titulo, descripcion, completada) VALUES
    (1, 'Tarea de ejemplo', 'Puedes borrar esta tarea cuando quieras', 0);

