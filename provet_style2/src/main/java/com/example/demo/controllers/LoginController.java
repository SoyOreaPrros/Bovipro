package com.example.demo.controllers;

import com.example.demo.MainApp;
import com.example.demo.SesionManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField     txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label         lblError;

    // ── Login normal ──────────────────────────────────────────────────────────
    @FXML
    public void onLogin() {
        String usuario  = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarError("Por favor completa usuario y contraseña.");
            return;
        }
        if (usuario.equals("admin") && password.equals(SesionManager.getPassword())) {
            try {
                MainApp.irA("views/dashboard.fxml");
            } catch (Exception e) {
                mostrarError("Error al cargar el sistema. Intenta de nuevo.");
            }
        } else {
            mostrarError("Usuario o contraseña incorrectos. Intenta de nuevo.");
            txtPassword.clear();
        }
    }

    // ── Recuperar contraseña ──────────────────────────────────────────────────
    @FXML
    public void onRecuperarPassword() {
        // --- Ventana emergente: paso 1 – pregunta de seguridad ---------------
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(MainApp.getVentana());
        dialogo.setTitle("Recuperar contraseña");
        dialogo.setResizable(false);

        VBox root = new VBox(14);
        root.setPadding(new Insets(28, 32, 28, 32));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #f0f7f2;");
        root.setPrefWidth(420);

        Label titulo = new Label("🔑 Recuperar contraseña");
        titulo.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #2d6a4f;");

        Separator sep = new Separator();

        Label lblPregunta = new Label("Pregunta de seguridad:");
        lblPregunta.setStyle("-fx-text-fill: #444444; -fx-font-weight: bold;");

        Label lblTextoPregunta = new Label(SesionManager.getPregunta());
        lblTextoPregunta.setWrapText(true);
        lblTextoPregunta.setStyle("-fx-text-fill: #1a1a1a; -fx-font-size: 13;"
                + "-fx-background-color: #ffffff; -fx-background-radius: 6;"
                + "-fx-border-color: #b0c8b0; -fx-border-radius: 6; -fx-padding: 8;");
        lblTextoPregunta.setMaxWidth(Double.MAX_VALUE);

        Label lblRespLabel = new Label("Tu respuesta:");
        lblRespLabel.setStyle("-fx-text-fill: #444444;");

        TextField txtRespuesta = new TextField();
        txtRespuesta.setPromptText("Escribe tu respuesta...");
        txtRespuesta.setStyle("-fx-border-color: #b0c8b0; -fx-border-radius: 6;"
                + "-fx-background-radius: 6; -fx-padding: 8;");

        Label lblAviso = new Label("");
        lblAviso.setWrapText(true);
        lblAviso.setStyle("-fx-font-size: 12;");

        Button btnVerificar = new Button("Verificar respuesta");
        btnVerificar.setMaxWidth(Double.MAX_VALUE);
        btnVerificar.setStyle("-fx-background-color: #4a9b6f; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");

        btnVerificar.setOnAction(e -> {
            String intento = txtRespuesta.getText();
            if (intento.isBlank()) {
                lblAviso.setText("⚠ Escribe tu respuesta.");
                lblAviso.setStyle("-fx-text-fill: #c62828; -fx-font-size: 12;");
                return;
            }
            if (SesionManager.verificarRespuesta(intento)) {
                dialogo.close();
                abrirVentanaNuevaPassword();
            } else {
                lblAviso.setText("❌ Respuesta incorrecta. Intenta de nuevo.");
                lblAviso.setStyle("-fx-text-fill: #c62828; -fx-font-size: 12;");
                txtRespuesta.clear();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333333;"
                + "-fx-background-radius: 6; -fx-padding: 8; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> dialogo.close());

        root.getChildren().addAll(titulo, sep, lblPregunta, lblTextoPregunta,
                lblRespLabel, txtRespuesta, lblAviso, btnVerificar, btnCancelar);

        dialogo.setScene(new Scene(root));
        dialogo.showAndWait();
    }

    // --- Ventana emergente: paso 2 – nueva contraseña -------------------------
    private void abrirVentanaNuevaPassword() {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(MainApp.getVentana());
        dialogo.setTitle("Nueva contraseña");
        dialogo.setResizable(false);

        VBox root = new VBox(14);
        root.setPadding(new Insets(28, 32, 28, 32));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #f0f7f2;");
        root.setPrefWidth(380);

        Label titulo = new Label("🔒 Crear nueva contraseña");
        titulo.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: #2d6a4f;");

        Separator sep = new Separator();

        Label lblNueva = new Label("Nueva contraseña:");
        lblNueva.setStyle("-fx-text-fill: #444444;");
        PasswordField pfNueva = new PasswordField();
        pfNueva.setPromptText("••••••••");
        pfNueva.setStyle("-fx-border-color: #b0c8b0; -fx-border-radius: 6;"
                + "-fx-background-radius: 6; -fx-padding: 8;");

        Label lblConfirm = new Label("Confirmar contraseña:");
        lblConfirm.setStyle("-fx-text-fill: #444444;");
        PasswordField pfConfirm = new PasswordField();
        pfConfirm.setPromptText("••••••••");
        pfConfirm.setStyle("-fx-border-color: #b0c8b0; -fx-border-radius: 6;"
                + "-fx-background-radius: 6; -fx-padding: 8;");

        Label lblAviso = new Label("");
        lblAviso.setWrapText(true);
        lblAviso.setStyle("-fx-font-size: 12;");

        Button btnGuardar = new Button("✔ Guardar nueva contraseña");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        btnGuardar.setStyle("-fx-background-color: #4a9b6f; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");

        btnGuardar.setOnAction(e -> {
            String nueva    = pfNueva.getText();
            String confirma = pfConfirm.getText();
            if (nueva.isBlank() || confirma.isBlank()) {
                lblAviso.setText("⚠ Completa ambos campos.");
                lblAviso.setStyle("-fx-text-fill: #c62828; -fx-font-size: 12;"); return;
            }
            if (nueva.length() < 4) {
                lblAviso.setText("⚠ Mínimo 4 caracteres.");
                lblAviso.setStyle("-fx-text-fill: #c62828; -fx-font-size: 12;"); return;
            }
            if (!nueva.equals(confirma)) {
                lblAviso.setText("⚠ Las contraseñas no coinciden.");
                lblAviso.setStyle("-fx-text-fill: #c62828; -fx-font-size: 12;"); return;
            }
            SesionManager.setPassword(nueva);
            lblAviso.setText("✔ Contraseña actualizada. Ya puedes iniciar sesión.");
            lblAviso.setStyle("-fx-text-fill: #2d6a4f; -fx-font-weight: bold; -fx-font-size: 12;");
            btnGuardar.setDisable(true);
            // cerrar automáticamente después de 1.5 s
            new Thread(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(dialogo::close);
            }).start();
        });

        root.getChildren().addAll(titulo, sep, lblNueva, pfNueva,
                lblConfirm, pfConfirm, lblAviso, btnGuardar);

        dialogo.setScene(new Scene(root));
        dialogo.showAndWait();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void mostrarError(String msg) {
        lblError.setText(msg);
        lblError.setVisible(true);
        lblError.setManaged(true);
    }
}
