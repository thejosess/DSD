

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author juse
 */
public interface Ejemplo_I extends Remote{
    public void escribir_mensaje(String mensaje)throws RemoteException;
}
