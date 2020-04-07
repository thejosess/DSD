import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry; 

/**
 *
 * @author juse
 */
public class Cliente {
    public static void main(String[] args){
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry mireg = LocateRegistry.getRegistry("localhost",1099);
            Donacion_I midonacion = (Donacion_I)mireg.lookup("servidor1");

            midonacion.registrarUsuario("Prueba", "1234");
            midonacion.registrarUsuario("Otro", "1234");
            System.out.println(midonacion.getUsuarios());


            
            System.out.println("Usuario registrado");
        }catch (NotBoundException | RemoteException e){
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}
