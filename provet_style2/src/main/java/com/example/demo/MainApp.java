package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage ventanaPrincipal;

    @Override
    public void start(Stage stage) throws Exception {
        ventanaPrincipal = stage;
        ventanaPrincipal.setTitle("Bovipro - Veterinaria de Ganado");
        irA("views/dashboard.fxml");
        ventanaPrincipal.centerOnScreen();
        ventanaPrincipal.show();
    }

    // Método estático para cambiar de vista desde cualquier controller
    public static void irA(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            MainApp.class.getResource(fxml)
        );
        Parent vista = loader.load();
        Scene escena = new Scene(vista);
        ventanaPrincipal.setScene(escena);
        ventanaPrincipal.centerOnScreen();
    }

    public static Stage getVentana() {
        return ventanaPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
