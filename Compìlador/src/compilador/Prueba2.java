/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Prodigy
 */
public class Prueba2 {
    
    public Prueba2(){
        Prueba1 P;
        P=null;
        Metodo1(P);
        if(P!=null)
            System.out.println("hola");
        
    }
    
    
    public void Metodo1(Prueba1 P){
       P = new Prueba1("w",54); 
    
    }
    
    
    public int Metodo2(int p){
       int f;
       if(p>5){
            f=6;
            return f;
       }
       f=Metodo2(7);
       return 0;
    }
}
