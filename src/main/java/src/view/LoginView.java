package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private Stage stage;
    private TextField idField;
    private Button loginButton;
    private Button registerButton;
    
    public LoginView(Stage primaryStage) {
        this.stage = primaryStage;
        createView();
    }
    
    private void createView() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("To-Do List App");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label idLabel = new Label("ID de Empleado:");
        idField = new TextField();
        idField.setPromptText("Ingrese su ID");
        idField.setPrefWidth(250);
        
        loginButton = new Button("Iniciar Sesión");
        loginButton.setPrefWidth(250);
        loginButton.setStyle("-fx-font-size: 14px;");
        
        registerButton = new Button("Registrarse");
        registerButton.setPrefWidth(250);
        registerButton.setStyle("-fx-font-size: 14px;");
        
        root.getChildren().addAll(titleLabel, idLabel, idField, loginButton, registerButton);
        
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Login - To-Do List App");
        stage.setResizable(false);
    }
    
    public TextField getIdField() {
        return idField;
    }
    
    public Button getLoginButton() {
        return loginButton;
    }
    
    public Button getRegisterButton() {
        return registerButton;
    }
    
    public Stage getStage() {
        return stage;
    }
}

