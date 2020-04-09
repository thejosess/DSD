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

Así de esta forma cuando vas a registrar un usuario, llama a buscarReplica para ver si puedes usarla o no, devuelve un bool con el estado de la operación (si se ha realizado de forma correcta el registro o no).

        @Override
    public boolean registrarUsuario(String nombre, String contrasena) throws RemoteException {
        boolean estado = true;
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
                {
                    System.out.println("Usuario ya registrado");
                    estado = false;
                }
            }
            else
            {
                System.out.println("Usuario ya registrado");
                estado = false;
            }
        }
        else
        {
            if(!this.buscarUsuario(nombre))
                this.anadirUsuario(nombre, contrasena);
            else
            {
                System.out.println("Usuario ya registrado");
                estado = false;
            }
        }
        return estado;
    }

Mira que puedas usar la réplica y luego compruebas cual de ellas tiene más usuario o si está registrado en alguno de los dos servidores.


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

                ...

        @Override
    public Usuario getUsuario(String nombre) throws RemoteException {
        Usuario usuario = null;
        for(Usuario user : this.usuarios){
            if(user.getNombre().equals(nombre)){
                return user;
            }
        }
        return usuario;
    }

Cabe destacar que el proceso para comprobar los métodos y su funcionamiento, primero lanzaba el servidor en modo debug con un br y luego lanzaba servidores y se me quedaba en dicho br para poder comprobar las variables, etc (lo comento porque tuve problemas para llegar a esta solución). 
La función principal del servidor, es permitir al usuario realizar donación bajo previo registro del mismo.   

    @Override
    public boolean realizarDonacion(String nombre, String contrasena,double cantidad) throws RemoteException {
        boolean estado = true;
        
        if(this.buscarUsuario(nombre)){
            this.totalDonado += cantidad;
            this.getUsuario(nombre).hacerDonacion();
            this.getUsuario(nombre).anadirCantidadDonada(cantidad);
        }
        else if(this.buscarReplica()){
            if(this.replica.buscarUsuario(nombre)){
                this.replica.realizarDonacion(nombre, contrasena, cantidad);
            }
        }
        else
            estado = false;
        
        return estado;
    }

Si el usuario está registrado en la otra réplica, se llama a la función de realizar donación de esa y se devuelve un valor true como estado de la operación realizada.    
Otra de las funciones que tenía que tener el servidor, era poder mostrar el dinero recaudado de forma total (ambos servidores). Primero cree una función que me devolviese el total recaudado por una instancia de Donacion_I

        @Override
    public double getRecaudado() throws RemoteException {
        return this.totalDonado;
    }

Luego hice otra función que devolviese el total recaudado contando con ambas réplicas y con la identificación del Usuario para poder sabes si ha realizado alguna donación o no (ya que si no ha donado nada, no puede ver el total recaudado)  

        @Override
    public double getTotalRecaudado(String nombre) throws RemoteException {
        double dinero = this.NO_PERMITIDO;
        Usuario usuario = this.getUsuario(nombre);
        
        if(usuario != null && usuario.getDonaciones() > 0){
            
            dinero = this.totalDonado;
            
            if(this.buscarReplica()){
                dinero += this.replica.getRecaudado();
            }            
        }
        return dinero;    
    }

Usuario es una clase que almacena la información relativa a cada usuario, luego cada servidor tiene un ArrayList de Usuarios.

    public class Usuario {
    private String nombre;
    private String contrasena;
    private int donaciones;
    private double cantidad_donada;
            ...
            
NO_PERMITIDO es una constante con valor -1, para poder así saber que el usuario no había relizado ninguna donación o no se encuentra en registrado.