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
    private String contrasena;
    private int donaciones;
    private double cantidad_donada;
    private double saldo;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.donaciones = 0;
        this.cantidad_donada = 0.0;
        this.saldo = 200;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public int getDonaciones() {
        return donaciones;
    }

    public double getCantidad_donada() {
        return cantidad_donada;
    }

    public void hacerDonacion() {
        this.donaciones++;
    }

    public void anadirCantidadDonada(double cantidad_donada) {
        this.cantidad_donada += cantidad_donada;
    }
    
    public boolean introducirContraseña(String contrasena){
        return contrasena.equals(this.contrasena);
    }
    
    public void setSaldo(double dinero){
        this.saldo += dinero;
    }
    
    public double getSaldo(){
        return this.saldo;
    }
}
