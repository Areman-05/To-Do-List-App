package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import src.controller.GestorTareas;
import src.controller.Tarea;

public class AgregarTareaDialog {
    private Stage stage;
    private TextField tituloField;
    private TextArea descripcionArea;
    private Button agregarButton;
    private Button cancelarButton;
    private GestorTareas gestorTareas;
    
    public AgregarTareaDialog(Stage parentStage) {
        this.stage = new Stage();
        this.stage.initOwner(parentStage);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.gestorTareas = new GestorTareas();
        createView();
    }
    
    private void createView() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Agregar Nueva Tarea");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label tituloLabel = new Label("Título:");
        tituloField = new TextField();
        tituloField.setPromptText("Ingrese el título de la tarea");
        tituloField.setPrefWidth(400);
        
        Label descripcionLabel = new Label("Descripción:");
        descripcionArea = new TextArea();
        descripcionArea.setPromptText("Ingrese la descripción (opcional)");
        descripcionArea.setPrefWidth(400);
        descripcionArea.setPrefRowCount(5);
        
        agregarButton = new Button("Agregar");
        agregarButton.setPrefWidth(150);
        
        cancelarButton = new Button("Cancelar");
        cancelarButton.setPrefWidth(150);
        
        root.getChildren().addAll(titleLabel, tituloLabel, tituloField, descripcionLabel, descripcionArea, agregarButton, cancelarButton);
        
        agregarButton.setOnAction(e -> handleAgregar());
        cancelarButton.setOnAction(e -> stage.close());
        
        Scene scene = new Scene(root, 450, 350);
        stage.setScene(scene);
        stage.setTitle("Agregar Tarea");
        stage.setResizable(false);
    }
    
    private void handleAgregar() {
        String titulo = tituloField.getText().trim();
        String descripcion = descripcionArea.getText().trim();
        
        if (titulo.isEmpty()) {
            showAlert("Error", "El título no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }
        
        if (gestorTareas.existeTitulo(titulo)) {
            showAlert("Error", "Ya existe una tarea con ese título.", Alert.AlertType.ERROR);
            return;
        }
        
        int nuevoId = gestorTareas.generarNuevoId();
        Tarea nuevaTarea = new Tarea(nuevoId, titulo, descripcion);
        gestorTareas.agregarTarea(nuevaTarea);
        
        showAlert("Éxito", "Tarea agregada con ID " + nuevoId + ".", Alert.AlertType.INFORMATION);
        stage.close();
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

