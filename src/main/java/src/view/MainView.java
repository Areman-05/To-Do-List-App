package src.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import src.controller.Empleado;
import src.controller.GestorTareas;
import src.controller.Tarea;

public class MainView {
    private Stage stage;
    private Label welcomeLabel;
    private Button agregarButton;
    private Button editarButton;
    private Button eliminarButton;
    private Button listarButton;
    private Button buscarButton;
    private Button marcarEstadoButton;
    private Button actualizarButton;
    private ComboBox<String> ordenarCombo;
    private VBox centerContent;
    private Label estadisticasLabel;
    
    public MainView(Stage primaryStage) {
        this.stage = primaryStage;
        createView();
    }
    
    private void createView() {
        BorderPane root = new BorderPane();
        
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);
        welcomeLabel = new Label("Bienvenido");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        estadisticasLabel = new Label("");
        estadisticasLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #6c757d;");
        topBar.getChildren().addAll(welcomeLabel, estadisticasLabel);
        
        HBox buttonBar = new HBox(10);
        buttonBar.setPadding(new Insets(15));
        buttonBar.setAlignment(Pos.CENTER);
        
        agregarButton = new Button("➕ Agregar Tarea");
        agregarButton.setPrefWidth(150);
        agregarButton.setPrefHeight(35);
        agregarButton.setTooltip(new javafx.scene.control.Tooltip("Crear una nueva tarea (Ctrl+N)"));
        
        editarButton = new Button("✏️ Editar Tarea");
        editarButton.setPrefWidth(150);
        editarButton.setPrefHeight(35);
        editarButton.setTooltip(new javafx.scene.control.Tooltip("Modificar una tarea existente (Ctrl+E)"));
        
        eliminarButton = new Button("🗑️ Eliminar Tarea");
        eliminarButton.setPrefWidth(150);
        eliminarButton.setPrefHeight(35);
        eliminarButton.setTooltip(new javafx.scene.control.Tooltip("Eliminar una tarea permanentemente (Ctrl+D)"));
        
        listarButton = new Button("📋 Listar Tareas");
        listarButton.setPrefWidth(150);
        listarButton.setPrefHeight(35);
        listarButton.setTooltip(new javafx.scene.control.Tooltip("Ver todas las tareas en una ventana separada (Ctrl+L)"));
        
        buscarButton = new Button("🔍 Buscar Tareas");
        buscarButton.setPrefWidth(150);
        buscarButton.setPrefHeight(35);
        buscarButton.setTooltip(new javafx.scene.control.Tooltip("Buscar tareas por palabra clave (Ctrl+F)"));
        
        marcarEstadoButton = new Button("✓ Cambiar Estado");
        marcarEstadoButton.setPrefWidth(150);
        marcarEstadoButton.setPrefHeight(35);
        marcarEstadoButton.setTooltip(new javafx.scene.control.Tooltip("Marcar tareas como completadas o pendientes"));
        
        actualizarButton = new Button("🔄 Actualizar");
        actualizarButton.setPrefWidth(120);
        actualizarButton.setPrefHeight(35);
        actualizarButton.setTooltip(new javafx.scene.control.Tooltip("Refrescar la lista de tareas (Ctrl+R)"));
        actualizarButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        
        buttonBar.getChildren().addAll(agregarButton, editarButton, eliminarButton, listarButton, buscarButton, marcarEstadoButton, actualizarButton);
        
        HBox ordenarBox = new HBox(10);
        ordenarBox.setPadding(new Insets(10));
        ordenarBox.setAlignment(Pos.CENTER_LEFT);
        Label ordenarLabel = new Label("Ordenar por:");
        ordenarCombo = new ComboBox<>();
        ordenarCombo.getItems().addAll("ID (ascendente)", "ID (descendente)", "Título (A-Z)", "Título (Z-A)", "Estado");
        ordenarCombo.setValue("ID (ascendente)");
        ordenarCombo.setPrefWidth(150);
        ordenarBox.getChildren().addAll(ordenarLabel, ordenarCombo);
        
        centerContent = new VBox(10);
        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.TOP_CENTER);
        
        VBox centerPanel = new VBox(5);
        centerPanel.getChildren().addAll(ordenarBox, centerContent);
        
        BorderPane contentPane = new BorderPane();
        contentPane.setTop(buttonBar);
        contentPane.setCenter(centerPanel);
        
        root.setTop(topBar);
        root.setCenter(contentPane);
        
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        
        javafx.scene.input.KeyCombination.Modifier ctrl = KeyCombination.CONTROL_DOWN;
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.N, ctrl), agregarButton::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.E, ctrl), editarButton::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D, ctrl), eliminarButton::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.L, ctrl), listarButton::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, ctrl), buscarButton::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.R, ctrl), actualizarButton::fire);
        
        stage.setScene(scene);
        stage.setTitle("Gestor de Tareas - To-Do List App");
        stage.setMinWidth(800);
        stage.setMinHeight(500);
    }
    
    public void setWelcomeMessage(String message) {
        welcomeLabel.setText(message);
    }
    
    public void actualizarEstadisticas(int total, int completadas, int pendientes) {
        estadisticasLabel.setText(String.format("| Total: %d | Completadas: %d | Pendientes: %d", total, completadas, pendientes));
    }
    
    public Button getAgregarButton() {
        return agregarButton;
    }
    
    public Button getEditarButton() {
        return editarButton;
    }
    
    public Button getEliminarButton() {
        return eliminarButton;
    }
    
    public Button getListarButton() {
        return listarButton;
    }
    
    public Button getBuscarButton() {
        return buscarButton;
    }
    
    public Button getMarcarEstadoButton() {
        return marcarEstadoButton;
    }
    
    public Button getActualizarButton() {
        return actualizarButton;
    }
    
    public ComboBox<String> getOrdenarCombo() {
        return ordenarCombo;
    }
    
    public VBox getCenterContent() {
        return centerContent;
    }
    
    public void actualizarListaTareas(java.util.List<Tarea> tareas) {
        centerContent.getChildren().clear();
        
        int total = tareas.size();
        long completadas = tareas.stream().filter(Tarea::isCompletada).count();
        long pendientes = total - completadas;
        
        actualizarEstadisticas(total, (int)completadas, (int)pendientes);
        
        Label tituloLabel = new Label("Mis Tareas");
        tituloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        centerContent.getChildren().add(tituloLabel);
        
        java.util.List<Tarea> tareasOrdenadas = new java.util.ArrayList<>(tareas);
        String ordenSeleccionado = ordenarCombo.getValue();
        if (ordenSeleccionado != null) {
            switch (ordenSeleccionado) {
                case "ID (ascendente)":
                    tareasOrdenadas.sort((a, b) -> Integer.compare(a.getId(), b.getId()));
                    break;
                case "ID (descendente)":
                    tareasOrdenadas.sort((a, b) -> Integer.compare(b.getId(), a.getId()));
                    break;
                case "Título (A-Z)":
                    tareasOrdenadas.sort((a, b) -> a.getTitulo().compareToIgnoreCase(b.getTitulo()));
                    break;
                case "Título (Z-A)":
                    tareasOrdenadas.sort((a, b) -> b.getTitulo().compareToIgnoreCase(a.getTitulo()));
                    break;
                case "Estado":
                    tareasOrdenadas.sort((a, b) -> Boolean.compare(a.isCompletada(), b.isCompletada()));
                    break;
            }
        }
        
        ListView<String> lista = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        
        if (tareasOrdenadas.isEmpty()) {
            items.add("📝 No hay tareas disponibles");
            items.add("");
            items.add("Use el botón '➕ Agregar Tarea' o presione Ctrl+N para crear una nueva tarea.");
        } else {
            for (Tarea tarea : tareasOrdenadas) {
                String estado = tarea.isCompletada() ? "✓ Completada" : "○ Pendiente";
                String texto = String.format("ID: %d | %s | %s", tarea.getId(), estado, tarea.getTitulo());
                if (!tarea.getDescripcion().isEmpty()) {
                    String descCorta = tarea.getDescripcion().length() > 50 
                        ? tarea.getDescripcion().substring(0, 50) + "..." 
                        : tarea.getDescripcion();
                    texto += " | " + descCorta;
                }
                items.add(texto);
            }
        }
        
        lista.setItems(items);
        lista.setPrefHeight(400);
        centerContent.getChildren().add(lista);
    }
    
    public Stage getStage() {
        return stage;
    }
}

