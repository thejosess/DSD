
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
public class Servidor2 {
    private ArrayList<Usuario> clientes; 

    public static void main(String [] args){
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            //Registry reg = LocateRegistry.createRegistry(1099);
            String nombre_server = "servidor2", nombre_replica = "servidor1", host = "localhost";
            DonacionReplicada servidor2 = new DonacionReplicada(nombre_server,nombre_replica,host);
            Naming.rebind("servidor2",servidor2);
            
            System.out.println("Servidor" + nombre_server + " preparado");
        } catch ( RemoteException | MalformedURLException e){
            System.out.println("Exception: " + e.getMessage());
        }
        
    }    
    
}
