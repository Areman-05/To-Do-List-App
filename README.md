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

- Lenguaje de programación: Java.
- Persistencia: Lectura y escritura de archivos de texto usando clases de entrada/salida (BufferedReader, BufferedWriter).
- Estructuras de datos: Uso de listas dinámicas con ArrayList para gestionar tareas y empleados.
- Interacción con el usuario: Consola con Scanner y menús claros y estructurados.

# Estructura del Proyecto

El proyecto está organizado en distintas carpetas para una mayor claridad y mantenibilidad del código:

-src/controller: Contiene las clases de lógica de negocio:
 - Tarea: Representa una tarea individual.
 - GestorTareas: Gestiona las operaciones sobre las tareas.
 - Empleado: Representa un empleado.
 - GestorEmpleados: Gestiona el registro y verificación de empleados.
- src/persistence: Incluye las clases encargadas de guardar y cargar datos desde archivos de texto:
  - GestorPersistencia: Gestiona la persistencia de las tareas.
  - GestorPersistenciaEmpleados: Gestiona la persistencia de los empleados.
-src/Main.java: Contiene el punto de entrada de la aplicación, donde se gestiona la interacción completa con el usuario a través de menús y submenús.
