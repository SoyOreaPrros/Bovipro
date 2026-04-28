package com.example.demo.controllers;

import com.example.demo.MainApp;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExpedienteController {

    // Header
    @FXML private ComboBox<String> cmbAnimal;
    @FXML private Label lblAnimal, lblModoLectura;

    // Pestaña 1 - Datos del Bovino
    @FXML private TextField txtIdAnimal, txtEdad, txtColor, txtPeso, txtPropietario, txtTelProp, txtMunicipio;
    @FXML private ComboBox<String> cmbRaza, cmbSexo, cmbReproductivo;

    // Pestaña 2 - Diagnóstico
    @FXML private TextArea txtMotivo, txtSintomas, txtPosiEnf, txtDiagDef;
    @FXML private ComboBox<String> cmbGravedad, cmbPronostico;

    // Pestaña 3 - Tratamiento
    @FXML private TextField txtFechaControl;
    @FXML private TextArea txtIndicaciones;
    @FXML private TableView<String[]> tablaMeds;
    @FXML private TableColumn<String[], String> colMed, colDos, colVia, colFrec;
    private ObservableList<String[]> medicamentos = FXCollections.observableArrayList();

    // Pestaña 4 — Vacunación
    @FXML private TableView<String[]>  tablaVacunasExp;
    @FXML private TableColumn<String[], String> colExpVacFecha, colExpVacNombre, colExpVacEnfermedad,
                                                 colExpVacDosis, colExpVacVia, colExpVacProxima,
                                                 colExpVacLote, colExpVacVet;
    @FXML private TextField txtExpCarbon, txtExpBrucela, txtExpIBR, txtExpLepto, txtExpClostridios;
    @FXML private TextArea  txtExpObsVacunas;
    private ObservableList<String[]> vacunasExp = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Poblar combobox de animales
        cmbAnimal.getItems().addAll("Toro #14", "Vaca #07", "Becerra #03", "Toro #22", "Vaca #11");

        // Poblar combos de datos (solo lectura al cargar)
        cmbRaza.getItems().addAll("Angus", "Brahman", "Charolais", "Hereford", "Holstein",
                                  "Limousin", "Simmental", "Suizo Pardo", "Zebu", "Criollo");
        cmbSexo.getItems().addAll("Macho", "Hembra");
        cmbReproductivo.getItems().addAll("Entero", "Castrado", "Gestación", "Lactancia");
        cmbGravedad.getItems().addAll("Leve", "Moderada", "Grave");
        cmbPronostico.getItems().addAll("Favorable", "Reservado", "Grave", "Desfavorable");

        // Configurar columnas de la tabla de medicamentos
        colMed.setCellValueFactory(d  -> new javafx.beans.property.SimpleStringProperty(d.getValue()[0]));
        colDos.setCellValueFactory(d  -> new javafx.beans.property.SimpleStringProperty(d.getValue()[1]));
        colVia.setCellValueFactory(d  -> new javafx.beans.property.SimpleStringProperty(d.getValue()[2]));
        colFrec.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue()[3]));
        tablaMeds.setItems(medicamentos);

        // Columnas tabla vacunación
        colExpVacFecha.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[0]));
        colExpVacNombre.setCellValueFactory(d      -> new SimpleStringProperty(d.getValue()[1]));
        colExpVacEnfermedad.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue()[2]));
        colExpVacDosis.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue()[3]));
        colExpVacVia.setCellValueFactory(d         -> new SimpleStringProperty(d.getValue()[4]));
        colExpVacProxima.setCellValueFactory(d     -> new SimpleStringProperty(d.getValue()[5]));
        colExpVacLote.setCellValueFactory(d        -> new SimpleStringProperty(d.getValue()[6]));
        colExpVacVet.setCellValueFactory(d         -> new SimpleStringProperty(d.getValue()[7]));
        tablaVacunasExp.setItems(vacunasExp);
    }

    @FXML
    public void onCargar() {
        String animal = cmbAnimal.getValue();
        if (animal == null) return;

        lblAnimal.setText("Expediente de: " + animal);
        lblModoLectura.setText("🔒 Modo solo lectura");

        // ── Pestaña 1: Datos del Bovino ──────────────────────────────────────
        txtIdAnimal.setText(animal);
        cmbSexo.setValue(animal.startsWith("Vaca") || animal.startsWith("Becerra") ? "Hembra" : "Macho");
        cmbRaza.setValue(
            animal.equals("Toro #14")   ? "Angus"    :
            animal.equals("Vaca #07")   ? "Holstein" :
            animal.equals("Toro #22")   ? "Brahman"  :
            animal.equals("Becerra #03")? "Simmental":
                                          "Hereford");
        txtPeso.setText(animal.startsWith("Becerra") ? "180" : "480");
        txtEdad.setText(animal.startsWith("Becerra") ? "8 meses" : "4 años");
        txtColor.setText(
            animal.equals("Toro #14")   ? "Negro"        :
            animal.equals("Vaca #07")   ? "Blanco y negro":
            animal.equals("Becerra #03")? "Café claro"   :
            animal.equals("Toro #22")   ? "Gris ceniciento":
                                          "Rojo colorado");
        cmbReproductivo.setValue(animal.startsWith("Vaca") ? "Lactancia" :
                                 animal.startsWith("Becerra") ? "Gestación" : "Entero");
        txtPropietario.setText(
            animal.equals("Toro #14")   ? "Rancho El Roble"   :
            animal.equals("Vaca #07")   ? "Granja Los Pinos"  :
            animal.equals("Toro #22")   ? "Hacienda Morales"  :
            animal.equals("Becerra #03")? "Rancho La Loma"    :
                                          "Finca Santa Cruz");
        txtTelProp.setText(
            animal.equals("Toro #14")   ? "228-100-2345" :
            animal.equals("Vaca #07")   ? "228-555-7890" :
            animal.equals("Toro #22")   ? "228-300-1122" :
            animal.equals("Becerra #03")? "228-444-0011" :
                                          "228-777-3300");
        txtMunicipio.setText(
            animal.equals("Toro #14")   ? "Emiliano Zapata" :
            animal.equals("Vaca #07")   ? "Veracruz"        :
            animal.equals("Toro #22")   ? "Xalapa"          :
            animal.equals("Becerra #03")? "Coatepec"        :
                                          "Orizaba");

        // ── Pestaña 2: Diagnóstico ────────────────────────────────────────────
        txtMotivo.setText(
            animal.equals("Toro #14")   ? "Pérdida de apetito y aislamiento del hato" :
            animal.equals("Vaca #07")   ? "Secreción nasal y tos persistente"         :
            animal.equals("Toro #22")   ? "Cojera en miembro posterior derecho"       :
            animal.equals("Becerra #03")? "Diarrea amarillenta y debilidad general"   :
                                          "Revision preventiva de rutina");
        txtSintomas.setText(
            animal.equals("Toro #14")   ? "Anorexia, temperatura 39.8°C, mucosas congestionadas" :
            animal.equals("Vaca #07")   ? "Rinorrea serosa, frecuencia respiratoria elevada 32 rpm, fiebre 40.1°C" :
            animal.equals("Toro #22")   ? "Inflamación del casco, sensibilidad al apoyo, temperatura local elevada" :
            animal.equals("Becerra #03")? "Heces líquidas amarillentas, deshidratación 7%, depresión" :
                                          "Sin síntomas aparentes, estado general bueno");
        txtPosiEnf.setText(
            animal.equals("Toro #14")   ? "Fiebre de transporte, BRD, Leptospirosis" :
            animal.equals("Vaca #07")   ? "Rinotraqueítis infecciosa bovina (IBR), Pasteurelosis" :
            animal.equals("Toro #22")   ? "Pie de vaca, Laminitis, Artritis séptica"  :
            animal.equals("Becerra #03")? "Criptosporidiosis, Colibacilosis, Salmonelosis" :
                                          "N/A");
        txtDiagDef.setText(
            animal.equals("Toro #14")   ? "Síndrome respiratorio bovino (BRD)" :
            animal.equals("Vaca #07")   ? "Rinotraqueítis infecciosa bovina (IBR)" :
            animal.equals("Toro #22")   ? "Pododermatitis séptica (pie de vaca)"    :
            animal.equals("Becerra #03")? "Colibacilosis neonatal"                  :
                                          "Animal sano");
        cmbGravedad.setValue(
            animal.equals("Toro #14")   ? "Moderada" :
            animal.equals("Vaca #07")   ? "Moderada" :
            animal.equals("Toro #22")   ? "Leve"     :
            animal.equals("Becerra #03")? "Grave"    :
                                          "Leve");
        cmbPronostico.setValue(
            animal.equals("Toro #14")   ? "Reservado"  :
            animal.equals("Vaca #07")   ? "Favorable"  :
            animal.equals("Toro #22")   ? "Favorable"  :
            animal.equals("Becerra #03")? "Reservado"  :
                                          "Favorable");

        // ── Pestaña 3: Tratamiento ────────────────────────────────────────────
        medicamentos.clear();
        if (animal.equals("Toro #14")) {
            medicamentos.add(new String[]{"Enrofloxacina",     "5 mg/kg",  "IM",   "1 vez/día x 5 días"});
            medicamentos.add(new String[]{"Flunixin meglumine", "2.2 mg/kg","IV",   "Dosis única"});
        } else if (animal.equals("Vaca #07")) {
            medicamentos.add(new String[]{"Oxitetraciclina LA",  "20 mg/kg", "IM",   "1 vez cada 3 días x 2 dosis"});
            medicamentos.add(new String[]{"Dexametasona",        "0.1 mg/kg","IM",   "Dosis única"});
        } else if (animal.equals("Toro #22")) {
            medicamentos.add(new String[]{"Penicilina G procaínica","22 000 UI/kg","IM","2 veces/día x 7 días"});
            medicamentos.add(new String[]{"Ketoprofeno",            "3 mg/kg",     "IV","1 vez/día x 3 días"});
        } else if (animal.equals("Becerra #03")) {
            medicamentos.add(new String[]{"Suero oral electrolítico","2 L",      "Oral","4 veces/día x 3 días"});
            medicamentos.add(new String[]{"Trimetoprim-sulfametoxazol","15 mg/kg","Oral","2 veces/día x 5 días"});
        } else {
            medicamentos.add(new String[]{"Ivermectina","200 mcg/kg","SC","Dosis única (preventivo)"});
        }
        txtIndicaciones.setText(
            animal.equals("Toro #14")   ? "Aislar del hato. Mantener hidratado. Revisión en 5 días." :
            animal.equals("Vaca #07")   ? "Separar de otras vacas. Control de temperatura diario. No ordeñar leche durante tratamiento." :
            animal.equals("Toro #22")   ? "Limpieza del casco cada 48 h. Vendaje protector. Piso seco." :
            animal.equals("Becerra #03")? "Calostro artificial si no mama. Temperatura corporal cada 6 h. Aislamiento del resto." :
                                          "Próxima desparasitación en 6 meses. Vacunación al día.");
        txtFechaControl.setText(
            animal.equals("Toro #14")   ? "01/05/2026" :
            animal.equals("Vaca #07")   ? "03/05/2026" :
            animal.equals("Toro #22")   ? "10/05/2026" :
            animal.equals("Becerra #03")? "29/04/2026" :
                                          "26/10/2026");

        // ── Pestaña 4: Vacunación ─────────────────────────────────────────────
        boolean esHembra = animal.startsWith("Vaca") || animal.startsWith("Becerra");
        vacunasExp.clear();
        vacunasExp.add(new String[]{"10/01/2026","Carbosap (Carbón Sintomático)",
            "Carbón sintomático / edematoso","2 ml","SC","10/07/2026","LT-2024-08","Dr. Martínez"});
        vacunasExp.add(new String[]{"10/01/2026","Clostridium 8-way",
            "Clostridiosis (8 serotipos)","5 ml","IM","10/07/2026","CL-2024-12","Dr. Martínez"});
        vacunasExp.add(new String[]{"15/01/2026","Leptavoid-H",
            "Leptospirosis (5 serogrupos)","5 ml","SC","15/07/2026","LP-2025-01","Dr. Martínez"});
        vacunasExp.add(new String[]{"20/01/2026","Triangle 10 (IBR/DVB)",
            "IBR, DVB, PI3, BRSV","2 ml","IM","20/01/2027","TR-2025-02","Dr. Martínez"});
        if (esHembra)
            vacunasExp.add(new String[]{"25/01/2026","RB51 (Brucella abortus)",
                "Brucelosis","2 ml","SC","Dosis única (permanente)","RB-2025-03","Dr. Martínez"});
        vacunasExp.add(new String[]{"01/03/2026","Rabia paralítica bovina",
            "Rabia","2 ml","SC","01/03/2027","RB-2025-04","Dr. Martínez"});
        vacunasExp.add(new String[]{"15/03/2026","Ivomec (desparasitante)",
            "Parásitos internos/externos","1 ml/50kg","SC","15/09/2026","IV-2025-05","Dr. Martínez"});

        // Estado vacunal resumen
        txtExpCarbon.setText("✔ 10/01/2026 — prox. 10/07/2026");
        txtExpBrucela.setText(esHembra ? "✔ 25/01/2026 — permanente" : "N/A (macho)");
        txtExpIBR.setText("✔ 20/01/2026 — prox. 20/01/2027");
        txtExpLepto.setText("✔ 15/01/2026 — prox. 15/07/2026");
        txtExpClostridios.setText("✔ 10/01/2026 — prox. 10/07/2026");
        txtExpObsVacunas.setText("Esquema de vacunación al día. Próxima revisión integral: julio 2026.");
    }

    @FXML
    public void irDashboard() {
        try { MainApp.irA("views/dashboard.fxml"); } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
