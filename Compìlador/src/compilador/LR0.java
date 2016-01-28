package compilador;


import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nomarlo
 */
public class LR0 {
    
    public Integer [][] tabla;
    public int nFilas;//numero de filas en la tabla
    public SortedMap <String, Integer>  Columnas;
    public SortedMap <Integer, Integer> Filas;//Mapa innecesario, s epuede acceder directamente a las filas de la tabla
    public SortedSet <Item>  It;
    public Vector <SortedSet <Item> > I;
    public Vector <Nodo> Reglas, ReglasOrigen;//Reglas en orden numeradas y reglas con el nodo cabecera de esa produccion
    public SortedSet <String> NoTerminales;
    public SortedSet <String> Terminales;
    
    public LR0(){
        nFilas=0;
        Filas= new TreeMap<>();
        Columnas= new TreeMap<>();
        It = new TreeSet <>();   
        I = new Vector <>();
        ReglasOrigen = new Vector <>();
        NoTerminales= new TreeSet<>();
        Terminales= new TreeSet<>();
        
    }
    
    public int tamano(int n){
        Nodo aux=Reglas.get(n);
        int r=0;
        while(aux!=null){
            aux=aux.siguiente;
            r++;
        }
        return r;
    }
    
    public void setConjuntos(SortedSet<String> NT,SortedSet<String>T){
        NoTerminales=NT;
        Terminales=T;
    } 
    
    public void setFilas(){
        /**
         * Mapa solo de adorno. Para seguir arquitectura de tabla LL1
         */
        for(int i=0;i<50;i++){
            Filas.put(i, i);
        }
    }
    
    public void setTabla(){
        tabla=new Integer[Filas.size()+1][Columnas.size()+1];
        
        for(int i=0;i<Filas.size()+1;i++){
            for(int e=0;e<Columnas.size();e++){
                tabla[i][e]=-1;
            }
        }
    }
    
    public void fillTabla(DescensoGramaticas DG, Nodo L){
        /**
         * Go to y shift se expresan con el numero de regla correspondiente tal cual
         * reduce se expresa con un ofsset de 100, es decir r1: 101
         */
        Vector <Item> Finales= new Vector <>(); // almacena los items que van a producir reducciones, ejemplo  E -> E + T.
        //empezamos de sde 1 porque la regla de la gramatica aumentada se considera abajo
        for(int i=1;i<Reglas.size();i++){
            int aux=0;
            
            Nodo Naux=Reglas.get(i);
            while(Naux!=null){
                Naux=Naux.siguiente;
                aux++;
            }
            Finales.add(new Item(i,aux));
        }
        
        
        //buscamos la regla inicial con el punto al final para poner estado de aceptaciòn
        // ejemplo: E' -> E.
        // 99 = acccepted
        Item Iaux=new Item(0,1);
        for(int i=0;i<I.size();i++){
            if(I.get(i).contains(Iaux))
                tabla[i][Columnas.get("$")]=99;            
        }
        /**
         * A partir de aqui llenar con reduce
         */
        for(int i=0;i<I.size();i++){
            for(int e=0;e<Finales.size();e++){
                if(I.get(i).contains(Finales.get(e))){
                    
                    SortedSet <String> F=DG.FollowLR0(ReglasOrigen.get(Finales.get(e).numRegla), L);
                    for(int x=0;x<F.size();x++){
                        tabla[i][Columnas.get(F.toArray()[x])]=Finales.get(e).numRegla+100;  
                    }
                    
                }
                    
            }
                      
        }
        
        
    }
    
    public void setColumnas(Nodo L){
        Terminales.remove("#");
        
        Object [] S=Terminales.toArray();
        int i;
        //Agregamos columnas de no terminales (operaciones shoft y reduce)
        for(i=0;i<S.length;i++){
            Columnas.put(S[i].toString(), i);
        }
        Columnas.put("$", i);
        //Columnas terminales (go to)
        NoTerminales.remove(L.simbolo);
        S=NoTerminales.toArray();
        int e=0;
        for(e=0,i=i+1;e<S.length;i++,e++){
            Columnas.put(S[e].toString(), i);
        }
    }
    public void doItems(Nodo L, int reglas){//analizamos la lista para obtener el conjunto de items
        int indice=0;
        Reglas=numeraReglas(L);
        for(int i=0;i<reglas;i++){
            Item aux= new Item(i,0);
            It.add(aux);            
        }
        I.add(It);
        nFilas++;
        
        while(indice<I.size()){
            //It.clear();
            Object array[]=  I.get(indice).toArray();//arreglo de items
            for(Object auxIt: array){
                Item x=(Item) auxIt;
                Nodo Naux=Reglas.get(x.numRegla);
                int pos=0;
                while(pos!=x.pos){
                    Naux=Naux.siguiente;
                    pos++;
                }
                SortedSet <Item>  ItAux=IrA(I.get(indice),Naux!=null?Naux.simbolo:"",L);
                if(!ItAux.isEmpty()&&!I.contains(ItAux)){
                    I.add(ItAux);
                    //Colocamos en la tabla un desplazamiento
                    tabla[indice][Columnas.get(Naux.simbolo)]=I.size()-1;                    
                    nFilas++;
                }
                else if(!ItAux.isEmpty()){
                    //Colocamos en la tabla un desplazamiento
                    int r=I.indexOf(ItAux);
                    tabla[indice][Columnas.get(Naux.simbolo)]=r;
                }
            
            }
                       
            indice++;
        } 
        
        
    }
    
    public SortedSet <Item> IrA(SortedSet <Item> IT, String simbolo,Nodo L){
        return cerraduraEpsilon(Mover(IT,simbolo,L),L);
    }
    
    public SortedSet <Item>Mover(SortedSet <Item> IT, String simbolo,Nodo L){
        SortedSet <Item> moverI = new TreeSet<>();
        
        Object array[]=  IT.toArray();//arreglo de items
        for(Object auxIt: array){
            int pos=0;
            Item auxIt2=(Item) auxIt;
            Item x= new Item(auxIt2.numRegla,auxIt2.pos);
            
            Nodo Naux=Reglas.get(x.numRegla);
            while(pos!=x.pos){
                Naux=Naux.siguiente;
                pos++;
            }
            
            if(Naux!=null && Naux.simbolo.equals(simbolo)){
                x.pos = x.pos+1;
                moverI.add(x);
            }
            
        }
        
        
        
        return moverI;
    }
    
    public SortedSet <Item>cerraduraEpsilon(SortedSet <Item> IT,Nodo L){
        SortedSet <Item> epsilonI = new TreeSet<>();
        SortedSet <Item> AuxepsilonI = IT;
        int numRegla,pos;
        Nodo Naux;
        Object array[]=  IT.toArray();//arreglo de items
        while(!AuxepsilonI.isEmpty()){
            Item auxIt2=(Item) AuxepsilonI.first();
            Item x= new Item(auxIt2.numRegla,auxIt2.pos);
            numRegla=x.numRegla;
            pos=0;
            
            
            epsilonI.add(x);
            
            Naux=Reglas.get(numRegla);
            while(pos!=x.pos){
                Naux=Naux.siguiente;
                pos++;
            }
            
            if(Naux!=null && NoTerminales.contains(Naux.simbolo)){
                Nodo Naux3=L;
                while(Naux3!=null){
                    if(Naux3.simbolo.equals(Naux.simbolo)){
                        Nodo Naux2=Naux3.siguiente;
                        while(Naux2!=null){
                            Item Iaux=new Item(Reglas.indexOf(Naux2),0);
                            if(!epsilonI.contains(Iaux)){
                                
                                AuxepsilonI.add(Iaux);
                            }
                            epsilonI.add(Iaux);
                            Naux2=Naux2.abajo;
                        }
                    }
                    Naux3=Naux3.abajo;
                }
                
            }            
           AuxepsilonI.remove(auxIt2);

        }  
        
        return epsilonI;
        
    }
    public Vector <Nodo> numeraReglas(Nodo L){//encuentra el numero de regla buscado
        Vector <Nodo> R= new Vector<>();
        
        while(L!=null){
            R.add(L.siguiente);
            ReglasOrigen.add(L);
            Nodo aux =L.siguiente;
            while(aux.abajo!=null){
                aux=aux.abajo;
                R.add(aux);
                ReglasOrigen.add(L);
            }
            L=L.abajo;
            
        }
        return R;
    }    
    
}
