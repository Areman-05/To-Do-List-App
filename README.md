TO-DO-LIST-APP


# Descripción

To-Do List App es una aplicación desarrollada en Java que permite gestionar tareas de manera sencilla. Su objetivo principal es ayudar a los usuarios a organizar su trabajo diario mediante una lista de tareas que pueden ser agregadas, editadas, marcadas como completadas y eliminadas.

Este proyecto es ideal para mejorar la productividad y el seguimiento de actividades pendientes, permitiendo una gestión eficiente de tareas en una interfaz simple y accesible.

# Características

- Agregar tareas: Permite a los usuarios crear nuevas tareas especificando un título y, opcionalmente, una descripción.
- Marcar tareas como completadas: Los usuarios pueden indicar cuáles de sus tareas ya han sido realizadas para mantener un seguimiento claro.
- Eliminar tareas: Las tareas que ya no sean necesarias pueden ser eliminadas de la lista para mantener la organización.
- Editar tareas: Se puede modificar el contenido de una tarea en caso de errores o cambios en la planificación.
-Listar todas las tareas: Se mostrará un listado de todas las tareas creadas, diferenciando las pendientes de las completadas.

# Tecnologías Utilizadas

- Lenguaje de programación: Java 
- Estructuras de datos: Uso de listas dinámicas con la clase ArrayList para gestionar las tareas.
- Entrada y salida de datos: Se emplea Scanner para la interacción con el usuario en consola.

# Estructura del Proyecto

El proyecto está organizado en distintas carpetas para una mejor estructuración y mantenibilidad del código:

- src/model: Contiene las clases que representan los datos principales del proyecto, como la clase Tarea.
- src/controller: Abarca la lógica de negocio y las operaciones sobre las tareas, como agregar, eliminar o editar.
- src/view: Se encarga de la interfaz con el usuario, ya sea por consola o mediante una futura interfaz gráfica con JavaFX.
- src/Main.java: Archivo principal donde se ejecuta la aplicación y se inicializa el sistema.
- README.md: Documento explicativo del proyecto y su funcionamiento.
