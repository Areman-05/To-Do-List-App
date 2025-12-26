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
- Interfaz gráfica: JavaFX 17 para una experiencia visual moderna.
- Persistencia híbrida: SQLite (principal) con respaldo automático en archivos CSV.
- Scripts de apoyo: PowerShell sencillo (`scripts/*.ps1`) para compilar, probar y ejecutar sin herramientas externas.
- Estructuras de datos: Uso de listas dinámicas con ArrayList para gestionar tareas y empleados.

# Configuración del Proyecto

## Requisitos previos

- JDK 17 (se usa `javac` y `java` directamente).
- PowerShell 5+ (incluido en Windows) para ejecutar los scripts provistos.

> El repositorio incluye el controlador `sqlite-jdbc.jar` en la carpeta `lib/` para que no tengas que descargar nada adicional.

## Scripts disponibles

Todos los scripts se encuentran en la carpeta `scripts/`. Si tu política de ejecución no permite correrlos, puedes habilitarlos temporalmente con `Set-ExecutionPolicy -Scope Process Bypass`.

| Script | Descripción |
|--------|-------------|
| `scripts/compilar.ps1` | Compila el código en `out/`. Usa `-IncluirTests` para compilar también los tests. |
| `scripts/ejecutar.ps1` | Ejecuta la aplicación. Si no existe la carpeta `out/`, compila automáticamente primero. |
| `scripts/probar.ps1`   | Compila (incluyendo los tests) y ejecuta el pequeño runner de pruebas integrado. |

Ejemplo de uso desde PowerShell (ubicado en la raíz del proyecto):

```powershell
powershell -ExecutionPolicy Bypass -File scripts\compilar.ps1
powershell -ExecutionPolicy Bypass -File scripts\ejecutar.ps1
powershell -ExecutionPolicy Bypass -File scripts\probar.ps1
```

## Ejecución manual (sin scripts)

Si prefieres no usar PowerShell, puedes hacerlo todo con dos comandos:

```powershell
javac -cp lib\sqlite-jdbc.jar -d out (Get-ChildItem -Recurse src\main\java\*.java).FullName
java -cp "out;lib\sqlite-jdbc.jar" src.Main
```

> El primer arranque creará la base de datos SQLite en `data/todolist.db` junto con los archivos CSV (`tareas.csv`, `empleados.csv`) y el historial (`historial.log`).

## Personalización de rutas

Puedes redefinir la ubicación de la base de datos al ejecutar el script `ejecutar.ps1`:

```powershell
powershell -ExecutionPolicy Bypass -File scripts\ejecutar.ps1 -DbPath "C:\ruta\mis-datos.db"
```

Internamente se traduce en la propiedad `-Dtodolist.db.url=jdbc:sqlite:C:\ruta\mis-datos.db`. Si quieres ajustar otras rutas (`todolist.tareas.csv`, `todolist.empleados.csv`, etc.) puedes ejecutar manualmente la aplicación con `java` e incluir las propiedades deseadas.

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

El proyecto trae un runner de pruebas sin dependencias externas (`src/test/java/src/controller/GestorTareasTest.java`) que valida los casos más importantes de tareas y empleados usando archivos temporales y una base SQLite aislada.

```powershell
powershell -ExecutionPolicy Bypass -File scripts\probar.ps1
```

Si alguna validación falla, el script devolverá un código distinto de cero y mostrará el error en consola.

# Interfaz Gráfica

La aplicación utiliza JavaFX 17 para proporcionar una interfaz gráfica moderna e intuitiva. Las características principales incluyen:

- **Pantalla de Login**: Inicio de sesión y registro de empleados
- **Vista Principal**: Panel de control con acceso a todas las funcionalidades
- **Gestión de Tareas**: Diálogos para agregar, editar, eliminar y buscar tareas
- **Lista Visual**: Visualización en tiempo real de todas las tareas con su estado
- **Cambio de Estado**: Marcar tareas como completadas o pendientes fácilmente

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
