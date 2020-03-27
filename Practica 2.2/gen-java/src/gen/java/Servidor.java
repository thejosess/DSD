package gen.java;

import gen.java.Calculadora.Processor;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;

//import tutorial.* asi se importarían modules etc

class Servidor{
    public static void main(String args[]){
        CalculadoraHandler handler = new CalculadoraHandler();
        Processor processor = new Processor(handler);
        try{
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            
            System.out.println("Iniciando servidor...");
            server.serve();
        }catch (Exception e){e.printStackTrace();}
    }
}