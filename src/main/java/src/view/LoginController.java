package src.view;

import javafx.scene.control.Alert;
import src.controller.GestorEmpleados;
import src.controller.Empleado;

public class LoginController {
    private GestorEmpleados gestorEmpleados;
    private LoginView loginView;
    private RegistroView registroView;
    
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.gestorEmpleados = new GestorEmpleados();
        setupHandlers();
    }
    
    private void setupHandlers() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
        loginView.getRegisterButton().setOnAction(e -> showRegistroView());
    }
    
    private void handleLogin() {
        try {
            String idText = loginView.getIdField().getText().trim();
            if (idText.isEmpty()) {
                showAlert("Error", "Por favor ingrese un ID", Alert.AlertType.ERROR);
                loginView.getIdField().requestFocus();
                return;
            }
            
            int id = Integer.parseInt(idText);
            if (id <= 0) {
                showAlert("Error", "El ID debe ser un número positivo", Alert.AlertType.ERROR);
                loginView.getIdField().requestFocus();
                return;
            }
            
            if (gestorEmpleados.verificarEmpleado(id)) {
                Empleado empleado = gestorEmpleados.obtenerEmpleado(id);
                showMainView(empleado);
            } else {
                showAlert("Error", "ID no registrado. Por favor regístrese primero.", Alert.AlertType.ERROR);
                loginView.getIdField().selectAll();
                loginView.getIdField().requestFocus();
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "El ID debe ser un número válido", Alert.AlertType.ERROR);
            loginView.getIdField().selectAll();
            loginView.getIdField().requestFocus();
        }
    }
    
    private void showRegistroView() {
        if (registroView == null) {
            registroView = new RegistroView(loginView.getStage());
            RegistroController registroController = new RegistroController(registroView, this);
        }
        registroView.show();
    }
    
    private void showMainView(Empleado empleado) {
        MainView mainView = new MainView(loginView.getStage());
        MainController mainController = new MainController(mainView, empleado);
        loginView.getStage().close();
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

