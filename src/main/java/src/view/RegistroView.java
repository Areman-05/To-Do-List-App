package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistroView {
    private Stage stage;
    private TextField idField;
    private ComboBox<String> rolCombo;
    private Button registrarButton;
    private Button volverButton;
    
    public RegistroView(Stage parentStage) {
        this.stage = new Stage();
        this.stage.initOwner(parentStage);
        createView();
    }
    
    private void createView() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Registro de Empleado");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label idLabel = new Label("ID de Empleado:");
        idField = new TextField();
        idField.setPromptText("Ingrese un nuevo ID");
        idField.setPrefWidth(250);
        
        Label rolLabel = new Label("Rol:");
        rolCombo = new ComboBox<>();
        rolCombo.getItems().addAll("empleado", "admin");
        rolCombo.setValue("empleado");
        rolCombo.setPrefWidth(250);
        
        registrarButton = new Button("Registrarse");
        registrarButton.setPrefWidth(250);
        registrarButton.setStyle("-fx-font-size: 14px;");
        
        volverButton = new Button("Volver");
        volverButton.setPrefWidth(250);
        volverButton.setStyle("-fx-font-size: 14px;");
        
        root.getChildren().addAll(titleLabel, idLabel, idField, rolLabel, rolCombo, registrarButton, volverButton);
        
        Scene scene = new Scene(root, 400, 350);
        scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Registro - To-Do List App");
        stage.setResizable(false);
    }
    
    public TextField getIdField() {
        return idField;
    }
    
    public ComboBox<String> getRolCombo() {
        return rolCombo;
    }
    
    public Button getRegistrarButton() {
        return registrarButton;
    }
    
    public Button getVolverButton() {
        return volverButton;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
}

