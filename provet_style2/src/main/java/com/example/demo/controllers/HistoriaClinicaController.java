package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HistoriaClinicaController {

    @FXML private TextField txtFecha, txtHora, txtMedico, txtNumHC;
    @FXML private TextField txtNombrePaciente, txtRaza, txtColor, txtFechaNac, txtEdad, txtSenas;
    @FXML private TextField txtPropietario, txtTelefono, txtDireccion, txtMunicipio;
    @FXML private TextField txtVacCarbon, txtVacBrucela, txtVacIBR, txtVacOtra;
    @FXML private TextField txtDesProducto, txtDesFecha;
    @FXML private TextField txtLlenadoCap, txtFC, txtFR, txtPulso, txtTemp, txtPeso;
    @FXML private TextArea txtMotivo, txtAnamnesis, txtAlergias, txtEnfermedades, txtCirugias;
    @FXML private ComboBox<String> cmbEspecie, cmbSexo, cmbAlimentacion, cmbReproductivo;
    @FXML private RadioButton rbUrbana, rbRural, rbDesSi, rbDesNo;
    @FXML private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbEspecie.getItems().add("Bovino");
        cmbEspecie.setValue("Bovino");
        cmbEspecie.setDisable(true);

        cmbSexo.getItems().addAll("Macho", "Hembra");
        cmbAlimentacion.getItems().addAll("Pastoreo", "Establo", "Mixta", "Suplementada");
        cmbReproductivo.getItems().addAll("Entero", "Castrado", "Gestación", "Lactancia");

        // Agrupar radio buttons
        ToggleGroup grupoProcedencia = new ToggleGroup();
        rbUrbana.setToggleGroup(grupoProcedencia);
        rbRural.setToggleGroup(grupoProcedencia);
        rbRural.setSelected(true);

        ToggleGroup grupoDes = new ToggleGroup();
        rbDesSi.setToggleGroup(grupoDes);
        rbDesNo.setToggleGroup(grupoDes);

        // Fecha automática
        txtFecha.setText(java.time.LocalDate.now().toString());
    }

    @FXML
    public void onGuardar() {
        String paciente = txtNombrePaciente.getText().trim();
        String propietario = txtPropietario.getText().trim();
        String motivo = txtMotivo.getText().trim();

        if (paciente.isEmpty() || propietario.isEmpty() || motivo.isEmpty()) {
            lblMensaje.setText("⚠ Completa al menos: paciente, propietario y motivo.");
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        lblMensaje.setText("✔ Historia clínica guardada correctamente.");
        lblMensaje.setStyle("-fx-text-fill: #2ecc71;");
    }

    @FXML
    public void irExamenClinico() {
        try { MainApp.irA("views/examen_clinico.fxml"); }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }

    @FXML
    public void irHistorial() {
        try { MainApp.irA("views/historial.fxml"); }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
