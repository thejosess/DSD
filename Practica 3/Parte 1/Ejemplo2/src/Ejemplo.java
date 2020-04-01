/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author juse
 */
public class Ejemplo implements Ejemplo_I{

    /**
     * @param args the command line arguments
     */

     public Ejemplo(){
         super();
     }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            Ejemplo_I prueba = new Ejemplo();
            Ejemplo_I stub = (Ejemplo_I) UnicastRemoteObject.exportObject(prueba,0);
            Registry registry = LocateRegistry.getRegistry();
            String nombre_objeto_remote = "un_nombre_para_obj_remoto";
            registry.rebind(nombre_objeto_remote, stub);
            System.out.println("Ejemplo bound");
        }catch (Exception e){
            System.err.println("Ejemplo exception:");
            e.printStackTrace();
        }
    }

    @Override
    public void escribir_mensaje(String mensaje) throws RemoteException {
        System.out.println("Entra hebra: "+mensaje);
        if (mensaje.endsWith("0")){
            try{
                System.out.println("Empezamos a dormir");
                Thread.sleep(5000);
                System.out.println("Terminamos de dormir");
            }
            catch (Exception e) {
                System.err.println("Ejemplo excepion: ");
                e.printStackTrace();
            }
        }
        System.out.println("\nSale hebra "+ mensaje);
    }
}
