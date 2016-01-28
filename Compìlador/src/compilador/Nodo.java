/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Prodigy
 */
public class Nodo {
    public String simbolo;
    public Nodo siguiente;
    public Nodo abajo;
    public boolean NoTerminal; 
    public int indice;
    
    
    public Nodo(){
        this.siguiente=null;
        this.abajo=null;
        this.simbolo=null;
        this.NoTerminal=true;
        indice=-1;
        
    }
    
    public void setNodo(String simbolo,boolean NoTerminal){
        this.siguiente=null;
        this.abajo=null;
        this.NoTerminal=NoTerminal;
        this.simbolo=simbolo;
        
    }
    
    public SortedSet  setTerminal(SortedSet <String> NoTerminales){
        SortedSet <String> S= new TreeSet<>();
        if(!NoTerminales.contains(this.simbolo)){
            S.add(this.simbolo);
            this.NoTerminal=false;
        }
        if(siguiente!=null)
            S.addAll(siguiente.setTerminal(NoTerminales));
        if(abajo!=null)
            S.addAll(abajo.setTerminal(NoTerminales));
        
        return S;
        
    }
    
   
    
}
