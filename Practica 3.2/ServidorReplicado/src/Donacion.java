
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
    
    private boolean buscarReplica(){
        
        boolean encontrada = true;
        
        if(this.replica == null){
            try{
                Registry reg = LocateRegistry.getRegistry(this.host,1099);
                this.replica =  (Donacion_I)reg.lookup(this.nombre_replica); //no hace falta crearla ya que se crea en el servidor2
                encontrada = true;
            } catch(NotBoundException | RemoteException e){
                encontrada = false;
            }
        }
        return encontrada;
    }

    
    @Override
    public void registrarUsuario(String nombre, String contrasena) throws RemoteException {
        if(this.buscarReplica())   //llamo por si no se ha inicializado
        {
            if(!this.buscarUsuario(nombre))
            {
                if(!this.replica.buscarUsuario(nombre)){
                    
                    if(this.replica.sizeUsuarios() < this.usuarios.size())
                    {
                        
                    this.replica.registrarUsuario(nombre, contrasena);
                    
                    }
                    else
                    {
                        this.anadirUsuario(nombre, contrasena);
                    }
                }
                else
                    System.out.println("Usuario ya registrado");
            }
            else
                System.out.println("Usuario ya registrado");
        }
        else
        {
            if(!this.buscarUsuario(nombre))
                this.anadirUsuario(nombre, contrasena);
            else
                System.out.println("Usuario ya registrado");
        }
    }


    private void anadirUsuario(String nombre, String contrasena){
        Usuario usuario = new Usuario(nombre, contrasena);
        this.usuarios.add(usuario);
    }
    
    @Override
    public int sizeUsuarios() throws RemoteException {
        return this.usuarios.size();
    }

    @Override
    public boolean buscarUsuario(String nombre) throws RemoteException {
        for(Usuario user : this.usuarios){
            if(user.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUsuarios() throws RemoteException {
        return this.usuarios.get(0).getNombre();
    }
    //QUITAR LUEGO

    
}
