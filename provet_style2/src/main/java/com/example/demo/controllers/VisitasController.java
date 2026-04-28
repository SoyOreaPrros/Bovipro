package com.example.demo.controllers;

import com.example.demo.MainApp;
import com.example.demo.models.DatosSimulados;
import com.example.demo.models.Visita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisitasController {

    // ── Pestaña Agregar ──
    @FXML private TextField txtFecha, txtMotivo;
    @FXML private ComboBox<String> cbxEstado, cbxPropietario;
    @FXML private Label lblMensajeAgg;

    // ── Pestaña Editar ──
    @FXML private TextField txtFechaE, txtMotivoE;
    @FXML private ComboBox<String> cbxPropietarioE, cbxEstadoE;
    @FXML private Label lblMensajeEdit;

    // ── Pestaña Lista ──
    @FXML private TextField txtBuscar;
    @FXML private TableView<Visita> tablaVisitas;
    @FXML private TableColumn<Visita, String> colId, colPropietario, colFecha, colMotivo, colEstado;
    @FXML private Label lblMensajeLista, lblTotal;

    @FXML private TabPane tabsVisitas;

    private ObservableList<Visita> visitas;
    private int indiceEditar = -1;

    private static final String[] PROPIETARIOS = {
        "Rancho El Roble", "Granja Los Pinos", "Hacienda Morales",
        "Rancho La Loma", "Rancho San Juan"
    };
    private static final String[] ESTADOS = {
        "Pendiente", "En espera", "En consulta", "Completada", "Cancelada"
    };

    @FXML
    public void initialize() {
        cbxEstado.getItems().addAll(ESTADOS);
        cbxEstado.setValue("Pendiente");
        cbxPropietario.getItems().addAll(PROPIETARIOS);

        cbxEstadoE.getItems().addAll(ESTADOS);
        cbxPropietarioE.getItems().addAll(PROPIETARIOS);

        // Columnas tabla lista
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPropietario.setCellValueFactory(new PropertyValueFactory<>("propietario"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        visitas = DatosSimulados.getVisitas();
        tablaVisitas.setItems(visitas);
        actualizarTotal();

        txtFecha.setText(java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    // ═══ PESTAÑA AGREGAR ═══

    @FXML
    public void onGuardar() {
        String propietario = cbxPropietario.getValue() != null ? cbxPropietario.getValue() : "";
        String fecha       = txtFecha.getText().trim();
        String motivo      = txtMotivo.getText().trim();
        String estado      = cbxEstado.getValue();

        if (propietario.isEmpty() || motivo.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ Propietario y motivo son obligatorios.", false);
            return;
        }
        String id = "V" + String.format("%03d", visitas.size() + 1);
        visitas.add(new Visita(id, propietario, "", fecha, motivo,
            estado != null ? estado : "Pendiente"));
        actualizarTotal();
        setMensaje(lblMensajeAgg, "✔ Visita de " + propietario + " registrada.", true);
        onLimpiarAgg();
        tabsVisitas.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarAgg() {
        cbxPropietario.setValue(null);
        cbxEstado.setValue("Pendiente");
        txtMotivo.clear();
        txtFecha.setText(java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblMensajeAgg.setText("");
    }

    // ═══ PESTAÑA EDITAR ═══

    @FXML
    public void onGuardarCambios() {
        if (indiceEditar < 0) {
            setMensaje(lblMensajeEdit, "⚠ Primero selecciona y carga una visita desde la lista.", false);
            return;
        }
        String propietario = cbxPropietarioE.getValue() != null ? cbxPropietarioE.getValue() : "";
        if (propietario.isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ El propietario no puede estar vacío.", false);
            return;
        }
        Visita v = visitas.get(indiceEditar);
        v.setPropietario(propietario);
        v.setMascota("");
        v.setFecha(txtFechaE.getText().trim());
        v.setMotivo(txtMotivoE.getText().trim());
        v.setEstado(cbxEstadoE.getValue() != null ? cbxEstadoE.getValue() : "Pendiente");
        tablaVisitas.refresh();
        setMensaje(lblMensajeEdit, "✔ Cambios guardados para " + propietario + ".", true);
        indiceEditar = -1;
        tabsVisitas.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarEditar() {
        cbxPropietarioE.setValue(null);
        cbxEstadoE.setValue(null);
        txtFechaE.clear();
        txtMotivoE.clear();
        lblMensajeEdit.setText("");
        indiceEditar = -1;
    }

    // ═══ PESTAÑA LISTA ═══

    @FXML
    public void onBuscar() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            tablaVisitas.setItems(visitas);
            actualizarTotal();
            return;
        }
        ObservableList<Visita> filtrados = FXCollections.observableArrayList();
        for (Visita v : visitas)
            if (v.getPropietario().toLowerCase().contains(texto)
             || v.getMotivo().toLowerCase().contains(texto))
                filtrados.add(v);
        tablaVisitas.setItems(filtrados);
        lblTotal.setText("Total: " + filtrados.size() + " visitas");
    }

    @FXML
    public void onIrEditar() {
        Visita sel = tablaVisitas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona una visita para editar.", false);
            return;
        }
        tabsVisitas.getSelectionModel().select(1);
        for (int i = 0; i < visitas.size(); i++) {
            if (visitas.get(i) == sel) {
                indiceEditar = i;
                cbxPropietarioE.setValue(sel.getPropietario());
                txtFechaE.setText(sel.getFecha());
                txtMotivoE.setText(sel.getMotivo());
                cbxEstadoE.setValue(sel.getEstado());
                setMensaje(lblMensajeEdit, "Visita de " + sel.getPropietario() + " cargada. Modifica y guarda.", true);
                return;
            }
        }
    }

    @FXML
    public void onEliminar() {
        Visita sel = tablaVisitas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona una visita para eliminar.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Eliminar visita de " + sel.getPropietario() + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            visitas.remove(sel);
            actualizarTotal();
            setMensaje(lblMensajeLista, "✔ Visita eliminada.", true);
        }
    }

    // ═══ UTILIDADES ═══

    private void actualizarTotal() {
        lblTotal.setText("Total: " + visitas.size() + " visitas");
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
