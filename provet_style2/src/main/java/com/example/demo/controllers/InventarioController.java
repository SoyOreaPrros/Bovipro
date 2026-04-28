package com.example.demo.controllers;

import com.example.demo.MainApp;
import com.example.demo.models.DatosSimulados;
import com.example.demo.models.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventarioController {

    // ── Pestaña Agregar ──
    @FXML private TextField txtNombreP, txtCategoriaP, txtCantidadP, txtPrecioP;
    @FXML private ComboBox<String> cbxEstadoP;
    @FXML private Label lblMensajeAgg;

    // ── Pestaña Editar ──
    @FXML private TextField txtNombreE, txtCategoriaE, txtCantidadE, txtPrecioE;
    @FXML private ComboBox<String> cbxEstadoE;
    @FXML private Label lblMensajeEdit;

    // ── Pestaña Lista ──
    @FXML private TextField txtBuscar;
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colId, colNombre, colCategoria;
    @FXML private TableColumn<Producto, String> colCantidad, colPrecio, colEstado;
    @FXML private Label lblMensajeLista, lblAlerta;

    @FXML private TabPane tabsInv;

    private ObservableList<Producto> productos;
    private int indiceEditar = -1;

    @FXML
    public void initialize() {
        String[] estados = {"OK", "Bajo stock", "Agotado"};

        cbxEstadoP.getItems().addAll(estados);
        cbxEstadoP.setValue("OK");
        cbxEstadoE.getItems().addAll(estados);

        // Columnas tabla lista
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        productos = DatosSimulados.getProductos();
        tablaProductos.setItems(productos);
        verificarStockBajo();
    }

    // ═══ PESTAÑA AGREGAR ═══
    @FXML
    public void onGuardar() {
        String nombre    = txtNombreP.getText().trim();
        String categoria = txtCategoriaP.getText().trim();
        String cantidad  = txtCantidadP.getText().trim();
        String estado    = cbxEstadoP.getValue();

        if (nombre.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ El nombre del producto es obligatorio.", false);
            return;
        }
        if (categoria.isEmpty() || cantidad.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ Completa todos los campos obligatorios.", false);
            return;
        }
        String id = "P" + String.format("%03d", productos.size() + 1);
        productos.add(new Producto(id, nombre, categoria, cantidad,
            txtPrecioP.getText().trim(), estado != null ? estado : "OK"));
        verificarStockBajo();
        setMensaje(lblMensajeAgg, "✔ Producto " + nombre + " registrado.", true);
        onLimpiarAgg();
        // Ir a la lista
        tabsInv.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarAgg() {
        txtNombreP.clear(); txtCategoriaP.clear(); txtCantidadP.clear(); txtPrecioP.clear();
        cbxEstadoP.setValue("OK");
        lblMensajeAgg.setText("");
    }

    // ═══ PESTAÑA EDITAR ═══

    public void onGuardarCambios() {
        if (indiceEditar < 0) {
            setMensaje(lblMensajeEdit, "⚠ Primero selecciona y carga un producto.", false);
            return;
        }
        String nombre = txtNombreE.getText().trim();
        if (nombre.isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ El nombre no puede estar vacío.", false);
            return;
        }
        Producto p = productos.get(indiceEditar);
        p.setNombre(nombre);
        p.setCategoria(txtCategoriaE.getText().trim());
        p.setCantidad(txtCantidadE.getText().trim());
        p.setPrecio(txtPrecioE.getText().trim());
        p.setEstado(cbxEstadoE.getValue() != null ? cbxEstadoE.getValue() : "OK");
        tablaProductos.refresh();
        verificarStockBajo();
        setMensaje(lblMensajeEdit, "✔ Cambios guardados para " + nombre + ".", true);
        indiceEditar = -1;
        // Ir a la lista
        tabsInv.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarEditar() {
        txtNombreE.clear(); txtCategoriaE.clear(); txtCantidadE.clear(); txtPrecioE.clear();
        cbxEstadoE.setValue(null);
        lblMensajeEdit.setText("");
        indiceEditar = -1;
    }

    // ═══ PESTAÑA LISTA ═══
    @FXML
    public void onBuscar() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            tablaProductos.setItems(productos);
            return;
        }
        ObservableList<Producto> filtrados = FXCollections.observableArrayList();
        for (Producto p : productos)
            if (p.getNombre().toLowerCase().contains(texto) || p.getCategoria().toLowerCase().contains(texto))
                filtrados.add(p);
        tablaProductos.setItems(filtrados);
    }

    @FXML
    public void onIrEditar() {
        Producto sel = tablaProductos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un producto para editar.", false);
            return;
        }
        tabsInv.getSelectionModel().select(1);
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i) == sel) {
                indiceEditar = i;
                txtNombreE.setText(sel.getNombre());
                txtCategoriaE.setText(sel.getCategoria());
                txtCantidadE.setText(sel.getCantidad());
                txtPrecioE.setText(sel.getPrecio());
                cbxEstadoE.setValue(sel.getEstado());
                setMensaje(lblMensajeEdit, "Producto " + sel.getNombre() + " cargado. Modifica y guarda.", true);
                return;
            }
        }
    }

    @FXML
    public void onEliminar() {
        Producto sel = tablaProductos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un producto para eliminar.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar"); confirm.setHeaderText(null);
        confirm.setContentText("¿Eliminar el producto: " + sel.getNombre() + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            productos.remove(sel);
            verificarStockBajo();
            setMensaje(lblMensajeLista, "✔ Producto eliminado.", true);
        }
    }

    private void verificarStockBajo() {
        int bajos = 0;
        for (Producto p : productos)
            if (p.getEstado().equals("Bajo stock") || p.getEstado().equals("Agotado")) bajos++;
        if (bajos > 0) {
            lblAlerta.setText("⚠ " + bajos + " producto(s) con stock bajo o agotado.");
            lblAlerta.setVisible(true);
        } else {
            lblAlerta.setVisible(false);
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
