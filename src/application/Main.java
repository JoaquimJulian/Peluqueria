package application;

import application.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    private Stage primaryStage; // Almacena la ventana principal

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Guarda la referencia a la ventana principal
        mostrarVista("LogIn.fxml"); // Muestra la vista de inicio de sesión al inicio
    }

    public void mostrarVista(String vista) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/" + vista));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(vista);
            
            // Aquí, comprobamos si la vista es "LogIn.fxml"
            if (vista.equals("LogIn.fxml")) {
                LogInController controller = loader.getController();
                controller.setMainApp(this); // Establecer la referencia a Main
            }
            
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
