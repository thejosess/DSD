import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry; 

/**
 *
 * @author juse
 */
public class cliente {
    public static void main(String[] args){
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry mireg = LocateRegistry.getRegistry("127.0.0.1",1099);
            icontador micontador = (icontador)mireg.lookup("micontador");
            System.out.println("Poniendo contador a 0");
            micontador.sumar(0);
            
            long horacomienzo = System.currentTimeMillis();
            
            System.out.println("Incrementando...");
            for(int i = 0 ; i < 1000 ; i++){
                micontador.incrementar();
            }
            
            long horafin = System.currentTimeMillis();
            System.out.println("Media de las RMI realizadas = " + ((horafin -horacomienzo)/1000f) + "mseg" );
            System.out.println("RMI realizadas = " + micontador.sumar());
        }catch (NotBoundException | RemoteException e){
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}
