/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gen.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author juse
 */
public class Cliente {
   
    public static void inicializarVectores(ArrayList<Double> v1, ArrayList<Double> v2) {
        for(int i = 0; i <  5; i++){
            v1.add(i+0.5);
            v2.add(i+1.5);
        }
    }
    
    public static void mostrarFuncion(funcion f1){
        String salida = "Funcion: " + f1.x + "x^" + f1.exponente_x + " + " + f1.y + "x^" + f1.exponente_y + " + " + f1.z + "x^" + f1.exponente_z ; 
        System.out.println(salida);
        
    }
    
        public static void main(String args[]){
    try {
      TTransport transport;
     
      transport = new TSocket("localhost", 9090);
      transport.open();

      TProtocol protocol = new  TBinaryProtocol(transport);
        Calculadora.Client client = new Calculadora.Client(protocol);
        client.ping();
        int resultado =client.suma(1, 1);
        System.out.println("1+1="+resultado);
        resultado = client.resta(1, 1);
        System.out.println("1-1="+resultado);
        resultado = client.division(4, 2);
        System.out.println("4/2="+resultado);
        resultado = client.multiplicacion(1, 2);
        System.out.println("1x2="+resultado);
        resultado = client.modulo(2, 3);
        System.out.println("2%4="+resultado);
        
        ArrayList<Double> v1 = new ArrayList<>();
        ArrayList<Double> v2 = new ArrayList<>();
        inicializarVectores(v1,v2);
        
        System.out.println("Sumando vectores: ");

        System.out.println(v1.toString());
        System.out.println(v2.toString());

        List<Double> rs;
        rs = client.sumarVectores(v1, v2);
        System.out.println("Vector resultado: ");
        System.out.println(rs.toString());
        
        System.out.println("\nRestando vectores: ");
        rs = client.restarVectores(v1, v2);
        System.out.println(rs.toString());
        
        System.out.println("\nMultiplicando vectores: ");
        rs = client.multiplicarVectores(v1, v2);
        System.out.println(rs.toString());
        
        System.out.println("\nDiviendo vectores: ");
        rs = client.dividirVectores(v1, v2);
        System.out.println(rs.toString());

        funcion f1 = new funcion(2,2,2,1,2,0);
        funcion f2 = new funcion(3,2,3,1,3,0);
        funcion f3 = new funcion(0,0,0,0,0,0);
        mostrarFuncion(f1);
        mostrarFuncion(f2);
        
        System.out.println("\nSumando funciones: ");
        f3 = client.sumaFunciones(f1, f2);
        mostrarFuncion(f3);
        
        System.out.println("\nRestando funciones: ");
        f3 = client.restaFunciones(f1, f2);
        mostrarFuncion(f3);
        
        System.out.println("\nMultiplicando funciones: ");
        f3 = client.multiplicacionFunciones(f1, f2);
        mostrarFuncion(f3);
        
        System.out.println("\nDividiendo funciones: ");
        f3 = client.divisionFunciones(f1, f2);
        mostrarFuncion(f3);
        
        System.out.println("\nDerivando primera funcion: ");
        f3 = client.derivadaFunciones(f1);
        mostrarFuncion(f3);
        
                
      transport.close();
    } catch (Exception e){e.printStackTrace();}
    }
}
