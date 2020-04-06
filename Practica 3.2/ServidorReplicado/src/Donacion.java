
import java.net.MalformedURLException;
import java.rmi.Naming;
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
    private Donacion_I replica; //creo una instancia del otro servidor
    
    public Donacion(String nombre, String replica, String host) throws RemoteException{
        this.crearReplica(replica, host);
        this.nombre = nombre;
        this.totalDonado = 0.0;
        this.usuarios = new ArrayList<>();
    }
    
    private void crearReplica(String replica, String host) throws RemoteException{

        /*try{
            Registry reg = LocateRegistry.getRegistry(host,1099);
            this.replica = (Donacion_I)reg.lookup(replica);
        } catch(NotBoundException | RemoteException e){
            System.out.println("Exception: " + e);
        }//DA ERROR AQUI*/
        
                try {
            //Registry reg = LocateRegistry.createRegistry(1099);
            DonacionReplicada servidor2 = new DonacionReplicada(replica,this.nombre,host);
            Naming.rebind("servidor2",servidor2);
            
            System.out.println("servidor replica" + replica + " creado");
        } catch ( RemoteException | MalformedURLException e){
            System.out.println("Exception: " + e.getMessage());
    }
    }
    
    @Override
    public void registrarUsuario(String nombre, String contrasena) throws RemoteException {
        if(replica.getUsuarios().size() < this.usuarios.size()){
            replica.registrarUsuario(nombre, contrasena);
        }
        else
        {
            Usuario usuario = new Usuario(nombre, contrasena);
            this.usuarios.add(usuario);
        }
    }

    @Override
    public ArrayList<Usuario> getUsuarios() throws RemoteException {
        return this.usuarios;
    }

    
}
