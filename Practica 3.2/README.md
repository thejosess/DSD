# Práctica 3.2: RMI    ~     José Santos Salvador
## Creación servidor replicado y donaciones.


# Introducción
//hablar como hago siempre de RMI y de la práctica de forma teorica, ponerle alguna foto suya del pdf.

# Creación servidor y servidor replicado

En un inicio cree una primera versión algo básica similar al ejemplo 2

    public class Servidor1 implements Servidor_I{

        private ArrayList<Usuario> usuarios;
        private double totalDonado;
        private String nombre;
        private String replica; //creo una instancia del otro servidor
        private String host;

        public static void main(String[] args) {
            if (System.getSecurityManager() == null){
                System.setSecurityManager(new SecurityManager());
            }
            try{
                Servidor_I prueba = new Servidor1();
                Servidor_I stub = (Servidor_I) UnicastRemoteObject.exportObject(prueba,0);
                Registry registry = LocateRegistry.getRegistry();
                String nombre_objeto_remote = "un_nombre_para_obj_remoto";
                registry.rebind(nombre_objeto_remote, stub);
                System.out.println("Servidor1 bound");
            }catch (Exception e){
                System.err.println("Servidor1 exception:");
                e.printStackTrace();
            }
        }

        @Override
        public synchronized void registrarUsuario(String usuario, String contrasena) throws RemoteException {
                    ... 
        }
    }

acabe desechando esta opción, ya que me obligaba a tener una serie de atributos en el servidor y métodos (respecto a las donaciones), mezclando así las responsabilidades de las clases. Decidí hacerlo más similar al ejemplo 3, creando una interfaz Donacion_I y dos clases concretas Donacion y DonacionReplicada donde meter las funcionalidades de registrar usuarios, crear donaciones, etc. La clase Donacion sería un atributo que tendría el servidor "principal" (Servidor1) y DonacionReplicada del servidor secundario (Servidor2).
//meter la interfaz cuando esté completa
Uno de los fallos que tuve fué poner en el main de ambos servidores (Servidor1 y Servidor2):

    Registry reg = LocateRegistry.createRegistry(1099);

Esto me produjo un error como es normal y decidí por tanto, dejarlo unicamente en el Servidor1.   
Uno de los puntos de esta práctica es la comunicación entre ambos servidores. Para ello pensé en crear un atributo Donacion_I en ambos servidores e inicializarla en sus constructores.


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

y en Donacion de Servidor1

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

entonces al meterse en crearReplica intenta buscar con reg.lookup(replica) un objeto que no ha sido creado, ya que primero se lanza el main de Servidor1. Lo cambié para que al crear el objeto Donacion en el servidor1, en el método crearReplica, se crease el objeto replica(DonacionReplicada) con Naming.rebind pero volvía a dar un error ya que al crear el objeto DonacionReplicada, esta llamaba a crearReplica de Servidor1. Pensé en quitar crearReplica en Servidor2 y luego hacer Servidor2.setReplica() tras haber creado ya Donacion y DonacionReplicada pero me pareció un poco "feo" hacerlo de esa forma.  
Otra posibilidad hubiese sido tener el atributo replica que hace referencia al otro servidor y cuando llamases a obtenerReplica, si no estaba creado, crearlo. El problema en este caso sería que haría falta escribir despues de la llamada a obtenerReplica

    if(this.replica == null){
        this.crearReplica();
    }
o hacerlo dentro de obtenerReplica pero eso obligaría a en cada función que llame a obtenerReplica, usar try and catch.

Al final opté por crear un método buscarReplica en Donacion y DonacionReplicada, donde se busca la replica, a través de su nombre que es un atributo de dichas clases. Si la encuentra, inicializa el atributo y devuelve true y si no la encuentra (porque el servidor no este lanzado) devuelve false. Dicho método sería privado para que el Cliente no puediese llamarlo el cliente.

        //Donacion.java

    private boolean buscarReplica(){
        
        boolean encontrada = true;
        //si no la encuentra devuelve false y el servidor1 lo hace sin llamar a la replica
        
        if(this.replica == null){
            try{
                Registry reg = LocateRegistry.getRegistry(this.host,1099);
                this.replica =  (Donacion_I)reg.lookup(this.nombre_replica); 
                
                encontrada = true;
            } catch(NotBoundException | RemoteException e){
                encontrada = false;
            }
        }
        return encontrada;
    }

Así de esta forma cuando vas a registrar un usuario, llamar a buscarReplica para ver si puedes usarla o no.

    @Override
    public void registrarUsuario(String nombre, String contrasena) throws RemoteException {
        if(this.buscarReplica())   //llamo por si no se ha inicializado
        {
            if(!this.buscarUsuario(nombre))
            {
                if(!this.replica.buscarUsuario(nombre)){
                    
                    if(this.replica.sizeUsuarios() < this.usuarios.size())
                    {
                        
                    this.replica.registrarUsuario(nombre, contrasena);
                    
                    }
                    else
                    {
                        this.anadirUsuario(nombre, contrasena);
                    }
                }
                else
                    System.out.println("Usuario ya registrado");
            }
            else
                System.out.println("Usuario ya registrado");
        }
        else
        {
            if(!this.buscarUsuario(nombre))
                this.anadirUsuario(nombre, contrasena);
            else
                System.out.println("Usuario ya registrado");
        }
    }

Mira que puedas usar la réplica y luego compruebas cual de ellas tiene mas usuario o si está registrado en alguno de los dos servidores.


    private void anadirUsuario(String nombre, String contrasena){
        Usuario usuario = new Usuario(nombre, contrasena);
        this.usuarios.add(usuario);
    }

    @Override
    public int sizeUsuarios() throws RemoteException {
        return this.usuarios.size();
    }

    @Override
    public boolean buscarUsuario(String nombre) throws RemoteException {
        for(Usuario user : this.usuarios){
            if(user.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }


Cabe destacar que el proceso para comprobar los métodos y su funcionamiento, primero lanzaba el servidor en modo debug con un br y luego lanzaba servidores y se me quedaba en dicho br para poder comprobar las variables, etc (lo comento porque tuve problemas para llegar a esta solución).

para depurar usé:
    //String getUsuarios() throws RemoteException; //mas seguridad si no devuelvo los clientes ¡¡para depurar

y primero lanzaba el servidor en modo debug y ponia un br donde quería y luiego lanzaba cliente y se paraba al llegar allí