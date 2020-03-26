## Memoria Práctica 2.2: Calculadora Thrift
---   
### 1.Calculadora básica 
La primera parte consistía en una calculadora capaz de realizar unas operaciones básicas (a las que le añadi el módulo). Para ello, primero se crea un archivo calculadora.thrift con las funciones que va a implementar el servidor que implementé en python.     


    service Calculadora{
        void ping(),
        i32 suma(1:i32 num1, 2:i32 num2),
        i32 resta(1:i32 num1, 2:i32 num2),
        i32 multiplicacion(1:i32 num1, 2:i32 num2),
        i32 division(1:i32 num1, 2:i32 num2),
        i32 modulo(1:i32 num1, 2:i32 num2),
    }

Para generar los archivos se ejecuta thrift señalando que van a ser en python.

    thrift -gen py calculadora.thrift

En esta práctica hay que tener en cuenta la diferencia entre el compilador de thrift (sentencia de arriba) que hay que instalar y las librerías o paquetes particulares de cada lenguaje (en python se puede con el gestor de paquetes pip)

    python -m pip install thrift

Tras generar los archivos, implementé las funciones en el servidor 

        def suma(self,n1,n2):
        print('sumando'+str(n1)+" con "+str(n2))
        return n1+n2

           ...

        def modulo(self,n1,n2):
        print('division '+str(n1)+ " con "+str(n2))
        return n1 % n2

Y las llamé desde el cliente tras crear la conexión con el servidor (ambos archivos han de ser creados manualmente)

    transport = TSocket.TSocket('localhost',9090)
    transport = TTransport.TBufferedTransport(transport)
    protocol = TBinaryProtocol.TBinaryProtocol(transport)
    #creamos el cliente
    client = Calculadora.Client(protocol)    

    resultado = client.suma(1, 1)
    print("1+1="+str(resultado))
            ...
    resultado = client.modulo(2, 4)
    print("2%4="+str(resultado))   

Los problemas que me encontré en esta parte fueron algunos errores con la versión de python 3.6 y que puede arreglar usando la versión 2.7.


## 2.Operaciones con vectores
Para realizar la implementación de operaciones con vectores declaré las funciones en calculadora.thrift como list<double>

    	list<double> sumarVectores(1:list<double> v1, 2:list<double> v2)

y la implementación en el servidor consiste en recorrer cada componente de los vectores y realizar la operación pertinente, el resultado de esa operación se guarda en un vector con append que devuelve la función al terminar.

        def sumarVectores(self,v1,v2):
        print('suma de vectores de tamano'+str(len(v1)))
        v3 = list()
        for i in range(0,len(v1)):
            v3.append(v1[i]+v2[i])
        return v3

El cliente llama a la función del servidor y almacena el vector resultante (cree una función para poder mostrar los vectores de una forma mas ordenada)

    def mostrarVector(resultado):
    salida = "Vector: [ "
    for i in range(0,len(resultado)):
        salida = salida + " " + str(resultado[i]) + " "
    salida = salida +  " ]"
    print(salida)

    v1 = [1,2,3,4,5]
    v2 = [6,7,8,9,10]

    print("\nsumando vectores: ")
    mostrarVector(v1)
    mostrarVector(v2)
    resultado = client.sumarVectores(v1,v2)
    print("Y el resultado es: ")
    mostrarVector(resultado)

En este punto de la práctica y sin tener ningún conocimiento de python, tuve que buscar la forma en que se implementaban los array, la forma de recorrerlos, usar bucles y almacenarlos. Uno de los problemas que tuve fué que no conseguía almacenar el resultado de la suma de dos vectore en el vector resultado, para ello tuve que declararlo como v3 = list() y usar append(valor). Cuando lo tenía todo terminado, al ejecutarlo me daba un error, fruto de que declaraba la función de mostrar el vector después, acostumbrado a lenguajes compilados.

![basica](./imagenes/basica.png "Calculadora básica y vectores")


         
explicar por algun lado que este mejora los fallos del anterior, no te obliga a C, no te pone punteros por defecto y tu tienes que implementar y crear servidor y cliente, 