
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
    void registrarUsuario (String nombre, String contrasena) throws RemoteException;
    ArrayList<Usuario> getUsuarios() throws RemoteException;

}
