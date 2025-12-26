package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.controller.GestorTareas;
import src.controller.Tarea;
import java.util.List;

public class EditarTareaDialog {
    private Stage stage;
    private ComboBox<Tarea> tareaCombo;
    private TextField tituloField;
    private TextArea descripcionArea;
    private Button guardarButton;
    private Button cancelarButton;
    private GestorTareas gestorTareas;
    
    public EditarTareaDialog(Stage parentStage, GestorTareas gestorTareas) {
        this.stage = new Stage();
        this.stage.initOwner(parentStage);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.gestorTareas = gestorTareas;
        createView();
    }
    
    private void createView() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Editar Tarea");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label tareaLabel = new Label("Seleccione la tarea:");
        tareaCombo = new ComboBox<>();
        cargarTareas();
        tareaCombo.setPrefWidth(400);
        tareaCombo.setOnAction(e -> cargarDatosTarea());
        
        Label tituloLabel = new Label("Título:");
        tituloField = new TextField();
        tituloField.setPrefWidth(400);
        
        Label descripcionLabel = new Label("Descripción:");
        descripcionArea = new TextArea();
        descripcionArea.setPrefWidth(400);
        descripcionArea.setPrefRowCount(5);
        
        guardarButton = new Button("Guardar");
        guardarButton.setPrefWidth(150);
        
        cancelarButton = new Button("Cancelar");
        cancelarButton.setPrefWidth(150);
        
        root.getChildren().addAll(titleLabel, tareaLabel, tareaCombo, tituloLabel, tituloField, descripcionLabel, descripcionArea, guardarButton, cancelarButton);
        
        guardarButton.setOnAction(e -> handleGuardar());
        cancelarButton.setOnAction(e -> stage.close());
        
        Scene scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.setTitle("Editar Tarea");
        stage.setResizable(false);
    }
    
    private void cargarTareas() {
        List<Tarea> tareas = gestorTareas.obtenerTareas();
        tareaCombo.getItems().clear();
        tareaCombo.getItems().addAll(tareas);
    }
    
    private void cargarDatosTarea() {
        Tarea tarea = tareaCombo.getValue();
        if (tarea != null) {
            tituloField.setText(tarea.getTitulo());
            descripcionArea.setText(tarea.getDescripcion());
        }
    }
    
    private void handleGuardar() {
        Tarea tarea = tareaCombo.getValue();
        if (tarea == null) {
            showAlert("Error", "Por favor seleccione una tarea.", Alert.AlertType.ERROR);
            return;
        }
        
        String nuevoTitulo = tituloField.getText().trim();
        String nuevaDescripcion = descripcionArea.getText().trim();
        
        if (nuevoTitulo.isEmpty()) {
            showAlert("Error", "El título no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }
        
        gestorTareas.editarTarea(tarea.getId(), nuevoTitulo, nuevaDescripcion);
        showAlert("Éxito", "Tarea editada correctamente.", Alert.AlertType.INFORMATION);
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

