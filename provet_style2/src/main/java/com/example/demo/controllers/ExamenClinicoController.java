package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExamenClinicoController {

    @FXML private ComboBox<String> cmbActitud, cmbCondicion, cmbHidratacion;
    @FXML private TextField txtMucConjuntival, txtMucOral, txtMucRectal, txtMucVulvar;
    @FXML private TextArea txtCardiovascular, txtRespiratorio, txtDigestivo;
    @FXML private TextArea txtMusculo, txtNervioso, txtGenitourinario;
    @FXML private TextArea txtPiel, txtNodulos, txtLocomocion;
    @FXML private TextArea txtSignos, txtPosiEnfermedades, txtDiagDefinitivo;
    @FXML private TextField txtFechaControl;
    @FXML private TableView<String[]> tablaPlan;
    @FXML private TableColumn<String[], String> colTipo, colPrincipio, colPresentacion;
    @FXML private TableColumn<String[], String> colDosis, colVia, colFrecuencia;
    @FXML private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbActitud.getItems().addAll("Normal", "Asténico", "Apoplético", "Linfático");
        cmbCondicion.getItems().addAll("Normal", "Caquéctico", "Delgado", "Sobrepeso", "Obeso");
        cmbHidratacion.getItems().addAll("Normal", "Deshidratación 0-5%", "6-7%", "8-9%", "+10%");

        colTipo.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));
        colPrincipio.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));
        colPresentacion.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));
        colDosis.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[3]));
        colVia.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[4]));
        colFrecuencia.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[5]));

        // Ejemplo simulado
        tablaPlan.getItems().add(
            new String[]{"Sintomático", "Oxitetraciclina 20%", "Inyectable", "10ml", "IM", "Cada 24h por 5 días"}
        );
    }

    @FXML
    public void onAgregarMedicamento() {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Agregar Medicamento");
        dialog.setHeaderText(null);
        dialog.setContentText("Aquí se abrirá el formulario para agregar un medicamento al plan.");
        dialog.showAndWait();
    }

    @FXML
    public void onGuardar() {
        String diagnostico = txtDiagDefinitivo.getText().trim();
        if (diagnostico.isEmpty()) {
            lblMensaje.setText("⚠ Ingresa al menos el diagnóstico definitivo.");
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }
        lblMensaje.setText("✔ Examen clínico guardado correctamente.");
        lblMensaje.setStyle("-fx-text-fill: #2ecc71;");
    }

    @FXML
    public void irHistoriaClinica() {
        try { MainApp.irA("views/historia_clinica.fxml"); }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }

    @FXML
    public void irDashboard() {
        try { MainApp.irA("views/dashboard.fxml"); }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
