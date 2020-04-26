# Práctica 3.2: RMI    ~     José Santos Salvador
## Creación servidor replicado y donaciones.


el dinero total lo tiene El donacion tocho no el replicado

en vez de hacer como este tio con getReplica, 
hacer directamente un atributo que sea la replica
asi el cliente no puede acceder a esta replica porque es privada

en vez de hacerlo con menu hacerlo pasandolo por parametro??


//almacena el dinero donde se ha registrado
//permite que los usuarios puedan ver el total donado por su parte, el total donado en general PERO SOLO SI SE HAN REGISTRADO EN ESE SERVIDOR ANTES.

//he hecho que cuando haya mas clientes en uno se le pase al otro servidor? y en caso de empate??
//el cliente podra acceder al nombre de la replica del otro? -> NOPE porque es un atributo privado ese nombre,

https://github.com/danidiaz1/DSD-Desarrollo-de-Sistemas-Distribuidos-UGR/tree/master/P2_RMI
//version tocha


he intentado crear una instancia dentro de cada servidor de una replica del otro,
sin embargo tuve problemas ya que al hacer esto en el servidor1
public class Servidor1{

    public static void main(String [] args){
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String nombre_server = "servidor1", nombre_replica = "servidor2", host = "localhost";
            Registry reg = LocateRegistry.createRegistry(1099);
            Donacion servidor1 = new Donacion(nombre_server,nombre_replica,host);
            Naming.rebind("servidor1",servidor1);
            
            System.out.println(nombre_server + " preparado");
        } catch ( RemoteException | MalformedURLException e){
            System.out.println("Exception: " + e.getMessage());
        }
        
    }    
}
y en el objeto Donaccion de servidor 1.
    public Donacion(String nombre, String replica, String host) throws RemoteException{
        this.crearReplica(replica, host);
        this.nombre = nombre;
        this.totalDonado = 0.0;
        this.usuarios = new ArrayList<>();
    }

    private void crearReplica(String replica, String host){
        try{
            Registry reg = LocateRegistry.getRegistry(host,1099);
            this.replica = (Donacion_I)reg.lookup(replica);
        } catch(NotBoundException | RemoteException e){
            System.out.println("Exception: " + e);
        }//DA ERROR AQUI
    }
//el objeto que se intenta buscar no se encuentra puesto que primero lanzas el servidor 1 y no está creada la replica,
al cambiarlo e intentar crear en el objeto Donacion del servidor1 cuando llamas a crearReplica, hacer el Naming.rebind, me daba un error al llamar al constructor de la replica, ya que hace:
    private void crearReplica(String replica, String host){
        try{
            Registry reg = LocateRegistry.getRegistry(host,1099);
            this.replica = (Donacion_I)reg.lookup(replica);
        } catch(NotBoundException | RemoteException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
y tampoco se ha terminado de crear todavia el objeto servidor1

Por tanto descarté ponerlo en el constructor, pensé en llamar luego a construir replica pero me obligaria a poner el método publico y podría acceder desde fuera, además de que se supone que eso se tendría que hacer desde el constrcutor,
tambien habia pensado escrbir de forma que Servidor1 creara DonacionReplicada (esta sin necesidad de asignar todavia DOnacion), tras crear en Servidor1 Donacion con DOnacionReplicada, llamaria a DonacionReplicada.setReplica o algo así y luego en servidor2 simplemente buscar servidor2


//PONER QUE USO INTERFAZ PARA EL USUARIO NO PUEDA ACCEDER A CIERTOS METODOS PRIVADOS

AÑADIR MAS COSAS
--------------------
añadir que puedas elegir a que servidor conectar, entonces
dependiendo de al servidor que te conectes pues uno es el servidor tocho y el otro es el replicado.
//es cuuestion de hacer muchos if-else y tal.

hacer que cuando le pida el usuario hacer x cosa, esto haga que el servidor pida ayuda al replicado en hacer esa operacion y cuando le devuelva la ayuda que lo haga.
mirar video de Pablo en go .ugr.es

bibliografia
http://chuwiki.chuidiang.org/index.php?title=Conceptos_b%C3%A1sicos_de_rmi
https://stackoverflow.com/questions/30589760/in-java-rmi-naming-rebindurl-obj-what-is-the-parameter-of-obj
https://docs.oracle.com/javase/7/docs/api/java/rmi/Naming.html#rebind(java.lang.String,%20java.rmi.Remote)