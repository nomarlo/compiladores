/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author nomarlo
 */
public class AFD {
    public int idAFD; //Id del AFD
    public int estadoInicial;
    public Integer [][] tabla;
    public Vector <Vector <Integer>> tablaOptimizada, MEstados;
    public SortedMap <Integer, Integer>  MSimbolos;//Mapeos de filas y columnas
    public SortedSet <Integer>  I;
    public Vector <SortedSet <Integer> > C;
    
    
    public AFD (int idAFD){
        MSimbolos = new TreeMap<>();
        tablaOptimizada= new Vector<>();
        MEstados= new Vector<>();
        this.idAFD=idAFD;
    }    
    public void imprimeAFD(){
        for(int i=0;i<tablaOptimizada.size();i++){
            System.out.print(i+":\t");
             for(int e=0;e<MSimbolos.size();e++){
                 System.out.print(tablaOptimizada.get(i).get(e)+"\t");
        
            }
            System.out.println();
        }
        
        for(int i=0;i<MEstados.size();i++){
            System.out.print(i+":\t");
             for(int e=0;e<2;e++){
                 System.out.print(MEstados.get(i).get(e)+"\t");
        
            }
            System.out.println();
        }
    }    
    public AFD(AFN A, int idAFN){
        int x=0;
        MSimbolos = new TreeMap<>();
        tablaOptimizada= new Vector<>();
        MEstados= new Vector<>();
        setMapeoSimbolos(A.Alfabeto);
        
        tabla= new Integer [500][MSimbolos.size()+5];
        
        C=new Vector<>();
        SortedSet <Integer> Ax= new TreeSet<>();
        this.idAFD=idAFN;
        Ax.add(A.estadoInicial);
        C.add(A.cerraduraEpsilon(Ax));
        
        while(x<C.size()){
            boolean est=false;
            for(Integer y: C.get(x)){
                for(Integer z: A.estadosFinales){
                    if((int)y==(int)z){
                        tabla[x][MSimbolos.size()]=Estado.M.get(z).getToken();
                        est=true;
                        break;
                    }
                }
                if (est)
                    break;

            }

            if(!est)
                tabla[x][MSimbolos.size()]=-1;
            
            for(Integer e: A.Alfabeto){
               
                if((int)e!=(int)AFN.epsilon)
                    I=A.IrA(C.elementAt(x), e);
                else 
                    continue;
                
                if(I.isEmpty() || I.size()==0){
                    tabla[x][MSimbolos.get(e)]=-1;
                    continue;
                }
                
                est=true;
                for(int s=0;s<C.size();s++){
                    if(I.equals(C.get(s))){
                        tabla[x][MSimbolos.get(e)]=s;
                        est=false;
                        break;
                    }
                }
                
                if(est){
                    est=false;
                    tabla[x][MSimbolos.get(e)]=C.size();
                    
                    
                    C.add(I);
                    
                }
                
            }
            x++;
            
        }
        //System.out.println(x);
        
        /**
        for(int i=0;i<A.Alfabeto.size();i++)
            System.out.print("\t"+A.Alfabeto.toArray()[i]);
        System.out.println();
        for(int i=0;i<x;i++){
            System.out.print(i+":\t");
            for(int e=0;e<=MSimbolos.size();e++){
                
                System.out.print(tabla[i][e]+"\t");
                
            
            }
            System.out.println();
        }
        /**
        //System.out.println(MSimbolos.size());
        for(int i=0;i<A.Alfabeto.size();i++)
            System.out.print("\t"+A.Alfabeto.toArray()[i]);
        
        **/
        setMapeoEstados(x);
    }
    
    
    
    private void setMapeoSimbolos(SortedSet <Integer> Alfabeto){
        int x=0;
        for(Integer i: Alfabeto){
            if((int)i!=(int)AFN.epsilon){
                MSimbolos.put(i, x);
                x++;
            }
        }        
    }
    
    private void setMapeoEstados(int n){
        boolean est;
        est=false;
        Vector <Integer> Ax, Ax2;
        for(int i=0;i<n;i++){
            Ax2= new Vector<>();
            
            for(int x=0;x<tablaOptimizada.size();x++){
                est=true;
                for(int e=0;e<MSimbolos.size();e++){
                    if((int)tabla[i][e]!=(int)tablaOptimizada.get(x).get(e)){
                        est=false;      
                        break;
                    }        
                    
                }              
                if(est){
                    Ax= new Vector<>();
                    Ax.add(x);//Mapeo a fila
                    Ax.add(tabla[i][MSimbolos.size()]);//token
                    MEstados.add(Ax);
                    break;
                }
            }
            
            for(int e=0;e<MSimbolos.size();e++){
                Ax2.add(tabla[i][e]);
                

            }
            
            
                
            if(!est){
                Ax= new Vector<>();
                Ax.add(tablaOptimizada.size());//Mapeo a fila
                Ax.add(tabla[i][MSimbolos.size()]);//token           
                MEstados.add(Ax);
                
                tablaOptimizada.add(Ax2);
            }
        }
       /**
        for(int i=0;i<tablaOptimizada.size();i++){
            System.out.print(i+":\t");
             for(int e=0;e<MSimbolos.size();e++){
                 System.out.print(tablaOptimizada.get(i).get(e)+"\t");
        
            }
            System.out.println();
        }
        
        for(int i=0;i<MEstados.size();i++){
            System.out.print(i+":\t");
             for(int e=0;e<2;e++){
                 System.out.print(MEstados.get(i).get(e)+"\t");
        
            }
            System.out.println();
        }
        **/
    }
}
