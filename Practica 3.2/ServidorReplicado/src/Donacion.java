
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
public class Donacion extends UnicastRemoteObject implements Donacion_I{
    
    private ArrayList<Usuario> usuarios;
    private double totalDonado;
    private String nombre;
    private String replica; //creo una instancia del otro servidor
    private String host;
    
    public Donacion(String nombre, String replica, String host) throws RemoteException{
        this.nombre = nombre;
        this.totalDonado = 0.0;
        this.usuarios = new ArrayList<>();
        this.replica = replica;
        this.host = host;
    }
    
    private Donacion_I buscarReplica(){
        Donacion_I re = null; //si no hago esto me da error por no inicializar la variable
        try{
            Registry reg = LocateRegistry.getRegistry(this.host,1099);
            re =  (Donacion_I)reg.lookup(this.replica);
        } catch(NotBoundException | RemoteException e){
            System.out.println("Exception: " + e.getMessage());
        }
        return re;
    }

    
    @Override
    public void registrarUsuario(String nombre, String contrasena) throws RemoteException {
        Donacion_I re = this.buscarReplica();
        if(re.getUsuarios().size() < this.usuarios.size()){
            re.registrarUsuario(nombre, contrasena);
        }
        else
        {
            Usuario usuario = new Usuario(nombre, contrasena);
            this.usuarios.add(usuario);
        }
    }//hay que comprobar que no esté ya en el sistema y todo eso, mirar bien guion

    @Override
    public ArrayList<Usuario> getUsuarios() throws RemoteException {
        return this.usuarios;
    }

    
}
