
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author juse
 */
public class Cliente_Ejemplo implements Runnable{
    
    String nombre_objeto_remoto = "un_nombre_para_obj_remoto";
    public String server;
    
    public Cliente_Ejemplo (String server){
        this.server = server;
    }
    
    @Override
    public void run() {
       System.out.println("Buscando el objeto remoto");
       try{
           Registry registry = LocateRegistry.getRegistry(server);
           Ejemplo_I instancia_local = (Ejemplo_I) registry.lookup(nombre_objeto_remoto);
           System.out.println("Invocando el objeto remoto");
           instancia_local.escribir_mensaje(Thread.currentThread().getName());
       } catch (Exception e){
            System.err.println("Ejemplo_I exception:");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        
        int n_hebras = Integer.parseInt(args[1]);
        
        Cliente_Ejemplo[] v_clientes = new Cliente_Ejemplo[n_hebras];
        Thread[] v_hebras = new Thread[n_hebras];
        for(int i = 0; i < n_hebras; i++){
            v_clientes[i] = new Cliente_Ejemplo(args[0]);
            v_hebras[i] = new Thread(v_clientes[i], "Cliente" +i);
            v_hebras[i].start();
        }
    }

}
