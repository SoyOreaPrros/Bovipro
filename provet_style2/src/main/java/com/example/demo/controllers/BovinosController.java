package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BovinosController {

    // ── Pestaña Agregar ──
    @FXML private TextField txtArete, txtNombre, txtFechaNac, txtPeso;
    @FXML private RadioButton rbMacho, rbHembra;
    @FXML private ComboBox<String> cbxProposito, cbxRaza, cbxPropietario;
    @FXML private Label lblMensajeAgg, lblTotal;

    // ── Pestaña Editar ──
    @FXML private TextField  txtAreteE, txtNombreE, txtFechaNacE, txtPesoE;
    @FXML private RadioButton rbMachoE, rbHembraE;
    @FXML private ComboBox<String> cbxPropositoE, cbxRazaE, cbxPropietarioE;
    @FXML private Label lblMensajeEdit;

    // ── Pestaña Lista ──
    @FXML private TextField txtBuscarLista;
    @FXML private TableView<String[]> tablaBovinos;
    @FXML private TableColumn<String[], String> colArete, colNombreB, colFDN;
    @FXML private TableColumn<String[], String> colSexo, colProposito, colPesoB, colRazaB, colPropietarioB;
    @FXML private Label lblMensajeLista;

    @FXML private TabPane tabsBovinos;

    private ObservableList<String[]> listaBovinos;
    private int indiceEditar = -1;

    @FXML
    public void initialize() {
        String[] razas = {"Angus","Brahman","Charolais","Hereford","Holstein",
                          "Limousin","Simmental","Suizo Pardo","Zebu","Criollo"};
        String[] propositos = {"Lechera","Carne","Doble propósito","Cría"};
        String[] propietarios = {"Rancho El Roble","Granja Los Pinos","Rancho La Loma",
                                 "Hacienda Morales","Rancho San Juan"};

        cbxRaza.getItems().addAll(razas);
        cbxProposito.getItems().addAll(propositos);
        cbxPropietario.getItems().addAll(propietarios);
        cbxRazaE.getItems().addAll(razas);
        cbxPropositoE.getItems().addAll(propositos);
        cbxPropietarioE.getItems().addAll(propietarios);

        // Grupos radio agregar
        ToggleGroup grupoSexo = new ToggleGroup();
        rbMacho.setToggleGroup(grupoSexo);
        rbHembra.setToggleGroup(grupoSexo);
        rbMacho.setSelected(true);

        // Grupos radio editar
        ToggleGroup grupoSexoE = new ToggleGroup();
        rbMachoE.setToggleGroup(grupoSexoE);
        rbHembraE.setToggleGroup(grupoSexoE);

        // Columnas tabla principal
        colArete.setCellValueFactory(d        -> prop(d.getValue()[0]));
        colNombreB.setCellValueFactory(d      -> prop(d.getValue()[1]));
        colFDN.setCellValueFactory(d          -> prop(d.getValue()[2]));
        colSexo.setCellValueFactory(d         -> prop(d.getValue()[3]));
        colProposito.setCellValueFactory(d    -> prop(d.getValue()[4]));
        colPesoB.setCellValueFactory(d        -> prop(d.getValue()[5]));
        colRazaB.setCellValueFactory(d        -> prop(d.getValue()[6]));
        colPropietarioB.setCellValueFactory(d -> prop(d.getValue()[7]));


        // Datos simulados
        listaBovinos = FXCollections.observableArrayList();
        listaBovinos.add(new String[]{"998912",  "",        "23/09/2000", "Hembra", "Lechera",         "89kg",  "Holstein",  "Rancho El Roble"});
        listaBovinos.add(new String[]{"8894214", "Vaquita", "12/02/2012", "Macho",  "Carne",           "100kg", "Angus",     "Granja Los Pinos"});
        listaBovinos.add(new String[]{"889819",  "Kike",    "04/01/2018", "Macho",  "Carne",           "90kg",  "Brahman",   "Rancho La Loma"});
        listaBovinos.add(new String[]{"3343434", "Vivi",    "05/06/2002", "Hembra", "Doble propósito", "101kg", "Simmental", "Hacienda Morales"});

        tablaBovinos.setItems(listaBovinos);
        actualizarTotal();
    }

    // Helper para simplificar
    private javafx.beans.property.SimpleStringProperty prop(String v) {
        return new javafx.beans.property.SimpleStringProperty(v);
    }

    // ═══ PESTAÑA AGREGAR ═══
    @FXML
    public void onGuardarBovino() {
        String arete = txtArete.getText().trim();
        String prop  = cbxPropietario.getValue() != null ? cbxPropietario.getValue() : "";

        if (arete.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ El número de arete es obligatorio.", false);
            return;
        }
        if (prop.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ Selecciona un propietario.", false);
            return;
        }
        // Verificar arete duplicado
        for (String[] b : listaBovinos) {
            if (b[0].equals(arete)) {
                setMensaje(lblMensajeAgg, "⚠ Ya existe un bovino con ese número de arete.", false);
                return;
            }
        }

        String sexo      = rbMacho.isSelected() ? "Macho" : "Hembra";
        String proposito = cbxProposito.getValue() != null ? cbxProposito.getValue() : "-";
        String raza      = cbxRaza.getValue() != null ? cbxRaza.getValue() : "-";
        String peso      = txtPeso.getText().trim().isEmpty() ? "-" : txtPeso.getText().trim() + "kg";
        String fecha     = txtFechaNac.getText().trim().isEmpty() ? "-" : txtFechaNac.getText().trim();
        String nombre    = txtNombre.getText().trim();

        listaBovinos.add(new String[]{arete, nombre, fecha, sexo, proposito, peso, raza, prop});
        actualizarTotal();
        setMensaje(lblMensajeAgg, "✔ Bovino " + arete + " registrado correctamente.", true);
        onLimpiarAgg();

        // Ir a la lista para ver el resultado
        tabsBovinos.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarAgg() {
        txtArete.clear(); txtNombre.clear(); txtFechaNac.clear(); txtPeso.clear();
        cbxProposito.setValue(null); cbxRaza.setValue(null); cbxPropietario.setValue(null);
        rbMacho.setSelected(true);
        lblMensajeAgg.setText("");
    }

    // ═══ PESTAÑA EDITAR ═══


    private void cargarEnEditar(String[] b, int indice) {
        indiceEditar = indice;
        txtAreteE.setText(b[0]);
        txtNombreE.setText(b[1]);
        txtFechaNacE.setText(b[2]);
        if ("Macho".equals(b[3])) rbMachoE.setSelected(true);
        else rbHembraE.setSelected(true);
        cbxPropositoE.setValue(b[4]);
        txtPesoE.setText(b[5].replace("kg", ""));
        cbxRazaE.setValue(b[6]);
        cbxPropietarioE.setValue(b[7]);
        setMensaje(lblMensajeEdit, "Bovino " + b[0] + " cargado. Modifica y guarda.", true);
    }

    @FXML
    public void onGuardarCambios() {
        if (indiceEditar < 0) {
            setMensaje(lblMensajeEdit, "⚠ Primero selecciona y carga un bovino.", false);
            return;
        }
        String arete = txtAreteE.getText().trim();
        if (arete.isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ El número de arete no puede estar vacío.", false);
            return;
        }
        String sexo = rbMachoE.isSelected() ? "Macho" : "Hembra";
        String peso = txtPesoE.getText().trim().isEmpty() ? "-" : txtPesoE.getText().trim() + "kg";

        String[] bovino = listaBovinos.get(indiceEditar);
        bovino[0] = arete;
        bovino[1] = txtNombreE.getText().trim();
        bovino[2] = txtFechaNacE.getText().trim();
        bovino[3] = sexo;
        bovino[4] = cbxPropositoE.getValue() != null ? cbxPropositoE.getValue() : "-";
        bovino[5] = peso;
        bovino[6] = cbxRazaE.getValue() != null ? cbxRazaE.getValue() : "-";
        bovino[7] = cbxPropietarioE.getValue() != null ? cbxPropietarioE.getValue() : "-";

        tablaBovinos.refresh();
        setMensaje(lblMensajeEdit, "✔ Cambios guardados para arete " + arete + ".", true);
        indiceEditar = -1;

        // Ir a la lista
        tabsBovinos.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarEditar() {
        txtAreteE.clear(); txtNombreE.clear(); txtFechaNacE.clear(); txtPesoE.clear();
        cbxPropositoE.setValue(null); cbxRazaE.setValue(null); cbxPropietarioE.setValue(null);
        lblMensajeEdit.setText("");
        indiceEditar = -1;
    }

    // ═══ PESTAÑA LISTA ═══
    @FXML
    public void onBuscarLista() {
        String texto = txtBuscarLista.getText().trim().toLowerCase();
        if (texto.isEmpty()) { tablaBovinos.setItems(listaBovinos); return; }
        ObservableList<String[]> filtrados = FXCollections.observableArrayList();
        for (String[] b : listaBovinos)
            if (b[0].toLowerCase().contains(texto)
             || b[1].toLowerCase().contains(texto)
             || b[7].toLowerCase().contains(texto)) filtrados.add(b);
        tablaBovinos.setItems(filtrados);
    }

    @FXML
    public void onIrEditar() {
        String[] sel = tablaBovinos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un bovino para editar.", false);
            return;
        }
        // Cambiar a pestaña Editar y cargar
        tabsBovinos.getSelectionModel().select(1);
        for (int i = 0; i < listaBovinos.size(); i++) {
            if (listaBovinos.get(i) == sel) {
                cargarEnEditar(sel, i);
                return;
            }
        }
    }

    @FXML
    public void onEliminar() {
        String[] sel = tablaBovinos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un bovino para eliminar.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar"); confirm.setHeaderText(null);
        confirm.setContentText("¿Eliminar bovino con arete " + sel[0] + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            listaBovinos.remove(sel);
            actualizarTotal();
            setMensaje(lblMensajeLista, "✔ Bovino eliminado correctamente.", true);
        }
    }

    private void actualizarTotal() {
        lblTotal.setText("Total registrados: " + listaBovinos.size());
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
