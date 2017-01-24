/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LectorValidador {
    private ArrayList lista = new ArrayList();
    private final MergeSort merge =  new MergeSort();
    private boolean band = true;
    private int columnas =0;
    private String separador, archivo;
    private ArrayList linea = new ArrayList();

    public ArrayList leerTexto(String[] parametros){
        this.separador = parametros[1];
        this.archivo = parametros[0];
        try {
            Scanner archivoLectura = new Scanner(new FileReader("C:\\Users\\melee_000\\Documents\\"+archivo));
            if(!archivoLectura.hasNextLine()){
                System.out.println("\nError 000: linea 1");
                band = false;
            }
            if(band == true){
                while(archivoLectura.hasNextLine()){
                    lista.add(archivoLectura.nextLine());
                }
            }
            archivoLectura.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LectorValidador.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(band == true){
            return lista;
        }
        return null;
    }
    
    public boolean validarTexto(ArrayList lista){
        
        String cad = lista.get(0).toString();
        columnas = Integer.parseInt(cad);
            char c = cad.charAt(0);
            if(!Character.isDigit(c)){
                System.out.println("\nError 001: linea 1");
                return false;
            }
        linea.add(columnas);
        if(columnas <1){
            System.out.println("\nError 001: linea 1");
            return false;
        }
        for(int i=1; i<columnas+1; i++){
                StringTokenizer token = new StringTokenizer(lista.get(i).toString(), separador);
                int cont = 0;
                while(token.hasMoreTokens()){
                    token.nextToken();
                    cont++;
                }
                if(cont!=1){
                    System.out.println("\nError 001: linea " + (i+1));
                    return false;
                }
                linea.add(lista.get(i));
            }
        if(lista.size()<columnas+1 ){
            System.out.println("\nError 000: linea "+ (columnas+1));
            return false;
        }
        for(int i =columnas+1; i<lista.size(); i++){
            if((lista.get(i).toString()).equals("")){
                System.out.println("\nError 000: linea "+(i+1));
                return false;
            }
            StringTokenizer token = new StringTokenizer(lista.get(i).toString(), separador);
            int cont = 0;
            while(token.hasMoreTokens()){
                token.nextToken();
                cont ++;
            }
            if(cont != columnas){
                System.out.println("\nError 010: linea "+(i));
                return false;
            }
            
        }
        return true;
    }
    
    public void sobreescribirArchivo(ArrayList lista){
        try {
            PrintWriter archivoSalida= new PrintWriter("C:\\Users\\melee_000\\Documents\\"+archivo);
            for(int i=0; i<columnas+1; i++){
                archivoSalida.println(linea.get(i));
            }
            for(int i=0; i<lista.size()-1; i++){
                    archivoSalida.println(lista.get(i));
            }
            archivoSalida.print(lista.get(lista.size()-1));
            archivoSalida.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LectorValidador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void imprimirArchivo(ArrayList lista){
        System.out.println("\nArchivo Ordenado Exitosamente!!\n");
        for(int i=0; i<lista.size(); i++){
            System.out.println(lista.get(i).toString());
        }
    }
   
}

