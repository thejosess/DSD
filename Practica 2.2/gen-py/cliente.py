from calculadora import Calculadora

from calculadora.ttypes import funcion

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

transport = TSocket.TSocket('localhost',9090)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)
#creamos el cliente
client = Calculadora.Client(protocol)

def mostrarVector(resultado):
    salida = "Vector: [ "
    for i in range(0,len(resultado)):
        salida = salida + " " + str(resultado[i]) + " "
    salida = salida +  " ]"
    print(salida)


def mostrarFuncion(f1):
    salida = "Funcion: " + str(f1.x) + "x^" + str(f1.exponente_x) + " + " + str(f1.y) + "x^" + str(f1.exponente_y) + " + " + str(f1.z) + "x^" + str(f1.exponente_z)  
    print(salida)


transport.open()
print("Hacemos ping al server")
client.ping()

resultado = client.suma(1, 1)
print("1+1="+str(resultado))
resultado = client.resta(1, 1)
print("1-1="+str(resultado))
resultado = client.multiplicacion(1, 1)
print("1x1="+str(resultado))
resultado = client.division(1, 1)
print("1/1="+str(resultado))
resultado = client.modulo(2, 4)
print("2%4="+str(resultado))
v1 = [1,2,3,4,5]
v2 = [6,7,8,9,10]

print("\nsumando vectores: ")
mostrarVector(v1)
mostrarVector(v2)
resultado = client.sumarVectores(v1,v2)
print("Y el resultado es: ")
mostrarVector(resultado)

print("\nahora restandolos ")
resultado = client.restarVectores(v1,v2)
print("Y el resultado es: ")
mostrarVector(resultado)

print("\nahora multiplicandolos ")
resultado = client.multiplicarVectores(v1,v2)
print("Y el resultado es: ")
mostrarVector(resultado)

print("\nahora dividiendolos ")
resultado = client.dividirVectores(v1,v2)
print("Y el resultado es: ")
mostrarVector(resultado)

""" han de ser de grado 2 """
f1 = funcion(2,2,2,1,2,0)
f2 = funcion(3,2,3,1,3,0)
mostrarFuncion(f1)
mostrarFuncion(f2)

print("\nsumando funciones, funcion resultante: ")
f3 = client.sumaFunciones(f1,f2)
mostrarFuncion(f3)

print("\nresntando funciones, funcion resultante: ")
f3 = client.restaFunciones(f1,f2)
mostrarFuncion(f3)

print("\nmultiplicando funciones, funcion resultante: ")
f3 = client.multiplicacionFunciones(f1,f2)
mostrarFuncion(f3)

print("\nderivando primera funcion, funcion resultante: ")
f3 = client.derivadaFunciones(f1)
mostrarFuncion(f3)




transport.close()
