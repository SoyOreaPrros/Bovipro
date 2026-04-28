package com.example.demo.controllers;

import com.example.demo.MainApp;
import com.example.demo.models.DatosSimulados;
import com.example.demo.models.Propietario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropietariosController {

    // ── Pestaña Agregar ──
    @FXML private TextField txtNombre, txtRancho, txtTelefono, txtMunicipio;
    @FXML private Label lblMensajeAgg;

    // ── Pestaña Editar ──
    @FXML private TextField txtNombreE, txtRanchoE, txtTelefonoE, txtMunicipioE;
    @FXML private Label lblMensajeEdit;

    // ── Pestaña Lista ──
    @FXML private TextField txtBuscarLista;
    @FXML private TableView<Propietario> tablaPropietarios;
    @FXML private TableColumn<Propietario, String> colId, colNombre, colRancho;
    @FXML private TableColumn<Propietario, String> colTelefono, colMunicipio, colAnimales;
    @FXML private Label lblMensajeLista, lblTotal;

    @FXML private TabPane tabsProp;

    private ObservableList<Propietario> propietarios;
    private int indiceEditar = -1;

    @FXML
    public void initialize() {
        // Columnas tabla lista
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRancho.setCellValueFactory(new PropertyValueFactory<>("rancho"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        colAnimales.setCellValueFactory(new PropertyValueFactory<>("animales"));



        propietarios = DatosSimulados.getPropietarios();
        tablaPropietarios.setItems(propietarios);
        actualizarTotal();
    }

    // ═══ PESTAÑA AGREGAR ═══
    @FXML
    public void onGuardar() {
        String nombre = txtNombre.getText().trim();
        String rancho = txtRancho.getText().trim();
        if (nombre.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ El nombre es obligatorio.", false);
            return;
        }
        if (rancho.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ El nombre del rancho es obligatorio.", false);
            return;
        }
        String id = "R" + String.format("%03d", propietarios.size() + 1);
        propietarios.add(new Propietario(id, nombre, rancho,
            txtTelefono.getText().trim(), txtMunicipio.getText().trim(), "0"));
        actualizarTotal();
        setMensaje(lblMensajeAgg, "✔ Propietario " + nombre + " registrado.", true);
        onLimpiarAgg();
        // Ir a la lista
        tabsProp.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarAgg() {
        txtNombre.clear(); txtRancho.clear(); txtTelefono.clear(); txtMunicipio.clear();
        lblMensajeAgg.setText("");
    }

    // ═══ PESTAÑA EDITAR ═══
    @FXML
    public void onGuardarCambios() {
        if (indiceEditar < 0) {
            setMensaje(lblMensajeEdit, "⚠ Primero selecciona y carga un propietario.", false);
            return;
        }
        String nombre = txtNombreE.getText().trim();
        if (nombre.isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ El nombre no puede estar vacío.", false);
            return;
        }
        Propietario p = propietarios.get(indiceEditar);
        p.setNombre(nombre);
        p.setRancho(txtRanchoE.getText().trim());
        p.setTelefono(txtTelefonoE.getText().trim());
        p.setMunicipio(txtMunicipioE.getText().trim());
        tablaPropietarios.refresh();
        setMensaje(lblMensajeEdit, "✔ Cambios guardados para " + nombre + ".", true);
        indiceEditar = -1;
        // Ir a la lista
        tabsProp.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarEditar() {
        txtNombreE.clear(); txtRanchoE.clear(); txtTelefonoE.clear(); txtMunicipioE.clear();
        lblMensajeEdit.setText("");
        indiceEditar = -1;
    }

    // ═══ PESTAÑA LISTA ═══
    @FXML
    public void onBuscarLista() {
        String texto = txtBuscarLista.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            tablaPropietarios.setItems(propietarios);
            actualizarTotal();
            return;
        }
        ObservableList<Propietario> filtrados = FXCollections.observableArrayList();
        for (Propietario p : propietarios)
            if (p.getNombre().toLowerCase().contains(texto)
             || p.getRancho().toLowerCase().contains(texto)
             || p.getMunicipio().toLowerCase().contains(texto))
                filtrados.add(p);
        tablaPropietarios.setItems(filtrados);
        lblTotal.setText("Total: " + filtrados.size() + " propietarios");
    }

    @FXML
    public void onIrEditar() {
        Propietario sel = tablaPropietarios.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un propietario para editar.", false);
            return;
        }
        tabsProp.getSelectionModel().select(1);
        for (int i = 0; i < propietarios.size(); i++) {
            if (propietarios.get(i) == sel) {
                indiceEditar = i;
                txtNombreE.setText(sel.getNombre());
                txtRanchoE.setText(sel.getRancho());
                txtTelefonoE.setText(sel.getTelefono());
                txtMunicipioE.setText(sel.getMunicipio());
                setMensaje(lblMensajeEdit, "Propietario " + sel.getNombre() + " cargado. Modifica y guarda.", true);
                return;
            }
        }
    }

    @FXML
    public void onEliminar() {
        Propietario sel = tablaPropietarios.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un propietario para eliminar.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar"); confirm.setHeaderText(null);
        confirm.setContentText("¿Eliminar a " + sel.getNombre() + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            propietarios.remove(sel);
            actualizarTotal();
            setMensaje(lblMensajeLista, "✔ Propietario eliminado.", true);
        }
    }

    private void actualizarTotal() {
        lblTotal.setText("Total: " + propietarios.size() + " propietarios");
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
