/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;


import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

/**
 *
 * @author Becario
 */
public class Estado {
    static SortedMap <Integer, Estado> M= new TreeMap<>(); //Colección de todos los estados creados
    
    private Vector <Integer []> Transiciones;
    private int token; //token que devuelve un estado final
    public Estado(int id){        
        Transiciones = new Vector<Integer[]>();
        M.put(id, this);
        //System.out.println(M.containsKey(id));
        //System.out.println(id);
    }
    
    public void pushTransicion(int id,int simbolo){
        Integer [] aux={id,simbolo};
        Transiciones.add(aux);
        //Es eficiente así o sería mejor un arreglo estado - simbolos quete llevan ahí ?
    }
    
    public Vector getTransiciones(){
        return  Transiciones;
    }
    
    public void setToken(int t){
        token=t;
    }
    
    public int getToken(){
        return token;
    }
    
}
