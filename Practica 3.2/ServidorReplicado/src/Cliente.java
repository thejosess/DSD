import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry; 
import java.util.Scanner;

/**
 *
 * @author juse
 */
public class Cliente {
    public static void main(String[] args){
        boolean seguir = true, identificado = false; 
        double importe, recaudado = 0, saldo = 0;
        Usuario user = null;
        int opcion;
        Scanner in = new Scanner (System.in);
        String nombre, contrasena, nombre2, contrasena2;

        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry mireg = LocateRegistry.getRegistry(args[0],1099);
            Donacion_I midonacion = (Donacion_I)mireg.lookup("servidor1"); 
            
            while(seguir){
                System.out.print("\nServicios ofrecicidos por el servidor: ");
                System.out.print("\n1.Registrarse\n2.Realizar donación\n3.Visualizar total recaudado\n4.Mostrar veces y cantidad donada por usuario\n5.Mostrar saldo usuario\n6.Hacer transferencia entre usuarios\n7.Terminar\n ");
                System.out.print("\nIntroduzca la operación que desea realizar: ");
                opcion = in.nextInt(); in.skip("\n");
                System.out.println();
                switch(opcion)
                {
                    case 1:
                        System.out.print("\nIntroduzca el usuario que quiere usar: ");
                        nombre = in.nextLine();
                        System.out.print("\nIntroduzca la contraseña: ");
                        contrasena = in.nextLine();
                        if(midonacion.registrarUsuario(nombre, contrasena))
                            System.out.println("\nRegistrado completado con exito");
                        else
                            System.out.println("\nUsuario ya registrado, pruebe otro nombre");
                           
                        break;
                        
                    case 2:
    
                        System.out.print("\nIntroduzca el usuario que quiere usar: ");
                        nombre = in.nextLine();
                        System.out.print("\nIntroduzca la contraseña: ");
                        contrasena = in.nextLine();
                        System.out.print("\nIntroduzca el importe que desea donar: ");
                        importe = in.nextInt(); in.skip("\n");
                        if(midonacion.realizarDonacion(nombre, contrasena, importe)){
                            System.out.println("Donacion realizada");
                        }
                        else{
                            System.out.println("Donacion no realizada");
                        }
                        
                        break;
                        
                    case 3:
                        
                        System.out.print("\nIntroduzca el usuario: ");
                        nombre = in.nextLine();
                        recaudado = midonacion.getTotalRecaudado(nombre);
                        if(recaudado != -1){
                            System.out.println("\nActualmente hay recaudados " + recaudado + " de euros \n");
                        }
                        else{
                            System.out.println("\nNo puede ver el total recaudado\n");
                        }
                        
                        break;
                    
                    case 4:
                        System.out.print("\nIntroduzca el usuario que quiere usar: ");
                        nombre = in.nextLine();
                        System.out.print("\nIntroduzca la contraseña: ");
                        contrasena = in.nextLine();
                        
                        System.out.print("\n Has donado " +midonacion.getDonacionesUsuario(nombre)+ " veces y un total de " + midonacion.getCantidadDonacionesUsuario(nombre)+"\n");
       
                    break;
                     
                    case 5:
                        System.out.print("\nIntroduzca el usuario que quiere usar: ");
                        nombre = in.nextLine();
                        System.out.print("\nIntroduzca la contraseña: ");
                        contrasena = in.nextLine();
                        saldo = midonacion.getSaldoUsuario(nombre, contrasena);
                        if(saldo != -1) 
                            System.out.print("\nEl saldo es: " + saldo + "\n");
                        else
                            System.out.print("\nNo se puede consultar el saldo\n");
                    break;
                        
                    case 6:
                       System.out.print("\nIntroduzca el usuario 1 que quiere donar dinero al usuario 2: ");
                       nombre = in.nextLine();
                       System.out.print("\nIntroduzca la contraseña: ");
                       contrasena = in.nextLine();
                       System.out.print("\nIntroduzca el usuario 2 que quiere recibir dinero del usuario 1: ");
                       nombre2 = in.nextLine();
                       System.out.print("\nIntroduzca la contraseña: ");
                       contrasena2 = in.nextLine();
                       
                       System.out.print("\nIntroduzca el importe que desea transferir: ");
                       importe = in.nextInt(); in.skip("\n");

                       saldo = midonacion.getSaldoUsuario(nombre, contrasena);
                       System.out.print("\nEl saldo del usuario 1 antes de la transferencia es: " + saldo + "\n");       
                       saldo = midonacion.getSaldoUsuario(nombre2, contrasena2);
                       System.out.print("\nEl saldo del usuario 2 antes de la transferencia es: " + saldo + "\n");
                       
                       if(midonacion.transferenciaDinero(nombre, contrasena, nombre2, contrasena2, importe))
                       {
                       System.out.print("\nTransferencia realizada con exito\n");
                       saldo = midonacion.getSaldoUsuario(nombre, contrasena);
                       System.out.print("\nEl saldo del usuario 1 despues de la transferencia es: " + saldo + "\n");       
                       saldo = midonacion.getSaldoUsuario(nombre2, contrasena2);
                       System.out.print("\nEl saldo del usuario 2 despues de la transferencia es: " + saldo + "\n");
                       }
                       else
                           System.out.print("No se ha podido realizar la transferencia realizada con exito");

                    break;
                    
                    
                    case 7:
                        seguir = false;
                        break;
                }
                        
            }
        }catch (NotBoundException | RemoteException e){
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}
