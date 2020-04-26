import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;



/**
 *
 * @author juse
 */
public class contador extends UnicastRemoteObject implements icontador{
    private int suma;
    
    public contador() throws RemoteException{
        
    }
    
    @Override
    public int sumar() throws RemoteException {
        return suma;
    }

    @Override
    public void sumar(int valor) throws RemoteException {
       suma = valor;
    }

    @Override
    public int incrementar() throws RemoteException {
        suma++;
        return suma;
    }
    
}
