package com.example.demo.models;

public class Visita {
    private String id;
    private String propietario;
    private String mascota;
    private String fecha;
    private String motivo;
    private String estado;

    public Visita(String id, String propietario, String mascota, String fecha, String motivo, String estado) {
        this.id = id;
        this.propietario = propietario;
        this.mascota = mascota;
        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = estado;
    }

    public String getId() { return id; }
    public String getPropietario() { return propietario; }
    public String getMascota() { return mascota; }
    public String getFecha() { return fecha; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }

    public void setId(String id) { this.id = id; }
    public void setPropietario(String propietario) { this.propietario = propietario; }
    public void setMascota(String mascota) { this.mascota = mascota; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setEstado(String estado) { this.estado = estado; }
}
