/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juse
 */
public class Usuario {
    private String nombre;
    private String contraseña;
    private int donaciones;
    private double cantidad_donada;

    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.donaciones = 0;
        this.cantidad_donada = 0.0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getDonaciones() {
        return donaciones;
    }

    public double getCantidad_donada() {
        return cantidad_donada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void hacerDonacion() {
        this.donaciones++;
    }

    public void anadirCantidadDonada(double cantidad_donada) {
        this.cantidad_donada += cantidad_donada;
    }
}
