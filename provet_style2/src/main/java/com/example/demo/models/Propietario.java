package com.example.demo.models;

public class Propietario {
    private String id;
    private String nombre;
    private String rancho;
    private String telefono;
    private String municipio;
    private String animales;

    public Propietario(String id, String nombre, String rancho, String telefono, String municipio, String animales) {
        this.id = id;
        this.nombre = nombre;
        this.rancho = rancho;
        this.telefono = telefono;
        this.municipio = municipio;
        this.animales = animales;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRancho() { return rancho; }
    public String getTelefono() { return telefono; }
    public String getMunicipio() { return municipio; }
    public String getAnimales() { return animales; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRancho(String rancho) { this.rancho = rancho; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
}
