package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.controller.Empleado;

public class MainView {
    private Stage stage;
    private Label welcomeLabel;
    private Button agregarButton;
    private Button editarButton;
    private Button eliminarButton;
    private Button listarButton;
    private Button buscarButton;
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
        
        buttonBar.getChildren().addAll(agregarButton, editarButton, eliminarButton, listarButton, buscarButton);
        
        centerContent = new VBox(10);
        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.TOP_CENTER);
        
        root.setTop(topBar);
        root.setCenter(buttonBar);
        root.setBottom(centerContent);
        
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
    
    public VBox getCenterContent() {
        return centerContent;
    }
    
    public Stage getStage() {
        return stage;
    }
}

