import glob
import sys

from calculadora import Calculadora
from calculadora.ttypes import funcion
#los enum que quiera usar

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import logging
logging.basicConfig(level=logging.DEBUG)
#para poder depurar

class CalculadoraHandler:
    def __init__(self):
        self.log = {}

    def ping(self):
        print('Me han hecho un ping()')

    def suma(self,n1,n2):
        print('sumando'+str(n1)+" con "+str(n2))
        return n1+n2
    def resta(self,n1,n2):
        print('restando '+str(n1)+ " con "+str(n2))
        return n1 - n2
    def multiplicacion(self,n1,n2):
        print('multiplicando '+str(n1)+ " con "+str(n2))
        return n1 * n2
    def division(self,n1,n2):
        print('division '+str(n1)+ " con "+str(n2))
        return n1 / n2
    def modulo(self,n1,n2):
        print('division '+str(n1)+ " con "+str(n2))
        return n1 % n2
    def sumarVectores(self,v1,v2):
        print('suma de vectores de tamano'+str(len(v1)))
        v3 = list()
        for i in range(0,len(v1)):
            v3.append(v1[i]+v2[i])
        return v3
    def sumarVectores(self,v1,v2):
        print('suma de vectores de tamano'+str(len(v1)))
        v3 = list()
        for i in range(0,len(v1)):
            v3.append(v1[i]+v2[i])
        return v3

    def restarVectores(self,v1,v2):
        print('suma de vectores de tamano'+str(len(v1)))
        v3 = list()
        for i in range(0,len(v1)):
            v3.append(v1[i]-v2[i])
        return v3

    def multiplicarVectores(self,v1,v2):
        print('suma de vectores de tamano'+str(len(v1)))
        v3 = list()
        for i in range(0,len(v1)):
            v3.append(v1[i]*v2[i])
        return v3

    def dividirVectores(self,v1,v2):
        print('suma de vectores de tamano'+str(len(v1)))
        v3 = list()
        for i in range(0,len(v1)):
            v3.append(v1[i]/v2[i])
        return v3

    def sumaFunciones(self,f1,f2):
        print("sumando funciones")
        result = funcion(0,0,0,0,0,0)
        result.x = f1.x + f2.x
        result.y = f1.y + f2.y
        result.z = f1.z + f2.z

        result.exponente_x = f1.exponente_x
        result.exponente_y = f1.exponente_y
        result.exponente_z = f1.exponente_z
        return result

    def restaFunciones(self,f1,f2):
        print("restando funciones")
        result = funcion(0,0,0,0,0,0)
        result.x = f1.x - f2.x
        result.y = f1.y - f2.y
        result.z = f1.z - f2.z

        result.exponente_x = f1.exponente_x
        result.exponente_y = f1.exponente_y
        result.exponente_z = f1.exponente_z
        return result

    def multiplicacionFunciones(self,f1,f2):
        print("multiplicando funciones")
        result = funcion(0,0,0,0,0,0)
        result.x = f1.x * f2.x
        result.y = f1.y * f2.y
        result.z = f1.z * f2.z

        result.exponente_x = f1.exponente_x
        result.exponente_y = f1.exponente_y
        result.exponente_z = f1.exponente_z
        return result

    def divisionFunciones(self,f1,f2):
        print("dividiendo funciones")
        result = funcion(0,0,0,0,0,0)
        result.x = f1.x / f2.x
        result.y = f1.y / f2.y
        result.z = f1.z / f2.z

        result.exponente_x = f1.exponente_x
        result.exponente_y = f1.exponente_y
        result.exponente_z = f1.exponente_z
        return result

    def derivadaFunciones(self,f1):
        print("derivando funcion")
        result = funcion(0,0,0,0,0,0)
        result.z = f1.y
        result.y = f1.x * 2
        result.x = 0

        result.exponente_x = f1.exponente_x
        result.exponente_y = f1.exponente_y
        result.exponente_z = f1.exponente_z
        return result





if __name__ == '__main__':
    handler = CalculadoraHandler()
    processor = Calculadora.Processor(handler)
    transport = TSocket.TServerSocket(host='127.0.0.1', port=9090)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor,transport, tfactory,pfactory)


    print('Iniciando servidor...')
    server.serve()
    print('done.')