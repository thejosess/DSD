
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
    
    private static final double NO_PERMITIDO = -1;
    
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
    
    private boolean buscarReplica(){
        
        boolean encontrada = true;
        //si no la encuentra devuelve false y el servidor1 lo hace sin llamar a la replica
        
        if(this.replica == null){
            try{
                Registry reg = LocateRegistry.getRegistry(this.host,1099);
                this.replica =  (Donacion_I)reg.lookup(this.nombre_replica); 
                
                encontrada = true;
            } catch(NotBoundException | RemoteException e){
                encontrada = false;
            }
        }
        return encontrada;
    }

    @Override
    public boolean registrarUsuario(String nombre, String contrasena) throws RemoteException {
        boolean estado = true;
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
                {
                    System.out.println("Usuario ya registrado");
                    estado = false;
                }
            }
            else
            {
                System.out.println("Usuario ya registrado");
                estado = false;
            }
        }
        else
        {
            if(!this.buscarUsuario(nombre))
                this.anadirUsuario(nombre, contrasena);
            else
                System.out.println("Usuario ya registrado");
                estado = false;
        }
        return estado;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean realizarDonacion(String nombre, String contrasena,double cantidad) throws RemoteException {
        boolean estado = true;
        
        if(this.buscarUsuario(nombre)){
            this.totalDonado += cantidad;
            this.getUsuario(nombre).hacerDonacion();
            this.getUsuario(nombre).anadirCantidadDonada(cantidad);
        }
        else if(this.buscarReplica()){
            if(this.replica.buscarUsuario(nombre)){
                this.replica.realizarDonacion(nombre, contrasena, cantidad);
            }
        }
        else
            estado = false;
        
        return estado;
    }

    @Override
    public double getRecaudado() throws RemoteException {
        return this.totalDonado;
    }

    @Override
    public double getTotalRecaudado(String nombre) throws RemoteException {
        double dinero = this.NO_PERMITIDO;
        Usuario usuario = this.getUsuario(nombre);
        
        if(usuario != null && usuario.getDonaciones() > 0){
            
            dinero = this.totalDonado;
            
            if(this.buscarReplica()){
                dinero += this.replica.getRecaudado();
            }            
        }

        //devuelve -1 que signifca NO PERMITIDO y luego tratarlo 
        return dinero;    
    }

    @Override
    public Usuario getUsuario(String nombre) throws RemoteException {
        Usuario usuario = null;
        for(Usuario user : this.usuarios){
            if(user.getNombre().equals(nombre)){
                return user;
            }
        }
        return usuario;
    }
    

}
