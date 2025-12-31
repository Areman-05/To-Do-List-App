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
    private Label contadorDescripcion;
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
        
        Label tituloLabel = new Label("Título: (máx. 100 caracteres)");
        tituloField = new TextField();
        tituloField.setPromptText("Ingrese el título de la tarea");
        tituloField.setPrefWidth(400);
        tituloField.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= 100) {
                return change;
            }
            return null;
        }));
        
        Label descripcionLabel = new Label("Descripción: (máx. 500 caracteres)");
        descripcionArea = new TextArea();
        descripcionArea.setPromptText("Ingrese la descripción (opcional)");
        descripcionArea.setPrefWidth(400);
        descripcionArea.setPrefRowCount(5);
        descripcionArea.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= 500) {
                return change;
            }
            return null;
        }));
        
        contadorDescripcion = new Label("0/500 caracteres");
        contadorDescripcion.setStyle("-fx-font-size: 11px; -fx-text-fill: #6c757d;");
        descripcionArea.textProperty().addListener((obs, oldText, newText) -> {
            contadorDescripcion.setText(newText.length() + "/500 caracteres");
        });
        
        agregarButton = new Button("Agregar");
        agregarButton.setPrefWidth(150);
        agregarButton.setDefaultButton(true);
        
        cancelarButton = new Button("Cancelar");
        cancelarButton.setPrefWidth(150);
        
        root.getChildren().addAll(titleLabel, tituloLabel, tituloField, descripcionLabel, descripcionArea, contadorDescripcion, agregarButton, cancelarButton);
        
        agregarButton.setOnAction(e -> handleAgregar());
        cancelarButton.setOnAction(e -> stage.close());
        tituloField.setOnAction(e -> agregarButton.fire());
        
        Scene scene = new Scene(root, 450, 350);
        scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Agregar Tarea");
        stage.setResizable(false);
    }
    
    private void handleAgregar() {
        String titulo = tituloField.getText().trim();
        String descripcion = descripcionArea.getText().trim();
        
        if (titulo.isEmpty()) {
            showAlert("Error", "El título no puede estar vacío.", Alert.AlertType.ERROR);
            tituloField.requestFocus();
            return;
        }
        
        if (titulo.length() > 100) {
            showAlert("Error", "El título no puede exceder 100 caracteres.", Alert.AlertType.ERROR);
            tituloField.selectAll();
            tituloField.requestFocus();
            return;
        }
        
        if (gestorTareas.existeTitulo(titulo)) {
            showAlert("Error de duplicado", 
                "Ya existe una tarea con el título: \"" + titulo + "\"\n\nPor favor, use un título diferente.", 
                Alert.AlertType.ERROR);
            tituloField.selectAll();
            tituloField.requestFocus();
            return;
        }
        
        try {
            int nuevoId = gestorTareas.generarNuevoId();
            Tarea nuevaTarea = new Tarea(nuevoId, titulo, descripcion);
            gestorTareas.agregarTarea(nuevaTarea);
            showAlert("Tarea agregada", 
                "La tarea \"" + titulo + "\" ha sido agregada exitosamente.\nID asignado: " + nuevoId, 
                Alert.AlertType.INFORMATION);
            stage.close();
        } catch (Exception e) {
            showAlert("Error", "No se pudo agregar la tarea: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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

