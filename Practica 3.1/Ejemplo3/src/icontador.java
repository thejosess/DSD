

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author juse
 */
public interface icontador extends Remote{
    int sumar() throws RemoteException;
    void sumar (int valor) throws RemoteException;
    public int incrementar() throws RemoteException;
}
