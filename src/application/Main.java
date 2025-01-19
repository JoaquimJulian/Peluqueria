package application;

import java.lang.reflect.Method;
import application.controllers.*;
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
    }
    
    public void mostrarVista(String vista) { 
        mostrarVista(vista, null);  // Sobrecarga el método para no hacer falta pasar siempre dos parámetros
    }
    
    public void mostrarVista(String vista, Object datos) { 
        try {
            setDatosCompartidos(datos);  // Se establece que la variable datosCompartidos va a contener los datos del controlador si los necesita la vista

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/" + vista));  // Crear el FXMLLoader
            Parent root = loader.load();  // Cargar la vista

            Scene scene = new Scene(root);  // Crear la escena
            primaryStage.setScene(scene);   // Establecer la escena en la ventana principal
            primaryStage.setTitle(vista);   // Establecer el título de la ventana

            // Obtener el controlador de la vista cargada
            Object controller = loader.getController();
            
            // Verificar si el controlador tiene el método 'setMainApp' y llamarlo
            if (controller != null) {
                Method method = controller.getClass().getMethod("setMainApp", Main.class);
                method.invoke(controller, this);
            }

            // Si la vista es "cobro.fxml", también necesitamos establecer el cobroController en "cobrarProductoController"
            if (vista.equals("cobro.fxml")) {
                // Obtener los controladores de las vistas
                cobroController cobroCtrl = loader.getController();  // Obtener el controlador de la vista "cobro.fxml"
                FXMLLoader loaderCobrarProducto = new FXMLLoader(getClass().getResource("/application/views/cobrarProducto.fxml"));
                loaderCobrarProducto.load();
                cobrarProductoController cobrarProductoCtrl = loaderCobrarProducto.getController();  // Obtener el controlador de la vista "cobrarProducto.fxml"
                
                // Establecer la referencia de cobroController en cobrarProductoController
                cobrarProductoCtrl.setCobroController(cobroCtrl);  
            }

            primaryStage.show();  // Mostrar la ventana

        } catch (Exception e) {
            e.printStackTrace();  // Imprimir el error en caso de que ocurra una excepción
        }    
    }        

    public static void main(String[] args) {
        launch(args);  // Lanzar la aplicación
    }
}
