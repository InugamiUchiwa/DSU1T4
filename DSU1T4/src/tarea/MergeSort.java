/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MergeSort {
    
    public ArrayList ordenaMerge(ArrayList L, String separador, int columna){
        int n = L.size(),i,m;
        ArrayList L1 = new ArrayList(),
                  L2 = new ArrayList();
        if(n>1){
            m=n/2;
            for(i=0; i<m; i++)
                L1.add(L.get(i));
            for(i=m; i<n; i++)
                L2.add(L.get(i));
                L = merge(ordenaMerge(L1, separador, columna), ordenaMerge(L2, separador, columna), separador, columna);
        }
        return L;
    }
    
    public ArrayList merge(ArrayList L1, ArrayList L2, String separador, int columna){
        ArrayList lista = new ArrayList();
        while(!L1.isEmpty() && !L2.isEmpty()){
            StringTokenizer columnaL1 = new StringTokenizer(L1.get(0).toString(), separador);
            StringTokenizer columnaL2 = new StringTokenizer(L2.get(0).toString(), separador);
            String col1=null, col2=null;
            for(int i=0; i<columna; i++){
                col1 = columnaL1.nextToken();
                col2 = columnaL2.nextToken();
            }
            if( col1.compareTo(col2) < 0){
                lista.add(L1.get(0));
                L1.remove(0);
                if(L1.isEmpty()){
                    lista.addAll(L2);
                    L2.clear();
                }
            }
            else{
                lista.add(L2.get(0));
                L2.remove(0);
                if(L2.isEmpty()){
                    lista.addAll(L1);
                    L1.clear();
                }
            }
        }
        return lista;
    }
}

