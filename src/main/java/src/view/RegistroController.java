package src.view;

import javafx.scene.control.Alert;
import src.controller.GestorEmpleados;

public class RegistroController {
    private RegistroView registroView;
    private GestorEmpleados gestorEmpleados;
    private LoginController loginController;
    
    public RegistroController(RegistroView registroView, LoginController loginController) {
        this.registroView = registroView;
        this.loginController = loginController;
        this.gestorEmpleados = new GestorEmpleados();
        setupHandlers();
    }
    
    private void setupHandlers() {
        registroView.getRegistrarButton().setOnAction(e -> handleRegistro());
        registroView.getVolverButton().setOnAction(e -> registroView.close());
    }
    
    private void handleRegistro() {
        try {
            String idText = registroView.getIdField().getText().trim();
            if (idText.isEmpty()) {
                showAlert("Error", "Por favor ingrese un ID", Alert.AlertType.ERROR);
                return;
            }
            
            int id = Integer.parseInt(idText);
            String rol = registroView.getRolCombo().getValue();
            
            if (gestorEmpleados.registrarEmpleado(id, rol)) {
                showAlert("Éxito", "Empleado registrado exitosamente como " + rol, Alert.AlertType.INFORMATION);
                registroView.close();
            } else {
                showAlert("Error", "Ese ID ya está registrado. Intente con otro.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "El ID debe ser un número válido", Alert.AlertType.ERROR);
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

