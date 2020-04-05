# Práctica 3.2: RMI    ~     José Santos Salvador
## Creación servidor replicado y donaciones.



opte por usar un objeto para que asi cliente no lo supiera

https://github.com/AlexRuiz7/UGR-DSD/blob/master/P3/RMI/Donacion.java
//version cutre de primeras

//poner en el README que primero hice una version cutre parecida al ejemplo 2 y copiar del github de arriba.
//poner que primero hice un objeto de donacion
//luego hice la otra

//otro de los fallos es que hacia en ambos servidores este trozo de código:
Registry reg = LocateRegistry.createRegistry(1099);
//ME di cuenta de que tenia que crear el servidor replica diferente ya que no es un servidor como tal si no que es un servidor a ojos del cliente pero es un cliente a ojos del servidor. POR ESO IMPLEMENTA LAS DOS INTERFACE
//tuve otro error con eso porque no implementaba las dos interfaces y entonces me daba error, el servidor tiene que implementar ambas interfaces

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