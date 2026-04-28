package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import java.util.Map;

public class HistorialController {

    // Selector
    @FXML private ComboBox<String> cmbMascota;
    @FXML private Label lblMascota;
    @FXML private TabPane tabsHistorial;

    // Pestaña 1 — Historial general
    @FXML private TableView<String[]> tablaHistorial;
    @FXML private TableColumn<String[], String> colFechaH, colMotivoH, colVetH, colNotasH;
    @FXML private TextArea txtDetalle;

    // Pestaña 2 — Vacunación
    @FXML private TableView<String[]> tablaVacunas;
    @FXML private TableColumn<String[], String> colVacFecha, colVacNombre, colVacEnfermedad,
                                                 colVacDosis, colVacVia, colVacProxima,
                                                 colVacLote, colVacVet;
    @FXML private TextField txtEstCarbon, txtEstBrucela, txtEstIBR, txtEstLepto, txtEstClostridios;
    @FXML private TextArea  txtObsVacunas;
    @FXML private Label     lblMensajeVac;

    // Datos por animal
    private Map<String, ObservableList<String[]>> historialPorAnimal = new HashMap<>();
    private Map<String, ObservableList<String[]>> vacunasPorAnimal   = new HashMap<>();
    private ObservableList<String[]> registrosActuales = FXCollections.observableArrayList();
    private ObservableList<String[]> vacunasActuales   = FXCollections.observableArrayList();

    private String animalActual = null;

    @FXML
    public void initialize() {
        cmbMascota.getItems().addAll("Toro #14","Vaca #07","Becerra #03","Toro #22","Vaca #11");

        // Columnas historial general
        colFechaH.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue()[0]));
        colMotivoH.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[1]));
        colVetH.setCellValueFactory(d    -> new SimpleStringProperty(d.getValue()[2]));
        colNotasH.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue()[3]));
        tablaHistorial.setItems(registrosActuales);

        tablaHistorial.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null)
                txtDetalle.setText("Fecha: " + sel[0] + "\nMotivo: " + sel[1]
                    + "\nVeterinario: " + sel[2] + "\nNotas: " + sel[3]);
        });

        // Columnas vacunación
        colVacFecha.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[0]));
        colVacNombre.setCellValueFactory(d      -> new SimpleStringProperty(d.getValue()[1]));
        colVacEnfermedad.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue()[2]));
        colVacDosis.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[3]));
        colVacVia.setCellValueFactory(d         -> new SimpleStringProperty(d.getValue()[4]));
        colVacProxima.setCellValueFactory(d     -> new SimpleStringProperty(d.getValue()[5]));
        colVacLote.setCellValueFactory(d        -> new SimpleStringProperty(d.getValue()[6]));
        colVacVet.setCellValueFactory(d         -> new SimpleStringProperty(d.getValue()[7]));
        tablaVacunas.setItems(vacunasActuales);

        // ── Datos simulados historial ──
        ObservableList<String[]> h14 = FXCollections.observableArrayList();
        h14.add(new String[]{"10/01/2026","Vacunación brucelosis",      "Dr. Martínez","Sin reacciones"});
        h14.add(new String[]{"15/03/2026","Desparasitación ivermectina","Dr. Martínez","Peso: 520kg"});
        h14.add(new String[]{"21/04/2026","Revisión general",           "Dr. Martínez","Buen estado"});
        historialPorAnimal.put("Toro #14", h14);

        ObservableList<String[]> h22 = FXCollections.observableArrayList();
        h22.add(new String[]{"05/02/2026","Cirugía pezuña",          "Dr. Martínez","Exitosa"});
        h22.add(new String[]{"20/02/2026","Control post-operatorio", "Dr. Martínez","Evolución favorable"});
        h22.add(new String[]{"10/04/2026","Radiografía extremidad",  "Dr. Martínez","Hueso consolidado"});
        historialPorAnimal.put("Toro #22", h22);

        for (String a : new String[]{"Vaca #07","Becerra #03","Vaca #11"}) {
            ObservableList<String[]> hd = FXCollections.observableArrayList();
            hd.add(new String[]{"12/02/2026","Primera consulta","Dr. Martínez","Sin antecedentes"});
            hd.add(new String[]{"05/04/2026","Revisión general","Dr. Martínez","Estado normal"});
            historialPorAnimal.put(a, hd);
        }

        // ── Datos simulados vacunación ──
        // Formato: [fecha, vacuna, contra, dosis, vía, próxima, lote, veterinario]
        for (String animal : new String[]{"Toro #14","Vaca #07","Becerra #03","Toro #22","Vaca #11"}) {
            ObservableList<String[]> vacs = FXCollections.observableArrayList();
            boolean esHembra = animal.startsWith("Vaca") || animal.startsWith("Becerra");

            vacs.add(new String[]{"10/01/2026","Carbosap (Carbón Sintomático)",
                "Carbón sintomático / edematoso","2 ml","SC","10/07/2026","LT-2024-08","Dr. Martínez"});
            vacs.add(new String[]{"10/01/2026","Clostridium 8-way",
                "Clostridiosis (8 serotipos)","5 ml","IM","10/07/2026","CL-2024-12","Dr. Martínez"});
            vacs.add(new String[]{"15/01/2026","Leptavoid-H",
                "Leptospirosis (5 serogrupos)","5 ml","SC","15/07/2026","LP-2025-01","Dr. Martínez"});
            vacs.add(new String[]{"20/01/2026","Triangle 10 (IBR/DVB)",
                "IBR, DVB, PI3, BRSV","2 ml","IM","20/01/2027","TR-2025-02","Dr. Martínez"});

            if (esHembra)
                vacs.add(new String[]{"25/01/2026","RB51 (Brucella abortus)",
                    "Brucelosis","2 ml","SC","Dosis única (permanente)","RB-2025-03","Dr. Martínez"});

            vacs.add(new String[]{"01/03/2026","Rabia paralítica bovina",
                "Rabia","2 ml","SC","01/03/2027","RB-2025-04","Dr. Martínez"});
            vacs.add(new String[]{"15/03/2026","Ivomec (desparasitante)",
                "Parásitos internos/externos","1 ml/50kg","SC","15/09/2026","IV-2025-05","Dr. Martínez"});

            vacunasPorAnimal.put(animal, vacs);
        }
    }

    @FXML
    public void onCargarHistorial() {
        String mascota = cmbMascota.getValue();
        if (mascota == null) { lblMascota.setText("⚠ Selecciona un animal."); return; }

        animalActual = mascota;
        lblMascota.setText("Animal: " + mascota);

        // Cargar historial general
        registrosActuales.clear();
        registrosActuales.addAll(historialPorAnimal.getOrDefault(mascota, FXCollections.observableArrayList()));
        txtDetalle.clear();

        // Cargar vacunación
        vacunasActuales.clear();
        vacunasActuales.addAll(vacunasPorAnimal.getOrDefault(mascota, FXCollections.observableArrayList()));

        // Estado vacunal resumen
        boolean esHembra = mascota.startsWith("Vaca") || mascota.startsWith("Becerra");
        txtEstCarbon.setText("✔ 10/01/2026 — prox. 10/07/2026");
        txtEstBrucela.setText(esHembra ? "✔ 25/01/2026 — permanente" : "N/A (macho)");
        txtEstIBR.setText("✔ 20/01/2026 — prox. 20/01/2027");
        txtEstLepto.setText("✔ 15/01/2026 — prox. 15/07/2026");
        txtEstClostridios.setText("✔ 10/01/2026 — prox. 10/07/2026");
        txtObsVacunas.setText("Esquema de vacunación al día. Próxima revisión integral: julio 2026.");
    }

    @FXML
    public void onNuevoRegistro() {
        if (animalActual == null) { aviso("Carga un animal primero."); return; }

        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Nuevo Registro"); d.setHeaderText(null);
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        TextField fFecha  = new TextField(hoy());
        TextField fMotivo = new TextField(); fMotivo.setPromptText("Ej: Vacunación brucelosis");
        TextField fVet    = new TextField("Dr. Martínez");
        TextArea  fNotas  = new TextArea(); fNotas.setPrefRowCount(3);
        fNotas.setPromptText("Observaciones, peso, temperatura...");

        GridPane g = grid();
        g.add(new Label("Fecha:"),       0,0); g.add(fFecha,  1,0);
        g.add(new Label("Motivo:"),      0,1); g.add(fMotivo, 1,1);
        g.add(new Label("Veterinario:"), 0,2); g.add(fVet,    1,2);
        g.add(new Label("Notas:"),       0,3); g.add(fNotas,  1,3);
        d.getDialogPane().setContent(g);

        d.showAndWait().ifPresent(r -> {
            if (r == guardar && !fMotivo.getText().trim().isEmpty()) {
                String[] nuevo = {fFecha.getText().trim(), fMotivo.getText().trim(),
                                  fVet.getText().trim(), fNotas.getText().trim()};
                historialPorAnimal.computeIfAbsent(animalActual,
                    k -> FXCollections.observableArrayList()).add(0, nuevo);
                registrosActuales.add(0, nuevo);
                lblMascota.setText("Animal: " + animalActual + " — ✔ Registro agregado");
            }
        });
    }

    @FXML
    public void onEliminarRegistro() {
        String[] sel = tablaHistorial.getSelectionModel().getSelectedItem();
        if (sel == null) { aviso("Selecciona un registro para eliminar."); return; }
        if (confirmar("¿Eliminar este registro?")) {
            registrosActuales.remove(sel);
            if (animalActual != null && historialPorAnimal.containsKey(animalActual))
                historialPorAnimal.get(animalActual).remove(sel);
            txtDetalle.clear();
        }
    }

    @FXML
    public void onAgregarVacuna() {
        if (animalActual == null) { aviso("Carga un animal primero."); return; }

        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Agregar Vacuna"); d.setHeaderText(null);
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        TextField fFecha   = new TextField(hoy());
        TextField fNombre  = new TextField(); fNombre.setPromptText("Ej: Carbosap");
        TextField fContra  = new TextField(); fContra.setPromptText("Ej: Carbón sintomático");
        TextField fDosis   = new TextField(); fDosis.setPromptText("Ej: 2 ml");
        ComboBox<String> fVia = new ComboBox<>();
        fVia.getItems().addAll("SC","IM","IV","Oral","Intranasal"); fVia.setValue("SC");
        TextField fProxima = new TextField(); fProxima.setPromptText("Ej: 10/07/2026");
        TextField fLote    = new TextField(); fLote.setPromptText("Ej: LT-2025-01");
        TextField fVet     = new TextField("Dr. Martínez");

        GridPane g = grid();
        g.add(new Label("Fecha aplicación:"),  0,0); g.add(fFecha,  1,0);
        g.add(new Label("Nombre vacuna:"),     0,1); g.add(fNombre, 1,1);
        g.add(new Label("Contra:"),            0,2); g.add(fContra, 1,2);
        g.add(new Label("Dosis:"),             0,3); g.add(fDosis,  1,3);
        g.add(new Label("Vía:"),               0,4); g.add(fVia,    1,4);
        g.add(new Label("Próxima dosis:"),     0,5); g.add(fProxima,1,5);
        g.add(new Label("Lote:"),              0,6); g.add(fLote,   1,6);
        g.add(new Label("Veterinario:"),       0,7); g.add(fVet,    1,7);
        d.getDialogPane().setContent(g);
        d.getDialogPane().setPrefWidth(480);

        d.showAndWait().ifPresent(r -> {
            if (r == guardar && !fNombre.getText().trim().isEmpty()) {
                String[] nueva = {
                    fFecha.getText().trim(), fNombre.getText().trim(), fContra.getText().trim(),
                    fDosis.getText().trim(), fVia.getValue(), fProxima.getText().trim(),
                    fLote.getText().trim(), fVet.getText().trim()
                };
                vacunasPorAnimal.computeIfAbsent(animalActual,
                    k -> FXCollections.observableArrayList()).add(nueva);
                vacunasActuales.add(nueva);
                setMensaje("✔ Vacuna registrada correctamente.", true);
            }
        });
    }

    @FXML
    public void onEliminarVacuna() {
        String[] sel = tablaVacunas.getSelectionModel().getSelectedItem();
        if (sel == null) { aviso("Selecciona una vacuna para eliminar."); return; }
        if (confirmar("¿Eliminar el registro de vacunación \"" + sel[1] + "\"?")) {
            vacunasActuales.remove(sel);
            if (animalActual != null && vacunasPorAnimal.containsKey(animalActual))
                vacunasPorAnimal.get(animalActual).remove(sel);
            setMensaje("✔ Registro eliminado.", true);
        }
    }

    // ── utilidades ──
    private String hoy() {
        return java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    private GridPane grid() {
        GridPane g = new GridPane(); g.setHgap(12); g.setVgap(10); g.setPadding(new Insets(20)); return g;
    }
    private void aviso(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING); a.setTitle("Aviso");
        a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
    private boolean confirmar(String msg) {
        Alert c = new Alert(Alert.AlertType.CONFIRMATION); c.setTitle("Confirmar");
        c.setHeaderText(null); c.setContentText(msg);
        return c.showAndWait().get() == ButtonType.OK;
    }
    private void setMensaje(String texto, boolean ok) {
        lblMensajeVac.setText(texto);
        lblMensajeVac.setStyle(ok
            ? "-fx-text-fill: #2d6a4f; -fx-font-weight: bold;"
            : "-fx-text-fill: #c62828; -fx-font-weight: bold;");
    }

    @FXML public void irDashboard() {
        try { MainApp.irA("views/dashboard.fxml"); } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
