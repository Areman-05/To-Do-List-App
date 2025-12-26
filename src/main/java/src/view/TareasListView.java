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
        
        Label titleLabel = new Label("Lista de Tareas");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        BorderPane.setMargin(titleLabel, new Insets(15));
        
        tareasList = new ListView<>();
        tareasList.setPrefHeight(400);
        
        root.setTop(titleLabel);
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
            items.add("No hay tareas disponibles.");
        } else {
            for (Tarea tarea : tareas) {
                String estado = tarea.isCompletada() ? "✔" : "✘";
                String texto = String.format("ID: %d | %s | Título: %s | Descripción: %s", 
                    tarea.getId(), estado, tarea.getTitulo(), tarea.getDescripcion());
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

