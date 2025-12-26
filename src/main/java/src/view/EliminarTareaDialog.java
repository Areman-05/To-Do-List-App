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

public class EliminarTareaDialog {
    private Stage stage;
    private ComboBox<Tarea> tareaCombo;
    private Button eliminarButton;
    private Button cancelarButton;
    private GestorTareas gestorTareas;
    
    public EliminarTareaDialog(Stage parentStage, GestorTareas gestorTareas) {
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
        
        Label titleLabel = new Label("Eliminar Tarea");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label tareaLabel = new Label("Seleccione la tarea a eliminar:");
        tareaCombo = new ComboBox<>();
        cargarTareas();
        tareaCombo.setPrefWidth(400);
        
        eliminarButton = new Button("Eliminar");
        eliminarButton.setPrefWidth(150);
        eliminarButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        
        cancelarButton = new Button("Cancelar");
        cancelarButton.setPrefWidth(150);
        
        root.getChildren().addAll(titleLabel, tareaLabel, tareaCombo, eliminarButton, cancelarButton);
        
        eliminarButton.setOnAction(e -> handleEliminar());
        cancelarButton.setOnAction(e -> stage.close());
        
        Scene scene = new Scene(root, 450, 250);
        scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Eliminar Tarea");
        stage.setResizable(false);
    }
    
    private void cargarTareas() {
        List<Tarea> tareas = gestorTareas.obtenerTareas();
        tareaCombo.getItems().clear();
        tareaCombo.getItems().addAll(tareas);
    }
    
    private void handleEliminar() {
        Tarea tarea = tareaCombo.getValue();
        if (tarea == null) {
            showAlert("Error", "Por favor seleccione una tarea.", Alert.AlertType.ERROR);
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmar eliminación");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("¿Está seguro de que desea eliminar la tarea: " + tarea.getTitulo() + "?");
        
        if (confirmAlert.showAndWait().get().getButtonData().isDefaultButton()) {
            gestorTareas.eliminarTarea(tarea.getId());
            showAlert("Éxito", "Tarea eliminada correctamente.", Alert.AlertType.INFORMATION);
            stage.close();
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

