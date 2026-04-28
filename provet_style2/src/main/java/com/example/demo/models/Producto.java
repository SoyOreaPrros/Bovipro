package com.example.demo.models;

public class Producto {
    private String id;
    private String nombre;
    private String categoria;
    private String cantidad;
    private String precio;
    private String estado;

    public Producto(String id, String nombre, String categoria, String cantidad, String precio, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getCantidad() { return cantidad; }
    public String getPrecio() { return precio; }
    public String getEstado() { return estado; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }
    public void setPrecio(String precio) { this.precio = precio; }
    public void setEstado(String estado) { this.estado = estado; }
}
