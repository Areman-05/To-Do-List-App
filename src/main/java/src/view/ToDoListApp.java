package src.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class ToDoListApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView(primaryStage);
        LoginController loginController = new LoginController(loginView);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

