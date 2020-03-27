/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gen.java;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author juse
 */
public class CalculadoraHandler implements Calculadora.Iface{


    @Override
    public int suma(int num1, int num2) {
        System.out.println("Sumando numeros");
        return num1 + num2;
    }

    @Override
    public void ping() {
        System.out.println("Me han hecho ping");
    }

    @Override
    public int resta(int num1, int num2)  {
        System.out.println("Restando numeros");
        return num1 - num2;
    }

    @Override
    public int multiplicacion(int num1, int num2) throws TException {
        System.out.println("multiplicacion numeros");
        return num1 * num2;
    }

    @Override
    public int division(int num1, int num2) throws TException {
        System.out.println("division numeros");
        return num1 / num2;
    }

    @Override
    public int modulo(int num1, int num2) throws TException {
        System.out.println("modulo numeros");
        return num1 % num2;
    }

    @Override
    public List<Double> sumarVectores(List<Double> v1, List<Double> v2) throws TException {
        List<Double> rs = v1;
        for(int i = 0; i < v1.size() ; i++){
            rs.set(i, v1.get(i) + v2.get(i));
        }
        return rs;
    }

    @Override
    public List<Double> restarVectores(List<Double> v1, List<Double> v2) throws TException {
        List<Double> rs = v1;
        for(int i = 0; i < v1.size() ; i++){
            rs.set(i, v1.get(i) - v2.get(i));
        }
        return rs;    
    }

    @Override
    public List<Double> multiplicarVectores(List<Double> v1, List<Double> v2) throws TException {
        List<Double> rs = v1;
        for(int i = 0; i < v1.size() ; i++){
            rs.set(i, v1.get(i) * v2.get(i));
        }
        return rs;
    }

    @Override
    public List<Double> dividirVectores(List<Double> v1, List<Double> v2) throws TException {
        List<Double> rs = v1;
        for(int i = 0; i < v1.size() ; i++){
            rs.set(i, v1.get(i) / v2.get(i));
        }
        return rs;
    }

    @Override
    public funcion sumaFunciones(funcion f1, funcion f2) throws TException {
        System.out.println("Sumando funciones");
        funcion result = new funcion();
        result.x = f1.x + f2.x;
        result.y = f1.y + f2.y;
        result.z = f1.z + f2.z;

        result.exponente_x = f1.exponente_x;
        result.exponente_y = f1.exponente_y;
        result.exponente_z = f1.exponente_z;
        return result;
    }

    @Override
    public funcion restaFunciones(funcion f1, funcion f2) throws TException {
        System.out.println("Restando funciones");
        funcion result = new funcion();
        result.x = f1.x - f2.x;
        result.y = f1.y - f2.y;
        result.z = f1.z - f2.z;

        result.exponente_x = f1.exponente_x;
        result.exponente_y = f1.exponente_y;
        result.exponente_z = f1.exponente_z;
        return result;
    }

    @Override
    public funcion multiplicacionFunciones(funcion f1, funcion f2) throws TException {
        System.out.println("Multiplicando funciones");
        funcion result = new funcion();
        result.x = f1.x * f2.x;
        result.y = f1.y * f2.y;
        result.z = f1.z * f2.z;

        result.exponente_x = f1.exponente_x;
        result.exponente_y = f1.exponente_y;
        result.exponente_z = f1.exponente_z;
        return result;
    }

    @Override
    public funcion divisionFunciones(funcion f1, funcion f2) throws TException {
        System.out.println("Dividiendo funciones");
        funcion result = new funcion();
        result.x = f1.x / f2.x;
        result.y = f1.y / f2.y;
        result.z = f1.z / f2.z;

        result.exponente_x = f1.exponente_x;
        result.exponente_y = f1.exponente_y;
        result.exponente_z = f1.exponente_z;
        return result;
    }

    @Override
    public funcion derivadaFunciones(funcion f1) throws TException {
        System.out.println("Derivando funcion");
        funcion result = new funcion();
        result.z = f1.y;
        result.y = f1.x * 2;
        result.x = 0;

        result.exponente_x = f1.exponente_x;
        result.exponente_y = f1.exponente_y;
        result.exponente_z = f1.exponente_z;
        return result;
    }

    
}