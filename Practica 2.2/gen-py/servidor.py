import glob
import sys

from calculadora import Calculadora
#from calculadora.ttypes import Trabajo
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