/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.TreeMap;

/**
 *
 * @author nomarlo
 */
public class Item implements Comparable<Item> {
    int numRegla,pos;
    
    public Item(){
        numRegla=0;
        pos=0;
       
    }
    
    public Item(int nR, int p){
        numRegla=nR;
        pos=p;
       
    }

    @Override
    public int compareTo(Item o) {
        if(numRegla<o.numRegla ||(numRegla == o.numRegla && pos<o.pos))
            return -1;
        return numRegla==o.numRegla && pos==o.pos ? 0:1;
    }
    
}
