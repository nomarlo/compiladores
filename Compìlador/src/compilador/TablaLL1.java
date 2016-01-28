/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 *
 * @author Prodigy
 */
public class TablaLL1 {
    
    public Integer [][] tabla;
    public SortedMap <String, Integer>  Columnas,Filas;
    
    public TablaLL1(){
        Filas= new TreeMap<>();
        Columnas= new TreeMap<>();
       
    }
    
    public void setColumnas(SortedSet Terminales){
        Terminales.remove("#");
        Object [] S=Terminales.toArray();
        int i;
        for(i=0;i<S.length;i++){
            Columnas.put(S[i].toString(), i);
        }
        Columnas.put("$", i);
    }
    
    public void setFilas (Nodo inicial){
        while(inicial!=null){
            Filas.put(inicial.simbolo, inicial.indice);
            inicial=inicial.abajo;
        }
    }
    
    public void setTabla (DescensoGramaticas DG, Nodo inicial){
        tabla=new Integer[Filas.size()+1][Columnas.size()+1];
        for(int i=0;i<tabla.length;i++){
            for(int e=0;e<tabla[i].length;e++){
                tabla[i][e]=-1;
            }
        }
        
        Nodo auxAb=inicial;
        while(auxAb!=null){
            Nodo auxSig=auxAb.siguiente;
            while(auxSig!=null){
                SortedSet <String> S;
                Nodo aux = inicial;
                if(auxSig.NoTerminal){
                    while(!aux.simbolo.equals(auxSig.simbolo))
                        aux=aux.abajo;
                }
                else{
                    aux=auxSig;
                }
                S= DG.First(aux);
                Object [] O=S.toArray();
                for(int i=0;i<O.length;i++){
                    if(!O[i].toString().equals("#")){
                        tabla[Filas.get(auxAb.simbolo)][Columnas.get(O[i].toString())]=auxSig.indice;
                    }
                }
                
                if(S.contains("#")){
                    S=DG.Follow(auxAb, inicial);
                    O=S.toArray();
                    for(int i=0;i<O.length;i++){
                        tabla[Filas.get(auxAb.simbolo)][Columnas.get(O[i].toString())]=auxSig.indice;                        
                    }
                    
                }
                auxSig=auxSig.abajo;
            }
            auxAb=auxAb.abajo;
        }   
        
        
    }
    
}
