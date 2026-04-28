package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label lblBienvenida;

    @FXML
    public void initialize() {
        lblBienvenida.setText("Bienvenido, Veterinaria");
    }

    @FXML public void irVisitas()       { navegar("views/visitas.fxml"); }
    @FXML public void irPropietarios()  { navegar("views/propietarios.fxml"); }
    @FXML public void irBovinos()       { navegar("views/bovinos.fxml"); }
    @FXML public void irEmergencias()   { navegar("views/emergencias.fxml"); }
    @FXML public void irTratamiento()   { navegar("views/tratamiento.fxml"); }
    @FXML public void irInventario()    { navegar("views/inventario.fxml"); }
    @FXML public void irHistorial()     { navegar("views/historial.fxml"); }
    @FXML public void irDiagnostico()   { navegar("views/diagnostico.fxml"); }
    @FXML public void irExpediente()    { navegar("views/expediente.fxml"); }
    @FXML public void irReportes()      { navegar("views/reportes.fxml"); }
    @FXML public void irPerfil()        { navegar("views/perfil.fxml"); }

    @FXML
    public void cerrarSesion() { navegar("views/login.fxml"); }

    private void navegar(String fxml) {
        try {
            MainApp.irA(fxml);
        } catch (Exception e) {
            System.out.println("Error al navegar: " + e.getMessage());
        }
    }
}
