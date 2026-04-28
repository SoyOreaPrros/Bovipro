package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EmergenciasController {

    // ── Pestaña Agregar ──
    @FXML private ComboBox<String> cbxPaciente;
    @FXML private ComboBox<String> cbxPropietario;
    @FXML private ComboBox<String> cmbNivel;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lblMensajeAgg;

    // ── Pestaña Editar ──
    @FXML private ComboBox<String> cbxPacienteE;
    @FXML private ComboBox<String> cbxPropietarioE;
    @FXML private ComboBox<String> cmbNivelE;
    @FXML private TextArea txtDescripcionE;
    @FXML private Label lblMensajeEdit;

    // ── Pestaña Lista ──
    @FXML private TableView<String[]> tablaEmergencias;
    @FXML private TableColumn<String[], String> colNivel, colPaciente, colPropietario2, colDescripcion;
    @FXML private Label lblMensajeLista;

    @FXML private TabPane tabsEmerg;

    private ObservableList<String[]> emergencias;
    private int indiceEditar = -1;

    @FXML
    public void initialize() {
        cmbNivel.getItems().addAll("Alta", "Media", "Baja");
        cmbNivel.setValue("Alta");
        cmbNivelE.getItems().addAll("Alta", "Media", "Baja");

        // Animales disponibles
        String[] animales = {"Toro #14", "Vaca #07", "Becerra #03", "Toro #22", "Vaca #11"};
        cbxPaciente.getItems().addAll(animales);
        cbxPacienteE.getItems().addAll(animales);

        // Propietarios disponibles
        String[] propietarios = {"Rancho El Roble", "Granja Los Pinos", "Hacienda Morales", "Rancho La Loma", "Rancho San Juan"};
        cbxPropietario.getItems().addAll(propietarios);
        cbxPropietarioE.getItems().addAll(propietarios);

        // Columnas tabla lista
        colNivel.setCellValueFactory(d        -> new SimpleStringProperty(d.getValue()[0]));
        colPaciente.setCellValueFactory(d     -> new SimpleStringProperty(d.getValue()[1]));
        colPropietario2.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[2]));
        colDescripcion.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue()[3]));


        // Datos de ejemplo
        emergencias = FXCollections.observableArrayList();
        emergencias.add(new String[]{"🔴 Alta",  "Toro #22",    "Hacienda Morales", "Fractura en pezuña"});
        emergencias.add(new String[]{"🟡 Media", "Vaca #07",    "Granja Los Pinos", "Mastitis severa"});
        emergencias.add(new String[]{"🟢 Baja",  "Becerra #03", "Rancho La Loma",   "Control post-vacuna"});

        tablaEmergencias.setItems(emergencias);

    }

    // Helper para convertir nivel en emoji
    private String nivelConEmoji(String nivel) {
        if ("Alta".equals(nivel))  return "🔴 Alta";
        if ("Media".equals(nivel)) return "🟡 Media";
        return "🟢 Baja";
    }

    private String emojiANivel(String con) {
        if (con.contains("Alta"))  return "Alta";
        if (con.contains("Media")) return "Media";
        return "Baja";
    }

    // ═══ PESTAÑA AGREGAR ═══
    @FXML
    public void onRegistrar() {
        String paciente    = cbxPaciente.getValue() != null ? cbxPaciente.getValue() : "";
        String propietario = cbxPropietario.getValue() != null ? cbxPropietario.getValue() : "";
        String descripcion = txtDescripcion.getText().trim();
        String nivel       = cmbNivel.getValue();

        if (paciente.isEmpty() || propietario.isEmpty() || descripcion.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ Completa todos los campos.", false);
            return;
        }
        emergencias.add(0, new String[]{nivelConEmoji(nivel), paciente, propietario, descripcion});
        setMensaje(lblMensajeAgg, "✔ Emergencia registrada para " + paciente + ".", true);
        onLimpiarAgg();
        // Ir a la lista
        tabsEmerg.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarAgg() {
        cbxPaciente.setValue(null); txtDescripcion.clear();
        cbxPropietario.setValue(null);
        cmbNivel.setValue("Alta");
        lblMensajeAgg.setText("");
    }

    // ═══ PESTAÑA EDITAR ═══
    @FXML




    public void onGuardarCambios() {
        if (indiceEditar < 0) {
            setMensaje(lblMensajeEdit, "⚠ Primero selecciona y carga una emergencia.", false);
            return;
        }
        if (cbxPacienteE.getValue() == null || cbxPacienteE.getValue().isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ El animal no puede estar vacío.", false);
            return;
        }
        String[] emerg = emergencias.get(indiceEditar);
        emerg[0] = nivelConEmoji(cmbNivelE.getValue() != null ? cmbNivelE.getValue() : "Alta");
        emerg[1] = cbxPacienteE.getValue() != null ? cbxPacienteE.getValue() : "";
        emerg[2] = cbxPropietarioE.getValue() != null ? cbxPropietarioE.getValue() : "";
        emerg[3] = txtDescripcionE.getText().trim();
        tablaEmergencias.refresh();
        setMensaje(lblMensajeEdit, "✔ Cambios guardados para " + emerg[1] + ".", true);
        indiceEditar = -1;
        // Ir a la lista
        tabsEmerg.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarEditar() {
        cbxPacienteE.setValue(null); txtDescripcionE.clear();
        cbxPropietarioE.setValue(null);
        cmbNivelE.setValue(null);
        lblMensajeEdit.setText("");
        indiceEditar = -1;
    }

    // ═══ PESTAÑA LISTA ═══
    @FXML
    public void onIrEditar() {
        String[] sel = tablaEmergencias.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona una emergencia para editar.", false);
            return;
        }
        tabsEmerg.getSelectionModel().select(1);
        for (int i = 0; i < emergencias.size(); i++) {
            if (emergencias.get(i) == sel) {

                indiceEditar = i;
                cmbNivelE.setValue(emojiANivel(sel[0]));
                cbxPacienteE.setValue(sel[1]);
                // Si tiene propietario guardado, marcar el checkbox y habilitar el campo
cbxPropietarioE.setValue(sel[2]);
                txtDescripcionE.setText(sel[3]);
                setMensaje(lblMensajeEdit, "Emergencia de " + sel[1] + " cargada. Modifica y guarda.", true);
                return;
            }
        }
    }

    @FXML
    public void onEliminar() {
        String[] sel = tablaEmergencias.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona una emergencia para eliminar.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar"); confirm.setHeaderText(null);
        confirm.setContentText("¿Eliminar emergencia de " + sel[1] + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            emergencias.remove(sel);
            setMensaje(lblMensajeLista, "✔ Emergencia eliminada.", true);
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
