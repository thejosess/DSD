
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
    private double totalDonado;
    private String nombre;
    private String host;
    private String nombre_replica;
    private Donacion_I replica;
    
    public DonacionReplicada(String nombre, String nombre_replica, String host) throws RemoteException{
        this.nombre = nombre;
        this.usuarios = new ArrayList<>();
        this.host = host;
        this.nombre_replica = nombre_replica;
        this.replica = null;
        this.totalDonado = 0.0;
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

    //tiene que ser igual o puedo implementarlo diferente porque es la replica?
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
