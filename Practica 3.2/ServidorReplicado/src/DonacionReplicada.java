
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
public class DonacionReplicada extends UnicastRemoteObject implements Donacion_I{
    
    private ArrayList<Usuario> usuarios;
    private String nombre;
    private Donacion_I replica;
    
    public DonacionReplicada(String nombre, String replica, String host) throws RemoteException{
        //this.crearReplica(replica, host);
        this.nombre = nombre;
        this.usuarios = new ArrayList<>();
    }
    
    private void crearReplica(String replica, String host){
        try{
            Registry reg = LocateRegistry.getRegistry(host,1099);
            this.replica = (Donacion_I)reg.lookup(replica);
        } catch(NotBoundException | RemoteException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }

    //tiene que ser igual o puedo implementarlo diferente porque es la replica?
    @Override
    public void registrarUsuario(String nombre, String contrasena)  {
        Usuario usuario = new Usuario(nombre, contrasena);
        this.usuarios.add(usuario);
    }

    @Override
    public ArrayList<Usuario> getUsuarios() throws RemoteException {
       return this.usuarios;
    }

}
