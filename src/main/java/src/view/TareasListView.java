package src.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.controller.GestorTareas;
import src.controller.Tarea;
import java.util.List;

public class TareasListView {
    private Stage stage;
    private GestorTareas gestorTareas;
    private ListView<String> tareasList;
    
    public TareasListView(Stage parentStage, GestorTareas gestorTareas) {
        this.stage = new Stage();
        this.stage.initOwner(parentStage);
        this.gestorTareas = gestorTareas;
        createView();
        cargarTareas();
    }
    
    private void createView() {
        BorderPane root = new BorderPane();
        
        VBox headerBox = new VBox(5);
        Label titleLabel = new Label("📋 Lista de Tareas");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label subtitleLabel = new Label("Vista completa de todas las tareas registradas");
        subtitleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        
        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        headerBox.setPadding(new Insets(15));
        
        tareasList = new ListView<>();
        tareasList.setPrefHeight(400);
        
        root.setTop(headerBox);
        root.setCenter(tareasList);
        
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Lista de Tareas");
    }
    
    private void cargarTareas() {
        List<Tarea> tareas = gestorTareas.obtenerTareas();
        ObservableList<String> items = FXCollections.observableArrayList();
        
        if (tareas.isEmpty()) {
            items.add("📝 No hay tareas disponibles");
            items.add("");
            items.add("Use el botón 'Agregar Tarea' en la ventana principal para crear una nueva tarea.");
        } else {
            items.add("📋 Total de tareas: " + tareas.size());
            items.add("");
            for (Tarea tarea : tareas) {
                String estado = tarea.isCompletada() ? "✓ Completada" : "○ Pendiente";
                String texto = String.format("ID: %d | %s | %s", 
                    tarea.getId(), estado, tarea.getTitulo());
                if (!tarea.getDescripcion().isEmpty()) {
                    String descCorta = tarea.getDescripcion().length() > 60 
                        ? tarea.getDescripcion().substring(0, 60) + "..." 
                        : tarea.getDescripcion();
                    texto += "\n   Descripción: " + descCorta;
                }
                items.add(texto);
            }
        }
        
        tareasList.setItems(items);
    }
    
    public void show() {
        stage.show();
        cargarTareas();
    }
}

