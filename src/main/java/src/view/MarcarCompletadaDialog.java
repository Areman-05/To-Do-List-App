package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.controller.GestorTareas;
import src.controller.Tarea;
import java.util.List;

public class MarcarCompletadaDialog {
    private Stage stage;
    private ComboBox<Tarea> tareaCombo;
    private Button marcarButton;
    private Button desmarcarButton;
    private Button cancelarButton;
    private GestorTareas gestorTareas;
    
    public MarcarCompletadaDialog(Stage parentStage, GestorTareas gestorTareas) {
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
        
        Label titleLabel = new Label("Cambiar Estado de Tarea");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label tareaLabel = new Label("Seleccione la tarea:");
        tareaCombo = new ComboBox<>();
        cargarTareas();
        tareaCombo.setPrefWidth(400);
        
        marcarButton = new Button("Marcar como Completada");
        marcarButton.setPrefWidth(200);
        marcarButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        
        desmarcarButton = new Button("Marcar como Pendiente");
        desmarcarButton.setPrefWidth(200);
        desmarcarButton.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black;");
        
        cancelarButton = new Button("Cancelar");
        cancelarButton.setPrefWidth(200);
        
        root.getChildren().addAll(titleLabel, tareaLabel, tareaCombo, marcarButton, desmarcarButton, cancelarButton);
        
        marcarButton.setOnAction(e -> handleMarcar(true));
        desmarcarButton.setOnAction(e -> handleMarcar(false));
        cancelarButton.setOnAction(e -> stage.close());
        
        Scene scene = new Scene(root, 450, 280);
        stage.setScene(scene);
        stage.setTitle("Cambiar Estado de Tarea");
        stage.setResizable(false);
    }
    
    private void cargarTareas() {
        List<Tarea> tareas = gestorTareas.obtenerTareas();
        tareaCombo.getItems().clear();
        tareaCombo.getItems().addAll(tareas);
    }
    
    private void handleMarcar(boolean completada) {
        Tarea tarea = tareaCombo.getValue();
        if (tarea == null) {
            showAlert("Error", "Por favor seleccione una tarea.", Alert.AlertType.ERROR);
            return;
        }
        
        if (gestorTareas.editarEstadoPorId(tarea.getId(), completada)) {
            String mensaje = completada ? "Tarea marcada como completada." : "Tarea marcada como pendiente.";
            showAlert("Éxito", mensaje, Alert.AlertType.INFORMATION);
            cargarTareas();
            stage.close();
        } else {
            showAlert("Información", "El estado de la tarea ya es el solicitado.", Alert.AlertType.INFORMATION);
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

