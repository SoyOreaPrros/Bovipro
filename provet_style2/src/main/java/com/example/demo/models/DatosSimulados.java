package com.example.demo.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatosSimulados {

    public static ObservableList<Visita> getVisitas() {
        ObservableList<Visita> lista = FXCollections.observableArrayList();
        lista.add(new Visita("V001", "Rancho El Roble",  "Toro #14",    "21/04/2026", "Vacunación brucelosis", "Completada"));
        lista.add(new Visita("V002", "Granja Los Pinos", "Vaca #07",    "21/04/2026", "Revisión general",      "En espera"));
        lista.add(new Visita("V003", "Rancho La Loma",   "Becerra #03", "22/04/2026", "Desparasitación",       "Pendiente"));
        lista.add(new Visita("V004", "Hacienda Morales", "Toro #22",    "22/04/2026", "Herida en pezuña",      "En consulta"));
        lista.add(new Visita("V005", "Rancho San Juan",  "Vaca #11",    "23/04/2026", "Control de peso",       "Pendiente"));
        return lista;
    }

    public static ObservableList<Mascota> getMascotas() {
        ObservableList<Mascota> lista = FXCollections.observableArrayList();
        lista.add(new Mascota("M001", "Toro #14",    "Bovino", "Angus",       "Rancho El Roble",  "4 años"));
        lista.add(new Mascota("M002", "Vaca #07",    "Bovino", "Holstein",    "Granja Los Pinos", "3 años"));
        lista.add(new Mascota("M003", "Becerra #03", "Bovino", "Simmental",   "Rancho La Loma",   "8 meses"));
        lista.add(new Mascota("M004", "Toro #22",    "Bovino", "Brahman",     "Hacienda Morales", "5 años"));
        lista.add(new Mascota("M005", "Vaca #11",    "Bovino", "Suizo Pardo", "Rancho San Juan",  "2 años"));
        lista.add(new Mascota("M006", "Becerro #09", "Bovino", "Hereford",    "Granja Los Pinos", "6 meses"));
        return lista;
    }

    public static ObservableList<Producto> getProductos() {
        ObservableList<Producto> lista = FXCollections.observableArrayList();
        lista.add(new Producto("P001", "Vacuna Brucelosis",     "Vacunas",     "60",  "$85.00",  "OK"));
        lista.add(new Producto("P002", "Oxitetraciclina 20%",   "Antibiótico", "25",  "$42.00",  "OK"));
        lista.add(new Producto("P003", "Jeringa 20ml",          "Material",    "150", "$3.50",   "OK"));
        lista.add(new Producto("P004", "Ivermectina 1%",        "Antiparasit", "6",   "$55.00",  "Bajo stock"));
        lista.add(new Producto("P005", "Guantes Largo Bovino",  "Material",    "4",   "$12.00",  "Bajo stock"));
        lista.add(new Producto("P006", "Suero Glucosado 500ml", "Solución",    "20",  "$18.00",  "OK"));
        lista.add(new Producto("P007", "Vitamina ADE",          "Vitaminas",   "30",  "$28.00",  "OK"));
        return lista;
    }

    public static ObservableList<Propietario> getPropietarios() {
        ObservableList<Propietario> lista = FXCollections.observableArrayList();
        lista.add(new Propietario("R001", "Carlos Mendoza",  "Rancho El Roble",  "228-111-2233", "Coatepec, Ver.",   "12"));
        lista.add(new Propietario("R002", "Sofía Herrera",   "Granja Los Pinos", "228-444-5566", "Xalapa, Ver.",     "8"));
        lista.add(new Propietario("R003", "Roberto Lara",    "Rancho La Loma",   "271-222-3344", "Veracruz, Ver.",   "20"));
        lista.add(new Propietario("R004", "Ernesto Morales", "Hacienda Morales", "229-555-6677", "Orizaba, Ver.",    "35"));
        lista.add(new Propietario("R005", "María Fuentes",   "Rancho San Juan",  "272-888-9900", "Córdoba, Ver.",    "15"));
        return lista;
    }

    public static String[] getRazasBovinas() {
        return new String[]{
            "Angus", "Brahman", "Charolais", "Hereford", "Holstein",
            "Limousin", "Simmental", "Suizo Pardo", "Zebu", "Criollo"
        };
    }
}

