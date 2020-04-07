
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
    
    private ArrayList<Usuario> usuarios;    //revisar esto al ahora de buscar
    private double totalDonado;
    private String nombre;
    private String nombre_replica; //creo una instancia del otro servidor
    private String host;
    private Donacion_I replica;
    
    public Donacion(String nombre, String nombre_replica, String host) throws RemoteException{
        this.nombre = nombre;
        this.totalDonado = 0.0;
        this.usuarios = new ArrayList<>();
        this.nombre_replica = nombre_replica;
        this.host = host;
        this.replica = null;
    }
    
    private void buscarReplica(){
        
        if(this.replica == null){
            try{
                Registry reg = LocateRegistry.getRegistry(this.host,1099);
                this.replica =  (Donacion_I)reg.lookup(this.nombre_replica); //no hace falta crearla ya que se crea en el servidor2
            } catch(NotBoundException | RemoteException e){
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }

    
    @Override
    public void registrarUsuario(String nombre, String contrasena) throws RemoteException {
        this.buscarReplica();   //llamo por si no se ha inicializado
        
        if(this.replica.sizeUsuarios() < this.usuarios.size()){
            this.replica.registrarUsuario(nombre, contrasena);
        }
        else
        {
            Usuario usuario = new Usuario(nombre, contrasena);
            this.usuarios.add(usuario);
        }
    }//hay que comprobar que no esté ya en el sistema y todo eso, mirar bien guion

    private void anadirUsuario(){
        
    }
    
    @Override
    public int sizeUsuarios() throws RemoteException {
        return this.usuarios.size();
    }

    @Override
    public boolean buscarUsuario(String nombre) throws RemoteException {
        return true;
    }

    
}
