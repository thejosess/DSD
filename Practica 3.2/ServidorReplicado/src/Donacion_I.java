
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
    String getUsuarios() throws RemoteException; //mas seguridad si no devuelvo los clientes ¡¡para depurar
    int sizeUsuarios() throws RemoteException;
    boolean buscarUsuario(String nombre) throws RemoteException;
    boolean realizarDonacion(String nombre, String contrasena,double cantidad) throws RemoteException;
    double getTotalRecaudado(String nombre) throws RemoteException;
    double getRecaudado() throws RemoteException;
    Usuario getUsuario(String nombre) throws RemoteException;
}
