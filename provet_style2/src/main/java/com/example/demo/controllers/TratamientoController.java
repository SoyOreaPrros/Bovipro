package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class TratamientoController {

    // Cada tratamiento guardado es un objeto interno
    // La tabla de la Lista muestra: animal, resumen de medicamentos, estado, fecha

    // ── Pestaña Agregar ──
    @FXML private ComboBox<String> cmbMascota, cmbEstado;
    @FXML private TextField txtPrincipioActivo, txtDosis, txtVia, txtFrecuencia;
    @FXML private TableView<String[]> tablaMedicamentos;
    @FXML private TableColumn<String[], String> colMedicamento, colDosis, colVia, colFrecuencia;
    @FXML private TextArea txtIndicaciones;
    @FXML private TextField txtFechaControl;
    @FXML private Label lblMensajeAgg;

    // ── Pestaña Editar ──
    @FXML private ComboBox<String> cmbMascotaE, cmbEstadoE;
    @FXML private TextField txtPrincipioActivoE, txtDosisE, txtViaE, txtFrecuenciaE;
    @FXML private TableView<String[]> tablaMedicamentosE;
    @FXML private TableColumn<String[], String> colMedicamentoE, colDosisE, colViaE, colFrecuenciaE;
    @FXML private TextArea txtIndicacionesE;
    @FXML private TextField txtFechaControlE;
    @FXML private Label lblMensajeEdit;

    // ── Pestaña Lista ──
    @FXML private TableView<Tratamiento> tablaTratamientos;
    @FXML private TableColumn<Tratamiento, String> colMascota, colResumen, colEstado, colFecha;
    @FXML private Label lblMensajeLista;

    @FXML private TabPane tabsTrat;

    // Lista de tratamientos completos
    private ObservableList<Tratamiento> tratamientos;
    // Lista de medicamentos en formulario Agregar
    private ObservableList<String[]> medicamentosAgg;
    // Lista de medicamentos en formulario Editar
    private ObservableList<String[]> medicamentosEdit;
    private int indiceEditar = -1;

    // Clase interna para representar un tratamiento completo
    public static class Tratamiento {
        public String animal;
        public String estado;
        public String fechaControl;
        public String indicaciones;
        public List<String[]> medicamentos; // cada String[]: [0]=med [1]=dosis [2]=via [3]=frec

        public Tratamiento(String animal, String estado, String fechaControl,
                           String indicaciones, List<String[]> medicamentos) {
            this.animal       = animal;
            this.estado       = estado;
            this.fechaControl = fechaControl;
            this.indicaciones = indicaciones;
            this.medicamentos = new ArrayList<>(medicamentos);
        }

        public String getResumen() {
            if (medicamentos.isEmpty()) return "Sin medicamentos";
            StringBuilder sb = new StringBuilder();
            for (String[] m : medicamentos) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(m[0]);
            }
            return sb.toString();
        }
    }

    @FXML
    public void initialize() {
        String[] animales = {"Toro #14", "Vaca #07", "Becerra #03", "Toro #22", "Vaca #11"};
        String[] estados  = {"Activo", "Finalizado", "Cancelado"};

        cmbMascota.getItems().addAll(animales);
        cmbEstado.getItems().addAll(estados);
        cmbEstado.setValue("Activo");

        cmbMascotaE.getItems().addAll(animales);
        cmbEstadoE.getItems().addAll(estados);

        // Listas de medicamentos
        medicamentosAgg  = FXCollections.observableArrayList();
        medicamentosEdit = FXCollections.observableArrayList();

        // Columnas tabla Agregar
        colMedicamento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[0]));
        colDosis.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[1]));
        colVia.setCellValueFactory(d         -> new SimpleStringProperty(d.getValue()[2]));
        colFrecuencia.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue()[3]));
        tablaMedicamentos.setItems(medicamentosAgg);

        // Columnas tabla Editar
        colMedicamentoE.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[0]));
        colDosisE.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[1]));
        colViaE.setCellValueFactory(d         -> new SimpleStringProperty(d.getValue()[2]));
        colFrecuenciaE.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue()[3]));
        tablaMedicamentosE.setItems(medicamentosEdit);

        // Columnas tabla Lista
        colMascota.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue().animal));
        colResumen.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue().getResumen()));
        colEstado.setCellValueFactory(d   -> new SimpleStringProperty(d.getValue().estado));
        colFecha.setCellValueFactory(d    -> new SimpleStringProperty(d.getValue().fechaControl));

        // Datos de ejemplo
        tratamientos = FXCollections.observableArrayList();
        List<String[]> meds1 = new ArrayList<>();
        meds1.add(new String[]{"Oxitetraciclina", "10ml", "IM", "Cada 24h"});
        meds1.add(new String[]{"Vitamina B12",    "5ml",  "SC", "Cada 48h"});
        tratamientos.add(new Tratamiento("Toro #14", "Activo", "30/04/2026",
            "Mantener en reposo, dieta blanda.", meds1));

        List<String[]> meds2 = new ArrayList<>();
        meds2.add(new String[]{"Ivermectina", "5ml", "SC", "Dosis única"});
        tratamientos.add(new Tratamiento("Becerra #03", "Finalizado", "15/03/2026",
            "", meds2));

        tablaTratamientos.setItems(tratamientos);
    }

    // ═══ PESTAÑA AGREGAR ═══
    @FXML
    public void onAgregarMedicamento() {
        String med  = txtPrincipioActivo.getText().trim();
        String dos  = txtDosis.getText().trim();
        String via  = txtVia.getText().trim();
        String frec = txtFrecuencia.getText().trim();
        if (med.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ Escribe el principio activo antes de agregar.", false);
            return;
        }
        medicamentosAgg.add(new String[]{med, dos, via, frec});
        txtPrincipioActivo.clear(); txtDosis.clear(); txtVia.clear(); txtFrecuencia.clear();
        lblMensajeAgg.setText("");
    }

    @FXML
    public void onQuitarMedicamento() {
        String[] sel = tablaMedicamentos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeAgg, "⚠ Selecciona un medicamento de la tabla.", false);
            return;
        }
        medicamentosAgg.remove(sel);
    }

    @FXML
    public void onGuardar() {
        String mascota = cmbMascota.getValue();
        if (mascota == null) {
            setMensaje(lblMensajeAgg, "⚠ Selecciona un animal.", false);
            return;
        }
        if (medicamentosAgg.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ Agrega al menos un medicamento al plan.", false);
            return;
        }
        tratamientos.add(new Tratamiento(
            mascota,
            cmbEstado.getValue() != null ? cmbEstado.getValue() : "Activo",
            txtFechaControl.getText().trim(),
            txtIndicaciones.getText().trim(),
            new ArrayList<>(medicamentosAgg)
        ));
        setMensaje(lblMensajeAgg, "✔ Tratamiento registrado para " + mascota + ".", true);
        onLimpiarAgg();
        tabsTrat.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarAgg() {
        cmbMascota.setValue(null); cmbEstado.setValue("Activo");
        txtPrincipioActivo.clear(); txtDosis.clear(); txtVia.clear(); txtFrecuencia.clear();
        medicamentosAgg.clear();
        txtIndicaciones.clear(); txtFechaControl.clear();
        lblMensajeAgg.setText("");
    }

    // ═══ PESTAÑA EDITAR ═══
    @FXML
    public void onAgregarMedicamentoE() {
        String med  = txtPrincipioActivoE.getText().trim();
        String dos  = txtDosisE.getText().trim();
        String via  = txtViaE.getText().trim();
        String frec = txtFrecuenciaE.getText().trim();
        if (med.isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ Escribe el principio activo antes de agregar.", false);
            return;
        }
        medicamentosEdit.add(new String[]{med, dos, via, frec});
        txtPrincipioActivoE.clear(); txtDosisE.clear(); txtViaE.clear(); txtFrecuenciaE.clear();
        lblMensajeEdit.setText("");
    }

    @FXML
    public void onQuitarMedicamentoE() {
        String[] sel = tablaMedicamentosE.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeEdit, "⚠ Selecciona un medicamento de la tabla.", false);
            return;
        }
        medicamentosEdit.remove(sel);
    }

    @FXML
    public void onGuardarCambios() {
        if (indiceEditar < 0) {
            setMensaje(lblMensajeEdit, "⚠ Primero selecciona un tratamiento desde la lista.", false);
            return;
        }
        if (cmbMascotaE.getValue() == null) {
            setMensaje(lblMensajeEdit, "⚠ Selecciona un animal.", false);
            return;
        }
        if (medicamentosEdit.isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ Agrega al menos un medicamento al plan.", false);
            return;
        }
        Tratamiento t = tratamientos.get(indiceEditar);
        t.animal       = cmbMascotaE.getValue();
        t.estado       = cmbEstadoE.getValue() != null ? cmbEstadoE.getValue() : "Activo";
        t.fechaControl = txtFechaControlE.getText().trim();
        t.indicaciones = txtIndicacionesE.getText().trim();
        t.medicamentos = new ArrayList<>(medicamentosEdit);

        tablaTratamientos.refresh();
        setMensaje(lblMensajeEdit, "✔ Cambios guardados para " + t.animal + ".", true);
        indiceEditar = -1;
        tabsTrat.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarEditar() {
        cmbMascotaE.setValue(null); cmbEstadoE.setValue(null);
        txtPrincipioActivoE.clear(); txtDosisE.clear(); txtViaE.clear(); txtFrecuenciaE.clear();
        medicamentosEdit.clear();
        txtIndicacionesE.clear(); txtFechaControlE.clear();
        lblMensajeEdit.setText("");
        indiceEditar = -1;
    }

    // ═══ PESTAÑA LISTA ═══
    @FXML
    public void onIrEditar() {
        Tratamiento sel = tablaTratamientos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un tratamiento para editar.", false);
            return;
        }
        for (int i = 0; i < tratamientos.size(); i++) {
            if (tratamientos.get(i) == sel) {
                indiceEditar = i;
                // Cargar datos en formulario editar
                cmbMascotaE.setValue(sel.animal);
                cmbEstadoE.setValue(sel.estado);
                txtFechaControlE.setText(sel.fechaControl);
                txtIndicacionesE.setText(sel.indicaciones);
                medicamentosEdit.clear();
                medicamentosEdit.addAll(sel.medicamentos);
                setMensaje(lblMensajeEdit, "Tratamiento de " + sel.animal + " cargado. Modifica y guarda.", true);
                tabsTrat.getSelectionModel().select(1);
                return;
            }
        }
    }

    @FXML
    public void onEliminar() {
        Tratamiento sel = tablaTratamientos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un tratamiento para eliminar.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar"); confirm.setHeaderText(null);
        confirm.setContentText("¿Eliminar tratamiento de " + sel.animal + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            tratamientos.remove(sel);
            setMensaje(lblMensajeLista, "✔ Tratamiento eliminado.", true);
        }
    }

    private void setMensaje(Label lbl, String texto, boolean ok) {
        lbl.setText(texto);
        lbl.setStyle(ok
            ? "-fx-text-fill: #2d6a4f; -fx-font-weight: bold;"
            : "-fx-text-fill: #c62828; -fx-font-weight: bold;");
    }

    @FXML
    public void irDashboard() {
        try { MainApp.irA("views/dashboard.fxml"); }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
