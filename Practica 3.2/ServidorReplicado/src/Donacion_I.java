
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juse
 */
public interface Donacion_I  extends Remote{
    boolean registrarUsuario (String nombre, String contrasena) throws RemoteException;
    int sizeUsuarios() throws RemoteException;
    boolean buscarUsuario(String nombre) throws RemoteException;
    boolean realizarDonacion(String nombre, String contrasena,double cantidad) throws RemoteException;
    double getTotalRecaudado(String nombre) throws RemoteException;
    double getRecaudado() throws RemoteException;
    Usuario getUsuario(String nombre) throws RemoteException;
    int getDonacionesUsuario(String nombre) throws RemoteException;
    double getCantidadDonacionesUsuario(String nombre) throws RemoteException;
    double getSaldoUsuario(String nombre, String contrasena) throws RemoteException;
    boolean transferenciaDinero(String u1, String pass1, String u2, String pass2,double cantidad) throws RemoteException;
    

}
