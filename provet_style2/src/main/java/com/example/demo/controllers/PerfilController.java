package com.example.demo.controllers;

import com.example.demo.MainApp;
import com.example.demo.SesionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PerfilController {

    // Cambiar contraseña
    @FXML private PasswordField txtPasswordActual, txtPasswordNueva, txtPasswordConfirm;
    @FXML private Label lblMensajePass;

    // Pregunta de seguridad
    @FXML private Label     lblPreguntaActual;
    @FXML private ComboBox<String> cmbPregunta;
    @FXML private TextField txtRespuestaNueva, txtRespuestaConfirm;
    @FXML private Label     lblMensajePregunta;

    @FXML
    public void initialize() {
        // Mostrar pregunta vigente
        lblPreguntaActual.setText(SesionManager.getPregunta());

        // Opciones de preguntas
        cmbPregunta.getItems().addAll(
            "¿Cuál es el nombre de tu primera mascota?",
            "¿En qué ciudad naciste?",
            "¿Cuál es el apellido de soltera de tu madre?",
            "¿Cuál fue el nombre de tu escuela primaria?",
            "¿Cuál es tu platillo favorito?",
            "¿Cuál es el nombre de tu mejor amigo de infancia?",
            "¿Cuál era el modelo de tu primer vehículo?"
        );
        cmbPregunta.setValue(SesionManager.getPregunta());
    }

    // ── Cambiar contraseña ────────────────────────────────────────────────────
    @FXML
    public void onCambiarPassword() {
        String actual    = txtPasswordActual.getText();
        String nueva     = txtPasswordNueva.getText();
        String confirmar = txtPasswordConfirm.getText();

        if (actual.isBlank() || nueva.isBlank() || confirmar.isBlank()) {
            setMsg(lblMensajePass, "⚠ Completa todos los campos.", false); return;
        }
        if (!actual.equals(SesionManager.getPassword())) {
            setMsg(lblMensajePass, "⚠ Contraseña actual incorrecta.", false); return;
        }
        if (nueva.length() < 4) {
            setMsg(lblMensajePass, "⚠ Mínimo 4 caracteres.", false); return;
        }
        if (!nueva.equals(confirmar)) {
            setMsg(lblMensajePass, "⚠ La nueva contraseña no coincide.", false); return;
        }
        SesionManager.setPassword(nueva);
        setMsg(lblMensajePass, "✔ Contraseña actualizada correctamente.", true);
        txtPasswordActual.clear();
        txtPasswordNueva.clear();
        txtPasswordConfirm.clear();
    }

    // ── Cambiar pregunta de seguridad ─────────────────────────────────────────
    @FXML
    public void onGuardarPregunta() {
        String pregunta  = cmbPregunta.getValue();
        String respuesta = txtRespuestaNueva.getText();
        String confirma  = txtRespuestaConfirm.getText();

        if (pregunta == null || pregunta.isBlank()) {
            setMsg(lblMensajePregunta, "⚠ Selecciona una pregunta.", false); return;
        }
        if (respuesta.isBlank() || confirma.isBlank()) {
            setMsg(lblMensajePregunta, "⚠ Escribe y confirma tu respuesta.", false); return;
        }
        if (!respuesta.trim().equalsIgnoreCase(confirma.trim())) {
            setMsg(lblMensajePregunta, "⚠ Las respuestas no coinciden.", false); return;
        }
        SesionManager.setPreguntaYRespuesta(pregunta, respuesta);
        lblPreguntaActual.setText(SesionManager.getPregunta());
        setMsg(lblMensajePregunta, "✔ Pregunta de seguridad actualizada.", true);
        txtRespuestaNueva.clear();
        txtRespuestaConfirm.clear();
    }

    @FXML
    public void irDashboard() {
        try { MainApp.irA("views/dashboard.fxml"); }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }

    // ── Utilidad ──────────────────────────────────────────────────────────────
    private void setMsg(Label lbl, String texto, boolean ok) {
        lbl.setText(texto);
        lbl.setStyle(ok
            ? "-fx-text-fill: #2d6a4f; -fx-font-weight: bold; -fx-font-size: 12;"
            : "-fx-text-fill: #c62828; -fx-font-weight: bold; -fx-font-size: 12;");
    }
}
