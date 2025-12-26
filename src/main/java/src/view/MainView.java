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
    private VBox centerContent;
    
    public MainView(Stage primaryStage) {
        this.stage = primaryStage;
        createView();
    }
    
    private void createView() {
        BorderPane root = new BorderPane();
        
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);
        welcomeLabel = new Label("Bienvenido");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        topBar.getChildren().add(welcomeLabel);
        
        HBox buttonBar = new HBox(10);
        buttonBar.setPadding(new Insets(15));
        buttonBar.setAlignment(Pos.CENTER);
        
        agregarButton = new Button("Agregar Tarea");
        agregarButton.setPrefWidth(150);
        agregarButton.setPrefHeight(35);
        
        editarButton = new Button("Editar Tarea");
        editarButton.setPrefWidth(150);
        editarButton.setPrefHeight(35);
        
        eliminarButton = new Button("Eliminar Tarea");
        eliminarButton.setPrefWidth(150);
        eliminarButton.setPrefHeight(35);
        
        listarButton = new Button("Listar Tareas");
        listarButton.setPrefWidth(150);
        listarButton.setPrefHeight(35);
        
        buscarButton = new Button("Buscar Tareas");
        buscarButton.setPrefWidth(150);
        buscarButton.setPrefHeight(35);
        
        marcarEstadoButton = new Button("Cambiar Estado");
        marcarEstadoButton.setPrefWidth(150);
        marcarEstadoButton.setPrefHeight(35);
        
        buttonBar.getChildren().addAll(agregarButton, editarButton, eliminarButton, listarButton, buscarButton, marcarEstadoButton);
        
        centerContent = new VBox(10);
        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.TOP_CENTER);
        
        BorderPane contentPane = new BorderPane();
        contentPane.setTop(buttonBar);
        contentPane.setCenter(centerContent);
        
        root.setTop(topBar);
        root.setCenter(contentPane);
        
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Gestor de Tareas - To-Do List App");
    }
    
    public void setWelcomeMessage(String message) {
        welcomeLabel.setText(message);
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
    
    public VBox getCenterContent() {
        return centerContent;
    }
    
    public void actualizarListaTareas(java.util.List<Tarea> tareas) {
        centerContent.getChildren().clear();
        
        Label tituloLabel = new Label("Mis Tareas");
        tituloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        centerContent.getChildren().add(tituloLabel);
        
        ListView<String> lista = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        
        if (tareas.isEmpty()) {
            items.add("No hay tareas disponibles. Use 'Agregar Tarea' para crear una.");
        } else {
            for (Tarea tarea : tareas) {
                String estado = tarea.isCompletada() ? "✔ Completada" : "✘ Pendiente";
                String texto = String.format("ID: %d | %s | %s", tarea.getId(), estado, tarea.getTitulo());
                if (!tarea.getDescripcion().isEmpty()) {
                    texto += " | " + tarea.getDescripcion();
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

