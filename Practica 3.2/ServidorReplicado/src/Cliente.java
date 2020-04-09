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
        double importe, recaudado = 0;
        Usuario user = null;
        int opcion;
        Scanner in = new Scanner (System.in);
        String nombre, contrasena;

        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry mireg = LocateRegistry.getRegistry("localhost",1099);
            Donacion_I midonacion = (Donacion_I)mireg.lookup("servidor1"); 
            
            while(seguir){
                System.out.print("\nServicios ofrecicidos por el servidor: ");
                System.out.print("\n1.Registrarse\n2.Identificarse\n3.Realizar donación\n4.Visualizar total recaudado\n5.Terminar\n ");
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
                        user = midonacion.getUsuario(nombre);
                        if(user != null && user.introducirContraseña(contrasena)){
                            System.out.println("\nUsuario identificado con exito\nn");
                            identificado = true;
                            
                        }
                        else{
                            System.out.println("\nUsuario no identificado");
                        }
                        break;
                    case 3:
                        if(identificado){
                            System.out.print("\nIntroduzca el importe que desea donar: ");
                            importe = in.nextInt(); in.skip("\n");
                            if(midonacion.realizarDonacion(user.getNombre(), user.getContrasena(), importe)){
                                System.out.println("Donacion realizada");
                            }
                            else{
                                System.out.println("Donacion no realizada");
                            }
                        }
                        else{
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
                        }
                        break;
                    case 4:
                        if(identificado){
                            recaudado = midonacion.getTotalRecaudado(user.getNombre());
                            if(recaudado != -1){
                                System.out.println("\nActualmente hay recaudados " + recaudado + " de euros \n");
                            }
                            else{
                                System.out.println("\nNo puede ver el total recaudado\n");
                            }
                        }
                        else{
                            System.out.print("\nIntroduzca el usuario: ");
                            nombre = in.nextLine();
                            importe = in.nextInt(); in.skip("\n");
                            recaudado = midonacion.getTotalRecaudado(nombre);
                            if(recaudado != -1){
                                System.out.println("\nActualmente hay recaudados " + recaudado + " de euros \n");
                            }
                            else{
                                System.out.println("\nNo puede ver el total recaudado\n");
                            }
                        }
                    case 5:
                        seguir = false;
                        break;
                }
                        
            }
            
                /* switch(opcion)
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
                        Usuario user = midonacion.getUsuario(nombre);
                        if(user != null && user.introducirContraseña(contrasena)){
                            System.out.println("\nUsuario identificado con exito\nn");
                            
                        }
                        else{
                            System.out.println("\nUsuario no identificado");
                            //hacer loop para que siga introduccion user?
                        }
                        //\n3.Realizar donación\n4.Visualizar total recaudado
                        break;
                    case 3:
                        seguir = false;
                        break;

                }*/

            /*midonacion.registrarUsuario("Prueba", "1234");
            midonacion.registrarUsuario("Otro", "1234");
            midonacion.realizarDonacion("Prueba", "1234", 5);
            midonacion.realizarDonacion("Otro", "1234", 5);
            System.out.println(midonacion.getRecaudado());
            System.out.println(midonacion.getTotalRecaudado("Prueba"));
            System.out.println(midonacion.getTotalRecaudado("Otro"));  //esto no ha realizado donacion y entonces caca*/


            
            System.out.println("Usuario registrado");
        }catch (NotBoundException | RemoteException e){
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}
