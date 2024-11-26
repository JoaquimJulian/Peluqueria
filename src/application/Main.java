package application;

import java.lang.reflect.Method;
import application.controllers.*;
import application.models.Servicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    private Stage primaryStage; // Almacena la ventana principal
    
    private Object datosCompartidos; //Almacena los datos del controlador 
    
    public void setDatosCompartidos(Object datos) {
        this.datosCompartidos = datos;
    }

    public Object getDatosCompartidos() {
        return datosCompartidos;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Guarda la referencia a la ventana principal
        mostrarVista("logInPrincipal.fxml"); // Muestra la vista de inicio de sesión al inicio
        
        
        primaryStage.setFullScreen(true);
    }

    public void mostrarVista(String vista) { //sobrecargamos el metodo para que no haga falta pasarle dos parametros siempre
    	mostrarVista(vista, null);
    }
    
    public void mostrarVista(String vista, Object datos) { //Y aqui le pasamos la vista y los datos del controlador si los necesita la vista que se va a ejecutar
    	try {
    		setDatosCompartidos(datos);  //digo que la variable datosCompartidos va acontener la información que ha enviado el controlador
    		
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/" + vista));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(vista);
            
            primaryStage.setFullScreen(true);
            
            
            // Obtener el controlador de la vista cargada
            Object controller = loader.getController();

            // Compruebo si el controlador cargado contiene el metodo 'setMainApp' y lo ejecuta para proporcionar una referencia de main al controlador.
            Method method = controller.getClass().getMethod("setMainApp", Main.class);
            method.invoke(controller, this);
            
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
