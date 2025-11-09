TO-DO-LIST-APP


# Descripción

To-Do List App es una aplicación desarrollada en Java que permite gestionar tareas de manera sencilla y ordenada. Su objetivo principal es ayudar a los usuarios a organizar y llevar un control de su trabajo diario mediante un sistema que permite añadir, editar, marcar como completadas y eliminar tareas.
La aplicación incluye además un sistema de empleados que permite el registro e inicio de sesión para controlar el acceso al gestor de tareas, garantizando que solo usuarios registrados puedan gestionar sus tareas.Este proyecto es ideal para mejorar la productividad, practicar conceptos de programación orientada a objetos, y familiarizarse con la persistencia de datos en archivos de texto.
Este proyecto es ideal para mejorar la productividad y el seguimiento de actividades pendientes, permitiendo una gestión eficiente de tareas en una interfaz simple y accesible.

# Características

- Registro e inicio de sesión de empleados: solo los empleados registrados pueden acceder al gestor de tareas.
- Agregar tareas: creación de tareas nuevas especificando un título y, opcionalmente, una descripción; el sistema asigna automáticamente un ID único.
- Editar tareas: modificar el título, la descripción o el estado (completada / no completada) de una tarea mediante submenús intuitivos.
- Eliminar tareas: borrar tareas por ID o por nombre para mayor comodidad.
- Listar tareas: mostrar todas las tareas creadas diferenciando claramente las completadas de las no completadas (✔ o ✘).
- Persistencia de datos: las tareas y empleados se guardan automáticamente en archivos de texto para que la información se mantenga entre ejecuciones.



# Tecnologías Utilizadas

- Lenguaje de programación: Java 17.
- Gestión de dependencias y construcción: Maven.
- Persistencia híbrida: SQLite (principal) con respaldo automático en archivos CSV.
- Testing: JUnit 5.
- Estructuras de datos: Uso de listas dinámicas con ArrayList para gestionar tareas y empleados.
- Interacción con el usuario: Consola con Scanner y menús claros y estructurados.

# Configuración del Proyecto

## Requisitos previos

- Java 17 o superior instalado y disponible en el `PATH`.
- Maven 3.9 o superior.

## Ejecución

1. Instala las dependencias y compila el proyecto:

   ```bash
   mvn clean package
   ```

2. Ejecuta la aplicación:

   ```bash
   mvn exec:java -Dexec.mainClass="src.Main"
   ```

   > El primer arranque creará la base de datos SQLite en `data/todolist.db` junto con los archivos CSV (`tareas.csv`, `empleados.csv`) y el historial (`historial.log`).

## Personalización de rutas

Para entornos de prueba o despliegues personalizados, puedes redefinir las rutas mediante propiedades del sistema al iniciar la JVM:

- `todolist.db.url` – Ruta JDBC de la base de datos (por defecto `jdbc:sqlite:data/todolist.db`).
- `todolist.tareas.csv` – Archivo principal de tareas (por defecto `tareas.csv`).
- `todolist.tareas.backup.csv` – Copia de seguridad de tareas (por defecto `tareas_backup.csv`).
- `todolist.empleados.csv` – Archivo principal de empleados (por defecto `empleados.csv`).
- `todolist.empleados.backup.csv` – Copia de seguridad de empleados (por defecto `empleados_backup.csv`).
- `todolist.historial.log` – Registro de operaciones.

Ejemplo:

```bash
mvn exec:java \
  -Dexec.mainClass="src.Main" \
  -Dtodolist.db.url="jdbc:sqlite:/ruta/personalizada/todolist.db" \
  -Dtodolist.tareas.csv="/ruta/tareas.csv"
```

## Scripts SQL incluidos

El proyecto incorpora scripts sencillos dentro de `src/main/resources/sql/`:

- `schema.sql`: define la estructura de tablas para `tareas` y `empleados`.
- `seed.sql`: inserta un empleado y una tarea de ejemplo usando `INSERT OR IGNORE`.

Estos scripts se ejecutan automáticamente al iniciar la aplicación, pero puedes utilizarlos por separado si deseas crear o inspeccionar la base de datos manualmente:

```bash
sqlite3 data/todolist.db < src/main/resources/sql/schema.sql
sqlite3 data/todolist.db < src/main/resources/sql/seed.sql
```

> Si prefieres no cargar datos iniciales, elimina o vacía `seed.sql`; el gestor seguirá funcionando porque todos los scripts se ejecutan de forma opcional.

# Ejecución de Pruebas

El proyecto incluye pruebas unitarias para la lógica principal de tareas y empleados. Para ejecutarlas:

```bash
mvn test
```

Durante las pruebas se utilizan rutas temporales y una base de datos aislada para no modificar los datos reales.

# Estructura del Proyecto

El proyecto está organizado en distintas carpetas para una mayor claridad y mantenibilidad del código:

src/controller: Contiene las clases de lógica de negocio:
- Tarea: Representa una tarea individual.
- GestorTareas: Gestiona las operaciones sobre las tareas.
- Empleado: Representa un empleado.
- GestorEmpleados: Gestiona el registro y verificación de empleados.
  
src/persistence: Incluye las clases encargadas de guardar y cargar datos desde archivos de texto:
- GestorPersistencia: Gestiona la persistencia de las tareas.
- GestorPersistenciaEmpleados: Gestiona la persistencia de los empleados.
  
src/Main.java: Contiene el punto de entrada de la aplicación, donde se gestiona la interacción completa con el usuario a través de menús y submenús.
