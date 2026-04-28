package com.example.demo.models;

public class Mascota {
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private String propietario;
    private String edad;

    public Mascota(String id, String nombre, String especie, String raza, String propietario, String edad) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.propietario = propietario;
        this.edad = edad;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getRaza() { return raza; }
    public String getPropietario() { return propietario; }
    public String getEdad() { return edad; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setRaza(String raza) { this.raza = raza; }
    public void setPropietario(String propietario) { this.propietario = propietario; }
    public void setEdad(String edad) { this.edad = edad; }
}
