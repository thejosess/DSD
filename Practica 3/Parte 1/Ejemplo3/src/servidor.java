import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

/**
 *
 * @author juse
 */
//en este ejemplo ya se lanza rmiregistry y no es necesario lanzarlo desde el terminal
public class servidor {
    public static void main(String [] args){
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            contador micontador = new contador();
            Naming.rebind("micontador",micontador);
            
            System.out.println("Sevidor RemoteException | MalformedURLExceptiondor preparada");
        } catch ( RemoteException | MalformedURLException e){
            System.out.println("Exception: " + e.getMessage());
        }
        
    }
}
