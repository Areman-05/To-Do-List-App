package src.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.controller.GestorTareas;
import src.controller.Tarea;
import java.util.List;

public class BuscarTareaDialog {
    private Stage stage;
    private TextField buscarField;
    private Button buscarButton;
    private ListView<String> resultadosList;
    private GestorTareas gestorTareas;
    
    public BuscarTareaDialog(Stage parentStage, GestorTareas gestorTareas) {
        this.stage = new Stage();
        this.stage.initOwner(parentStage);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.gestorTareas = gestorTareas;
        createView();
    }
    
    private void createView() {
        BorderPane root = new BorderPane();
        
        VBox topBox = new VBox(10);
        topBox.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Buscar Tareas");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        HBox buscarBox = new HBox(10);
        buscarField = new TextField();
        buscarField.setPromptText("Ingrese palabra clave para buscar");
        buscarField.setPrefWidth(300);
        
        buscarButton = new Button("Buscar");
        buscarButton.setPrefWidth(100);
        
        buscarBox.getChildren().addAll(buscarField, buscarButton);
        topBox.getChildren().addAll(titleLabel, buscarBox);
        
        resultadosList = new ListView<>();
        resultadosList.setPrefHeight(350);
        
        root.setTop(topBox);
        root.setCenter(resultadosList);
        
        buscarButton.setOnAction(e -> realizarBusqueda());
        buscarField.setOnAction(e -> realizarBusqueda());
        
        Scene scene = new Scene(root, 550, 450);
        stage.setScene(scene);
        stage.setTitle("Buscar Tareas");
        stage.setResizable(false);
    }
    
    private void realizarBusqueda() {
        String palabraClave = buscarField.getText().trim();
        if (palabraClave.isEmpty()) {
            showAlert("Error", "Por favor ingrese una palabra clave.", Alert.AlertType.ERROR);
            return;
        }
        
        List<Tarea> todasTareas = gestorTareas.obtenerTareas();
        ObservableList<String> resultados = FXCollections.observableArrayList();
        
        for (Tarea tarea : todasTareas) {
            if (tarea.getTitulo().toLowerCase().contains(palabraClave.toLowerCase()) ||
                tarea.getDescripcion().toLowerCase().contains(palabraClave.toLowerCase())) {
                String estado = tarea.isCompletada() ? "✔" : "✘";
                String texto = String.format("ID: %d | %s | Título: %s | Descripción: %s", 
                    tarea.getId(), estado, tarea.getTitulo(), tarea.getDescripcion());
                resultados.add(texto);
            }
        }
        
        if (resultados.isEmpty()) {
            resultados.add("No se encontraron tareas con esa palabra clave.");
        }
        
        resultadosList.setItems(resultados);
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void show() {
        stage.show();
    }
}

