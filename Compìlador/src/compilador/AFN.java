
package compilador;


import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author Becario
 */
public class AFN {
    static int id=0;//id del estado
    static final int epsilon=228;//En realidad es sigma
    public SortedSet <Integer> Estados;
    public int idAFN; //Id del AFN (me parece redundante por la implementación)
    public int estadoInicial;
    public Vector <Integer> estadosFinales;
    public SortedSet <Integer> Alfabeto;
    
    public AFN (){
        Estados = new TreeSet <>();     
        estadosFinales = new Vector <>();
        Alfabeto = new TreeSet<>();
        
    }    
    public AFN (int idAFN){
        Estados = new TreeSet <>();    
        estadosFinales = new Vector <>();
        Alfabeto = new TreeSet<>();
        this.idAFN=idAFN;
    }
    
    public int creaAFN( int simbolo){ //Método para crear automata simple (1 simbolo)
        Estado E= new Estado(id);  
        E.pushTransicion(id+1, simbolo);
        Alfabeto.add(simbolo);
        
        Estados.add(id);
        
        estadoInicial=id;
        
        id++;
        E = new Estado(id);
        
        Estados.add(id);
        estadosFinales.add(id);
        id++;
                
        
        
        
        
        return idAFN;
    }
    
    public int concatenacionAFN(AFN A){ //El AFN que queremos combinar con el actual
        Vector <Integer[]> T=Estado.M.get(A.estadoInicial).getTransiciones(); //las trnasiciones del estado inicial
       // Estado.M.remove(A.estadoInicial); ¿Si se combinan dos automatas uno deja de existir?
        for (Integer e : estadosFinales){ //recorremos todos los estados finales para unirlos con el inicial A
                for(int i=0;i< T.size();i++){
                    Estado.M.get(e).pushTransicion(T.get(i)[0], T.get(i)[1]);
                }
         }
        estadosFinales.clear();
        for(int i=0;i< A.estadosFinales.size();i++){
            estadosFinales.add(A.estadosFinales.get(i));
        }
       // Estado.M.remove(A.estadoInicial); Se elimina o se quedA?
        this.Estados.addAll(A.Estados);//Hacemos la union de los conjuntos
        Alfabeto.addAll(A.Alfabeto);//Hacemos la union de los dos alfabetos
        Estados.remove(A.estadoInicial);
        return this.idAFN;
    }
    
    public int unionAFN(AFN A){
        Estado E= new Estado(id);  
        E.pushTransicion(A.estadoInicial, epsilon);
        E.pushTransicion(estadoInicial, epsilon);
        Alfabeto.add(epsilon);
        Estados.add(id);
        estadoInicial=id;
        id++;
        
        E= new Estado(id);
        Estados.add(id);
        
       // Estado.M.remove(A.estadoInicial); ¿Si se combinan dos automatas uno deja de existir?
        for (Integer e : A.estadosFinales){ //recorremos todos los estados finales para unirlos con epsilon a un nuevo estado
            Estado.M.get(e).pushTransicion(id, epsilon);
        }
        
        for (Integer e : estadosFinales){ //recorremos todos los estados finales para unirlos con epsilon a un nuevo estado
            Estado.M.get(e).pushTransicion(id, epsilon);
        }
        
        estadosFinales.clear();
        estadosFinales.add(id);
        Alfabeto.addAll(A.Alfabeto);//Hacemos la union de los dos alfabetos
       // System.out.println("ID estado final:"+id);
        
        
        id++;
        
        this.Estados.addAll(A.Estados);//Hacemos la union de los conjuntos
        
        
        
        return this.idAFN;
    }
    
    public int unionEspecialAFN(AFN A){
        Estado E= new Estado(id);  
        E.pushTransicion(A.estadoInicial, epsilon);
        E.pushTransicion(estadoInicial, epsilon);
        Alfabeto.add(epsilon);
        Estados.add(id);
        estadoInicial=id;
        id++;
        
        //Unimos los dos conjuntos de estados finales
        estadosFinales.addAll(A.estadosFinales);
         
        
        
        Alfabeto.addAll(A.Alfabeto);//Hacemos la union de los dos alfabetos
       // System.out.println("ID estado final:"+id);
        
        
       
        
        this.Estados.addAll(A.Estados);//Hacemos la union de los conjuntos
        
        
        
        return this.idAFN;
    }
    
    public int cerraduraPositiva(){
        Estado E = new Estado(id);
        E.pushTransicion(estadoInicial, epsilon);
        Alfabeto.add(epsilon);
      //  E.pushTransicion(id+1, epsilon);//Transicion epsilon entre el nuevo inicial y el nuevo final
        
        for(Integer e: estadosFinales){
            Estado.M.get(e).pushTransicion(estadoInicial , epsilon);
        }
        
        estadoInicial=id;
        
        Estados.add(id);
        id++;
        
        E= new Estado(id);
        
        for(Integer e: estadosFinales){
            Estado.M.get(e).pushTransicion(id, epsilon);
        }
        
        estadosFinales.clear();
        
        estadosFinales.add(id);
        
        Estados.add(id);
        id++;
        
        return this.idAFN;
    }
    
    public int cerraduraKleene(){
        Estado E = new Estado(id);
        E.pushTransicion(estadoInicial, epsilon);
        E.pushTransicion(id+1, epsilon);//Transicion epsilon entre el nuevo inicial y el nuevo final
        Alfabeto.add(epsilon);
        for(Integer e: estadosFinales){
            Estado.M.get(e).pushTransicion(estadoInicial , epsilon);
        }
        
        estadoInicial=id;
        
        Estados.add(id);
        id++;
        
        E= new Estado(id);
        
        for(Integer e: estadosFinales){
            Estado.M.get(e).pushTransicion(id, epsilon);
        }
        
        estadosFinales.clear();
        
        estadosFinales.add(id);
        
        Estados.add(id);
        id++;
        
        return this.idAFN;
    }
   
    
    public int opcional(){
        Estado E = new Estado(id);
        E.pushTransicion(estadoInicial, epsilon);
        E.pushTransicion(id+1, epsilon);//Transicion epsilon entre el nuevo inicial y el nuevo final
        Alfabeto.add(epsilon);
        
        
        estadoInicial=id;
        
        Estados.add(id);
        id++;
        
        E= new Estado(id);
        
        for(Integer e: estadosFinales){
            Estado.M.get(e).pushTransicion(id, epsilon);
        }
        
        estadosFinales.clear();
        
        estadosFinales.add(id);
        
        Estados.add(id);
        id++;
        
        return this.idAFN;
    }
    
    public SortedSet <Integer> cerraduraEpsilon(SortedSet <Integer> edos){
        Stack <Integer> C = new Stack <>();
        SortedSet <Integer> D= new TreeSet <>();
        int e;
        for(Integer i:edos){
           // System.out.println(i);
            C.push(i);
        }        
        while(!C.empty()){
            e=C.pop();
           // System.out.println( e);
            if(!D.contains(e)){
                D.add(e);
                
                Vector <Integer[]> T= Estado.M.get(e).getTransiciones();
                
                for(int i=0;i< T.size();i++){
                   // System.out.println("Estado actual:"+e+" id transicion"+T.get(i)[0]+" simbolo"+T.get(i)[1]);
                    if(T.get(i)[1] == epsilon ){ //Si tiene transicio epsilon y esta contenido ese estado en el automata
                        //System.out.println("Estado actual:"+e+" id transicion"+T.get(i)[0]+" simbolo"+T.get(i)[1]);
                        C.push(T.get(i)[0]);
                    }
                }
                
                
            }
            
        }        
        //D.add(5);
        
        return D;
    }
    
    public SortedSet <Integer> Mover(SortedSet <Integer> C, int x){
        SortedSet <Integer> D = new TreeSet <>();
        
        for(Integer e: C){
            //System.out.println(e);
            Vector <Integer[]> Aux= Estado.M.get(e).getTransiciones();
            //System.out.println("valor e:" +e);
            //System.out.println("simbolo: "+x);
            for(int i=0;i<Aux.size();i++){
                //System.out.println("Simbolo transiciones:"+Aux.get(i)[1]+" estado:"+Aux.get(i)[0]);
                if(Aux.get(i)[1]==x && !D.contains(Aux.get(i)[0]))
                    D.add(Aux.get(i)[0]);
            }
            
            
            
        }        
        
        return D; 
    }
    
    public SortedSet <Integer> IrA( SortedSet <Integer> C, int x){
          
        return cerraduraEpsilon(Mover(C,x));
    }

}
