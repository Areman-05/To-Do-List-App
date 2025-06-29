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

- Lenguaje de programación: Java 
- Estructuras de datos: Uso de listas dinámicas con la clase ArrayList para gestionar las tareas.
- Entrada y salida de datos: Se emplea Scanner para la interacción con el usuario en consola.

# Estructura del Proyecto

El proyecto está organizado en distintas carpetas para una mejor estructuración y mantenibilidad del código:

- src/model: Contiene las clases que representan los datos principales del proyecto, como la clase src.controller.Tarea.
- src/controller: Abarca la lógica de negocio y las operaciones sobre las tareas, como agregar, eliminar o editar.
- src/view: Se encarga de la interfaz con el usuario, ya sea por consola o mediante una futura interfaz gráfica con JavaFX.
- src/Main.java: Archivo principal donde se ejecuta la aplicación y se inicializa el sistema.
- README.md: Documento explicativo del proyecto y su funcionamiento.
