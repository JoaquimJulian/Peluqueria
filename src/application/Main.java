package application;

import application.controllers.*;
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
            
            // Obtener el controlador de la vista cargada
            Object controller = loader.getController();

            // Verificar si el controlador tiene el método setMainApp
            if (controller instanceof LogInController) {
                ((LogInController) controller).setMainApp(this);
            } else if (controller instanceof diaAdminController) {
                ((diaAdminController) controller).setMainApp(this);
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
