package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DiagnosticoController {

    // Índices del array String[]
    // [0]=animal  [1]=diagnostico  [2]=gravedad  [3]=fecha
    // [4]=sintomas  [5]=tratamiento
    // [6]=temp  [7]=fc  [8]=fr  [9]=pulso  [10]=llenado
    // [11]=actitud  [12]=condicion  [13]=hidratacion
    // [14]=cardiovascular  [15]=digestivo  [16]=nervioso
    // [17]=respiratorio  [18]=musculo  [19]=piel

    // ── Pestaña Agregar ──
    @FXML private ComboBox<String> cmbMascota, cmbGravedad;
    @FXML private TextArea txtSintomas, txtDiagnostico, txtTratamientoSugerido;
    @FXML private Label lblMensajeAgg;
    @FXML private TextField txtTemp, txtFC, txtFR, txtPulso, txtLlenado;
    @FXML private ComboBox<String> cmbActitud, cmbCondicion, cmbHidratacion;
    @FXML private TextArea txtCardiovascular, txtDigestivo, txtNervioso;
    @FXML private TextArea txtRespiratorio, txtMusculo, txtPiel;

    // ── Pestaña Editar ──
    @FXML private ComboBox<String> cmbMascotaE, cmbGravedadE;
    @FXML private TextField txtFechaE;
    @FXML private TextArea txtSintomasE, txtDiagnosticoE, txtTratamientoE;
    @FXML private Label lblMensajeEdit;
    @FXML private TextField txtTempE, txtFCE, txtFRE, txtPulsoE, txtLlenadoE;
    @FXML private ComboBox<String> cmbActitudE, cmbCondicionE, cmbHidratacionE;
    @FXML private TextArea txtCardiovascularE, txtDigestivoE, txtNerviosoE;
    @FXML private TextArea txtRespiratorioE, txtMusculoE, txtPielE;

    // ── Pestaña Lista ──
    @FXML private TableView<String[]> tablaDiagnosticos;
    @FXML private TableColumn<String[], String> colAnimal, colDiagnostico, colGravedad, colFecha;
    @FXML private Label lblMensajeLista;

    @FXML private TabPane tabsDiag;

    private ObservableList<String[]> diagnosticos;
    private int indiceEditar = -1;

    @FXML
    public void initialize() {
        String[] animales   = {"Toro #14", "Vaca #07", "Becerra #03", "Toro #22", "Vaca #11"};
        String[] gravedades = {"Leve", "Moderada", "Grave"};
        String[] actitudes  = {"Alerta", "Deprimido", "Decúbito", "Agresivo"};
        String[] condiciones = {"1 - Muy delgado", "2 - Delgado", "3 - Normal", "4 - Gordo", "5 - Obeso"};
        String[] hidrataciones = {"Normal", "Deshidratación leve (5%)", "Deshidratación moderada (8%)", "Deshidratación grave (10%+)"};

        // Agregar
        cmbMascota.getItems().addAll(animales);
        cmbGravedad.getItems().addAll(gravedades);
        cmbGravedad.setValue("Leve");
        cmbActitud.getItems().addAll(actitudes);
        cmbCondicion.getItems().addAll(condiciones);
        cmbHidratacion.getItems().addAll(hidrataciones);

        // Editar
        cmbMascotaE.getItems().addAll(animales);
        cmbGravedadE.getItems().addAll(gravedades);
        cmbActitudE.getItems().addAll(actitudes);
        cmbCondicionE.getItems().addAll(condiciones);
        cmbHidratacionE.getItems().addAll(hidrataciones);

        // Columnas tabla lista
        colAnimal.setCellValueFactory(d      -> new SimpleStringProperty(d.getValue()[0]));
        colDiagnostico.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[1]));
        colGravedad.setCellValueFactory(d    -> new SimpleStringProperty(d.getValue()[2]));
        colFecha.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[3]));

        // Datos de ejemplo (20 campos)
        diagnosticos = FXCollections.observableArrayList();
        diagnosticos.add(new String[]{
            "Toro #14", "Dermatofitosis leve", "Leve", "10/03/2026",
            "Caída del pelo en cuello", "",
            "38.6", "68", "22", "Normal", "<2 seg",
            "Alerta", "3 - Normal", "Normal",
            "", "", "", "", "", ""
        });
        diagnosticos.add(new String[]{
            "Vaca #07", "Mastitis subclínica", "Moderada", "01/04/2026",
            "Inflamación cuarto delantero", "",
            "39.2", "75", "24", "Fuerte", "<2 seg",
            "Deprimido", "2 - Delgado", "Deshidratación leve (5%)",
            "", "Dolor abdominal leve", "", "", "", ""
        });

        tablaDiagnosticos.setItems(diagnosticos);
    }

    // ═══ PESTAÑA AGREGAR ═══
    @FXML
    public void onGuardar() {
        String mascota     = cmbMascota.getValue();
        String sintomas    = txtSintomas.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        String gravedad    = cmbGravedad.getValue();

        if (mascota == null || sintomas.isEmpty() || diagnostico.isEmpty()) {
            setMensaje(lblMensajeAgg, "⚠ Completa los campos obligatorios.", false);
            return;
        }
        String hoy = java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        diagnosticos.add(0, new String[]{
            mascota, diagnostico, gravedad, hoy,
            sintomas, txtTratamientoSugerido.getText().trim(),
            txtTemp.getText().trim(), txtFC.getText().trim(), txtFR.getText().trim(),
            txtPulso.getText().trim(), txtLlenado.getText().trim(),
            valOr(cmbActitud.getValue()), valOr(cmbCondicion.getValue()), valOr(cmbHidratacion.getValue()),
            txtCardiovascular.getText().trim(), txtDigestivo.getText().trim(), txtNervioso.getText().trim(),
            txtRespiratorio.getText().trim(), txtMusculo.getText().trim(), txtPiel.getText().trim()
        });

        setMensaje(lblMensajeAgg, "✔ Diagnóstico guardado para " + mascota + ".", true);
        onLimpiarAgg();
        tabsDiag.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarAgg() {
        cmbMascota.setValue(null); cmbGravedad.setValue("Leve");
        txtSintomas.clear(); txtDiagnostico.clear(); txtTratamientoSugerido.clear();
        txtTemp.clear(); txtFC.clear(); txtFR.clear(); txtPulso.clear(); txtLlenado.clear();
        cmbActitud.setValue(null); cmbCondicion.setValue(null); cmbHidratacion.setValue(null);
        txtCardiovascular.clear(); txtDigestivo.clear(); txtNervioso.clear();
        txtRespiratorio.clear(); txtMusculo.clear(); txtPiel.clear();
        lblMensajeAgg.setText("");
    }

    // ═══ PESTAÑA EDITAR ═══
    @FXML
    public void onGuardarCambios() {
        if (indiceEditar < 0) {
            setMensaje(lblMensajeEdit, "⚠ Primero selecciona un diagnóstico desde la lista.", false);
            return;
        }
        if (cmbMascotaE.getValue() == null || txtDiagnosticoE.getText().trim().isEmpty()) {
            setMensaje(lblMensajeEdit, "⚠ Animal y diagnóstico son obligatorios.", false);
            return;
        }
        String[] d = diagnosticos.get(indiceEditar);
        d[0]  = cmbMascotaE.getValue();
        d[1]  = txtDiagnosticoE.getText().trim();
        d[2]  = valOr(cmbGravedadE.getValue());
        d[3]  = txtFechaE.getText().trim();
        d[4]  = txtSintomasE.getText().trim();
        d[5]  = txtTratamientoE.getText().trim();
        d[6]  = txtTempE.getText().trim();
        d[7]  = txtFCE.getText().trim();
        d[8]  = txtFRE.getText().trim();
        d[9]  = txtPulsoE.getText().trim();
        d[10] = txtLlenadoE.getText().trim();
        d[11] = valOr(cmbActitudE.getValue());
        d[12] = valOr(cmbCondicionE.getValue());
        d[13] = valOr(cmbHidratacionE.getValue());
        d[14] = txtCardiovascularE.getText().trim();
        d[15] = txtDigestivoE.getText().trim();
        d[16] = txtNerviosoE.getText().trim();
        d[17] = txtRespiratorioE.getText().trim();
        d[18] = txtMusculoE.getText().trim();
        d[19] = txtPielE.getText().trim();

        tablaDiagnosticos.refresh();
        setMensaje(lblMensajeEdit, "✔ Cambios guardados para " + d[0] + ".", true);
        indiceEditar = -1;
        tabsDiag.getSelectionModel().select(2);
    }

    @FXML
    public void onLimpiarEditar() {
        cmbMascotaE.setValue(null); cmbGravedadE.setValue(null); txtFechaE.clear();
        txtSintomasE.clear(); txtDiagnosticoE.clear(); txtTratamientoE.clear();
        txtTempE.clear(); txtFCE.clear(); txtFRE.clear(); txtPulsoE.clear(); txtLlenadoE.clear();
        cmbActitudE.setValue(null); cmbCondicionE.setValue(null); cmbHidratacionE.setValue(null);
        txtCardiovascularE.clear(); txtDigestivoE.clear(); txtNerviosoE.clear();
        txtRespiratorioE.clear(); txtMusculoE.clear(); txtPielE.clear();
        lblMensajeEdit.setText("");
        indiceEditar = -1;
    }

    // ═══ PESTAÑA LISTA ═══
    @FXML
    public void onIrEditar() {
        String[] sel = tablaDiagnosticos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un diagnóstico para editar.", false);
            return;
        }
        for (int i = 0; i < diagnosticos.size(); i++) {
            if (diagnosticos.get(i) == sel) {
                indiceEditar = i;
                cargarEnEditar(sel);
                tabsDiag.getSelectionModel().select(1);
                return;
            }
        }
    }

    @FXML
    public void onEliminar() {
        String[] sel = tablaDiagnosticos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje(lblMensajeLista, "⚠ Selecciona un diagnóstico para eliminar.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar"); confirm.setHeaderText(null);
        confirm.setContentText("¿Eliminar diagnóstico de " + sel[0] + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            diagnosticos.remove(sel);
            setMensaje(lblMensajeLista, "✔ Diagnóstico eliminado.", true);
        }
    }

    // ── Helpers ──
    private void cargarEnEditar(String[] d) {
        cmbMascotaE.setValue(d[0]);
        txtDiagnosticoE.setText(d[1]);
        cmbGravedadE.setValue(d[2]);
        txtFechaE.setText(d[3]);
        txtSintomasE.setText(d[4]);
        txtTratamientoE.setText(d[5]);
        txtTempE.setText(d[6]);
        txtFCE.setText(d[7]);
        txtFRE.setText(d[8]);
        txtPulsoE.setText(d[9]);
        txtLlenadoE.setText(d[10]);
        cmbActitudE.setValue(d[11].isEmpty() ? null : d[11]);
        cmbCondicionE.setValue(d[12].isEmpty() ? null : d[12]);
        cmbHidratacionE.setValue(d[13].isEmpty() ? null : d[13]);
        txtCardiovascularE.setText(d[14]);
        txtDigestivoE.setText(d[15]);
        txtNerviosoE.setText(d[16]);
        txtRespiratorioE.setText(d[17]);
        txtMusculoE.setText(d[18]);
        txtPielE.setText(d[19]);
        setMensaje(lblMensajeEdit, "Diagnóstico de " + d[0] + " cargado. Modifica y guarda.", true);
    }

    private String valOr(String val) {
        return val != null ? val : "";
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
