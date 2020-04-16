
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
    
    private static final double NO_PERMITIDO = -1;
   
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
            {
                System.out.println("Usuario ya registrado");
                estado = false;
            }
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
    public boolean realizarDonacion(String nombre, String contrasena,double cantidad) throws RemoteException {
        boolean estado = true;
        Usuario user = null;
        
        if(this.buscarUsuario(nombre)){
            this.totalDonado += cantidad;
            user = this.getUsuario(nombre);
            user.hacerDonacion();
            user.anadirCantidadDonada(cantidad);
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
        Usuario usuario = this.getUsuario(nombre); //comprobar que está registrado?? está implicito no??
        
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
                usuario =  user;
            }
        }
        return usuario;
    }
    
    public int getDonacionesUsuario(String nombre) throws RemoteException{
        Usuario user = null;
        int valor = 0;
        
        if(this.buscarUsuario(nombre))
        {
            user = this.getUsuario(nombre);
            valor = user.getDonaciones();
        }
        else if(this.buscarReplica()){
            if(this.replica.buscarUsuario(nombre)){
                valor = replica.getDonacionesUsuario(nombre);
            }
        }
        
        return valor;
    }

    @Override
    public double getCantidadDonacionesUsuario(String nombre) throws RemoteException {
        Usuario user = null;
        double valor = 0;
        
        if(this.buscarUsuario(nombre))
        {
            user = this.getUsuario(nombre);
            valor = user.getCantidad_donada();
        }
        else if(this.buscarReplica()){
            if(this.replica.buscarUsuario(nombre)){
                valor = this.replica.getCantidadDonacionesUsuario(nombre);
            }
        }
        
        return valor;
    }

    @Override
    public double getSaldoUsuario(String nombre, String contrasena) throws RemoteException {
        double valor = this.NO_PERMITIDO;
        Usuario user = null;
        
        if(this.buscarUsuario(nombre))
        {
            user = this.getUsuario(nombre);
            if(user.introducirContraseña(contrasena))
                valor = user.getSaldo();
        }   
        else if(this.buscarReplica()){
            if(this.replica.buscarUsuario(nombre)){
                valor = this.replica.getSaldoUsuario(nombre,contrasena);
            }
        }
  
        return valor;
    }

    @Override
    public boolean transferenciaDinero(String u1, String pass1, String u2, String pass2, double cantidad) throws RemoteException {
        boolean estado = true;
        
        if(this.buscarUsuario(u1)){
            if(this.getUsuario(u1).introducirContraseña(pass1))
                this.getUsuario(u1).setSaldo(-cantidad);
        }
        else if(this.buscarReplica()){
            if(this.replica.buscarUsuario(u2)){
                this.replica.transferenciaDinero(u1,pass1,u2,pass2,cantidad);
            }
        }
        else
            estado = false;
        
        if(this.buscarUsuario(u2)){
            if(this.getUsuario(u2).introducirContraseña(pass2))
                this.getUsuario(u2).setSaldo(cantidad);
        }
        else if(this.buscarReplica()){
            if(this.replica.buscarUsuario(u2)){
                this.replica.transferenciaDinero(u1,pass1,u2,pass2,cantidad);
            }
        }
        else
            estado = false;
        
      
        return estado;
    }

}
