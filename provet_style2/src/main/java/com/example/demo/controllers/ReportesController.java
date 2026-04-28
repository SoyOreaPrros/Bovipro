package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ReportesController {

    // ── Tarjetas resumen ──
    @FXML private Label lblTotalTrat, lblEfectivos, lblNoEfectivos, lblEnCurso;

    // ── Filtro ──
    @FXML private ComboBox<String> cbxFiltroAnimal, cbxFiltroResultado;
    @FXML private Label lblMensaje;

    // ── Tabla principal ──
    @FXML private TableView<String[]> tablaTratamientos;
    @FXML private TableColumn<String[], String> colAnimal, colMedicamento, colDosis;
    @FXML private TableColumn<String[], String> colDuracion, colFecha, colResultado, colNota;

    // Lista completa de datos
    private ObservableList<String[]> todosLosTratamientos;

    @FXML
    public void initialize() {
        // Llenar combos de filtro
        cbxFiltroAnimal.getItems().addAll(
            "Todos", "Toro #14", "Vaca #07", "Becerra #03", "Toro #22", "Vaca #11"
        );
        cbxFiltroAnimal.setValue("Todos");

        cbxFiltroResultado.getItems().addAll("Todos", "Efectivo", "No efectivo", "En curso");
        cbxFiltroResultado.setValue("Todos");

        // Columnas de la tabla
        colAnimal.setCellValueFactory(d      -> new SimpleStringProperty(d.getValue()[0]));
        colMedicamento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[1]));
        colDosis.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[2]));
        colDuracion.setCellValueFactory(d    -> new SimpleStringProperty(d.getValue()[3]));
        colFecha.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[4]));
        colResultado.setCellValueFactory(d   -> new SimpleStringProperty(d.getValue()[5]));
        colNota.setCellValueFactory(d        -> new SimpleStringProperty(d.getValue()[6]));

        // Colorear filas según resultado
        tablaTratamientos.setRowFactory(tv -> new TableRow<String[]>() {
            @Override
            protected void updateItem(String[] fila, boolean vacio) {
                super.updateItem(fila, vacio);
                if (vacio || fila == null) {
                    setStyle("");
                } else if (fila[5].equals("✅ Efectivo")) {
                    setStyle("-fx-background-color: #e8f5e9;");
                } else if (fila[5].equals("❌ No efectivo")) {
                    setStyle("-fx-background-color: #ffebee;");
                } else {
                    setStyle("-fx-background-color: #fff8e1;");
                }
            }
        });

        // Datos de ejemplo con diferentes resultados
        todosLosTratamientos = FXCollections.observableArrayList();
        todosLosTratamientos.add(new String[]{"Toro #14",    "Oxitetraciclina", "10ml", "5 días",  "10/03/2026", "✅ Efectivo",     "Recuperación completa en 4 días"});
        todosLosTratamientos.add(new String[]{"Vaca #07",    "Penicilina",      "20ml", "7 días",  "01/04/2026", "✅ Efectivo",     "Mastitis resuelta sin recaída"});
        todosLosTratamientos.add(new String[]{"Becerra #03", "Ivermectina",     "5ml",  "1 día",   "15/03/2026", "✅ Efectivo",     "Parásitos eliminados"});
        todosLosTratamientos.add(new String[]{"Toro #22",    "Meloxicam",       "15ml", "3 días",  "05/02/2026", "❌ No efectivo",  "No mejoró, se cambió a tratamiento quirúrgico"});
        todosLosTratamientos.add(new String[]{"Vaca #11",    "Amoxicilina",     "8ml",  "5 días",  "20/03/2026", "❌ No efectivo",  "Bacteria resistente, requirió otro antibiótico"});
        todosLosTratamientos.add(new String[]{"Toro #14",    "Vitamina B12",    "5ml",  "3 días",  "01/04/2026", "⏳ En curso",     "Tratamiento iniciado, pendiente revisión"});
        todosLosTratamientos.add(new String[]{"Becerra #03", "Oxitetraciclina", "6ml",  "7 días",  "22/04/2026", "⏳ En curso",     "Día 3 de tratamiento, evolución favorable"});
        todosLosTratamientos.add(new String[]{"Vaca #07",    "Calcio IV",       "50ml", "2 días",  "10/02/2026", "✅ Efectivo",     "Hipocalcemia resuelta en 48 horas"});
        todosLosTratamientos.add(new String[]{"Toro #22",    "Antibiótico X",   "12ml", "10 días", "01/03/2026", "❌ No efectivo",  "Sin respuesta al tratamiento"});
        todosLosTratamientos.add(new String[]{"Vaca #11",    "Enrofloxacina",   "10ml", "5 días",  "10/04/2026", "✅ Efectivo",     "Infección respiratoria controlada"});

        tablaTratamientos.setItems(todosLosTratamientos);
        actualizarContadores(todosLosTratamientos);
    }

    // ═══ FILTRAR ═══
    @FXML
    public void onFiltrar() {
        String animal    = cbxFiltroAnimal.getValue();
        String resultado = cbxFiltroResultado.getValue();

        ObservableList<String[]> filtrados = FXCollections.observableArrayList();

        for (String[] t : todosLosTratamientos) {
            boolean pasaAnimal    = animal.equals("Todos") || t[0].equals(animal);
            boolean pasaResultado = resultado.equals("Todos")
                || (resultado.equals("Efectivo")    && t[5].equals("✅ Efectivo"))
                || (resultado.equals("No efectivo") && t[5].equals("❌ No efectivo"))
                || (resultado.equals("En curso")    && t[5].equals("⏳ En curso"));

            if (pasaAnimal && pasaResultado) {
                filtrados.add(t);
            }
        }

        tablaTratamientos.setItems(filtrados);
        actualizarContadores(filtrados);

        if (filtrados.isEmpty()) {
            setMensaje("⚠ No se encontraron resultados con ese filtro.", false);
        } else {
            setMensaje("Mostrando " + filtrados.size() + " tratamiento(s).", true);
        }
    }

    @FXML
    public void onLimpiarFiltro() {
        cbxFiltroAnimal.setValue("Todos");
        cbxFiltroResultado.setValue("Todos");
        tablaTratamientos.setItems(todosLosTratamientos);
        actualizarContadores(todosLosTratamientos);
        lblMensaje.setText("");
    }

    // ═══ MARCAR RESULTADO ═══
    @FXML
    public void onMarcarEfectivo() {
        String[] sel = tablaTratamientos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje("⚠ Selecciona un tratamiento de la lista.", false);
            return;
        }
        sel[5] = "✅ Efectivo";
        tablaTratamientos.refresh();
        actualizarContadores(todosLosTratamientos);
        setMensaje("✔ Tratamiento de " + sel[0] + " marcado como efectivo.", true);
    }

    @FXML
    public void onMarcarNoEfectivo() {
        String[] sel = tablaTratamientos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje("⚠ Selecciona un tratamiento de la lista.", false);
            return;
        }
        sel[5] = "❌ No efectivo";
        tablaTratamientos.refresh();
        actualizarContadores(todosLosTratamientos);
        setMensaje("✔ Tratamiento de " + sel[0] + " marcado como no efectivo.", true);
    }

    @FXML
    public void onMarcarEnCurso() {
        String[] sel = tablaTratamientos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setMensaje("⚠ Selecciona un tratamiento de la lista.", false);
            return;
        }
        sel[5] = "⏳ En curso";
        tablaTratamientos.refresh();
        actualizarContadores(todosLosTratamientos);
        setMensaje("✔ Tratamiento de " + sel[0] + " marcado como en curso.", true);
    }

    // ═══ HELPERS ═══
    private void actualizarContadores(ObservableList<String[]> lista) {
        int efectivos   = 0;
        int noEfectivos = 0;
        int enCurso     = 0;

        for (String[] t : lista) {
            if (t[5].equals("✅ Efectivo"))     efectivos++;
            else if (t[5].equals("❌ No efectivo")) noEfectivos++;
            else                                 enCurso++;
        }

        lblTotalTrat.setText(String.valueOf(lista.size()));
        lblEfectivos.setText(String.valueOf(efectivos));
        lblNoEfectivos.setText(String.valueOf(noEfectivos));
        lblEnCurso.setText(String.valueOf(enCurso));
    }

    private void setMensaje(String texto, boolean ok) {
        lblMensaje.setText(texto);
        lblMensaje.setStyle(ok
            ? "-fx-text-fill: #2d6a4f; -fx-font-weight: bold;"
            : "-fx-text-fill: #c62828; -fx-font-weight: bold;");
    }

    @FXML
    public void irDashboard() {
        try { MainApp.irA("views/dashboard.fxml"); }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
