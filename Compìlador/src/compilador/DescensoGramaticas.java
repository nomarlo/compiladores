/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author Prodigy
 */
public class DescensoGramaticas {
    
    public ScannerG Lex;
   
    public Integer idAFN=0;
    public int reglas=0;
    public SortedSet <String> NoTerminales;
    public SortedSet <String> Terminales;
    public static int iI,iD;//indice para lado izquierdo y derecho
    
    public DescensoGramaticas(String s){
        iI=iD=0;
        
        Lex = new ScannerG(s);
        NoTerminales = new TreeSet<>();
        Terminales = new TreeSet<>();
    }
    
    
    public boolean AnalizarExpr(Nodo l){
        boolean R;
        //AFN A1=new AFN();
        R = G(l);
        if (R){
            if (Lex.GetToken() == Token.FIN){
                
                return R;
            }
            
            else{
                R=false;
            }
        }
        return R;
    }
    
    public boolean G(Nodo l){
        
        if(ListaReglas(l)){
            return true;
        }
        return false;
        
    }
    
    public boolean ListaReglas(Nodo l){
        Nodo aux= new Nodo();
        if(Regla(l)){
            if(ListaReglasp(aux)){
                if(aux.simbolo!=null){
                    l.abajo=aux;
                    reglas++;
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean ListaReglasp(Nodo l){
        Nodo aux= new Nodo();
        Integer []e=new Integer[2];
        e=Lex.GetEdoScanner();
        if(Regla(l)){
            
            if(ListaReglasp(aux)){
                if(aux.simbolo!=null){
                    l.abajo=aux;
                    reglas++;
                }
                return true;
            }
            return false;
        }
        Lex.RestauraEdoScanner(e);
        return true;
    }    
    public boolean Regla(Nodo l){
        int t;
        Nodo Aux= new Nodo();
        t=Lex.GetToken();
        if(t==Token.SIMB){
            NoTerminales.add(Lex.GetLexema());
            l.setNodo(Lex.GetLexema(),true);
            t=Lex.GetToken();
            l.indice=iI;
            iI++;
            if(t==Token.FLECHA){
               // Aux=null;
                if(ListaLd(Aux)){
                    
                    l.siguiente=Aux;
                    t=Lex.GetToken();
                    if(t==Token.PYC){
                        reglas++;
                        return true;
                    }
                }
            }
        }
        return false;
                
    }
    /**
    public boolean Li(){
        int t;
        t=Lex.GetToken();
        if(t==Token.SIMB)
            return true;
        return false;
    }**/
    
    public boolean ListaLd(Nodo l){
        Nodo Aux = new Nodo() ;
        if(Ld(l)){
           // Aux=null;
            l.indice=iD;
            iD++;
            if(ListaLdp(Aux)){
                if(Aux.simbolo!=null)
                    l.abajo=Aux;
                return true;
            }
        }
        return false;
    }
    
    public boolean ListaLdp(Nodo l){
        int t;
        Nodo Aux= new Nodo();
        Integer []e=new Integer[2];
        e=Lex.GetEdoScanner();
        t=Lex.GetToken();
        if(t==Token.OR){
            if(Ld(l)){
                l.indice=iD;
                iD++;
                //Aux=null;
                if(ListaLdp(Aux)){
                    if(Aux.simbolo!=null){
                        l.abajo=Aux;
                        reglas++;
                    }
                    return true;
                    
                }
            }
            return false;
        }
        
        Lex.RestauraEdoScanner(e);
        return true;
    }
    
    public boolean Ld(Nodo l){
        Nodo Aux= new Nodo();

        if(ListaSimbolos(l)){
            //Aux=null;            
            return true;
        }
        return false;
    }
    
    public boolean ListaSimbolos(Nodo l){
        int t;
        Nodo Aux= new Nodo();
        t=Lex.GetToken();
        if(t==Token.SIMB){
            l.setNodo(Lex.GetLexema(),true);
            //Aux=null;
            if(ListaSimbolosp(Aux)){
                if(Aux.simbolo!=null)
                    l.siguiente=Aux;
                return true;
            }
        }
        return false;
    }
    
    public boolean ListaSimbolosp(Nodo l){
        int t;
        Nodo Aux= new Nodo();
        Integer []e=new Integer[2];
        e=Lex.GetEdoScanner();        
        t=Lex.GetToken();
        if(t==Token.SIMB){
            //Aux=null;
            l.setNodo(Lex.GetLexema(),true);
            if(ListaSimbolosp(Aux)){
                if(Aux.simbolo!=null)
                    l.siguiente=Aux;
            
                return true;
            }
            
            return false;
        }
        
        Lex.RestauraEdoScanner(e);
        return true;
        
    }
    
    public SortedSet <String> First(Nodo A){
        SortedSet<String> X=new TreeSet<>();
        
        if(A.NoTerminal == false){
            X.add(A.simbolo);
             return X;
        }
        else{
            Nodo aux=A.siguiente;
            Nodo aux2 = A.abajo;
            while(aux!= null){
               Nodo aux3=aux2;
               Nodo aux4=aux; 

                if(aux4!=null &&aux.NoTerminal){
                    while(aux3!=null && !aux4.simbolo.equals(aux3.simbolo)  ){
                        aux3=aux3.abajo;
                    }
                    aux4=aux3;
                }
                
                SortedSet<String> U;
                U=First(aux4);
                if(U.contains("#")){
                    if(aux4.siguiente != null){
                        U.remove("#");
                        X.addAll(U);
                        X.addAll(First(aux4.siguiente));

                    }
                    else {
                        X.addAll(U);
                    }


                }
                X.addAll(U);
                aux=aux.abajo;
                 
            }
        }
        return X;
       
    }
    
    
    public SortedSet  <String> Follow(Nodo A, Nodo inicio){
        SortedSet  <String> F = new TreeSet<>();
        Nodo auxAb;
        if(A.indice==0)
            F.add("$");
        auxAb=inicio;
        
        while(auxAb!=null){
            int num=0;
            Nodo auxSig=auxAb.siguiente;
            while(auxSig!=null){
                auxSig=auxSig.siguiente;
                num++;                
            }
            if(num==3 && auxAb.siguiente.siguiente.simbolo.equals(A.simbolo)){
                boolean est=false;
                SortedSet  <String> Fp;
                /*Se usa la suposicion de que si una regla tiene 3 elementos 
                se da uno de estos dos casos:
                T' -> * F T'
                F -> ( E )
                Si se presentara algo como esto falla
                T' -> * F X'
                */
                if(!auxAb.siguiente.siguiente.siguiente.NoTerminal)
                    Fp=First(auxAb.siguiente.siguiente.siguiente);
                else
                    Fp=First(auxAb);
                if(Fp.contains("#")){
                    est=true;
                }
                Fp.remove("#");
                F.addAll(Fp);
                if(est){
                    F.addAll(Follow(auxAb,inicio));
                }
                
            }
            else if(num==2 && auxAb.siguiente.siguiente.simbolo.equals(A.simbolo)){
                F.addAll(Follow(auxAb,inicio));
            
            }
            
            
            auxAb=auxAb.abajo;
        }
        
        
        
        
        return F;      
    }
    
    public SortedSet  <String> FollowLR0(Nodo A, Nodo inicio){
        SortedSet  <String> F = new TreeSet<>();
        Nodo auxAb;
        Nodo auxSig2;
        if(A.indice==0)
            F.add("$");
        auxAb=inicio;
        
        if(!A.NoTerminal){
            F.addAll(First(A));
            return F;
        }
        
        while(auxAb!=null){
            auxSig2=auxAb.siguiente;
            while(auxSig2!=null){
                int num=0;
                Nodo auxSig=auxSig2;
                while(auxSig!=null){
                    auxSig=auxSig.siguiente;
                    num++;                
                }


                if(num==1 && auxSig2.simbolo.equals(A.simbolo) ){
                    F.addAll(FollowLR0(auxAb,inicio));
                }
                if(num==3 && auxSig2.siguiente.siguiente.simbolo.equals(A.simbolo)){

                    /*Se usa la suposicion de que si una regla tiene 3 elementos 
                    se da uno de estos dos casos:
                    T' -> T' * F 
                    F -> ( E )
                    Si se presentara algo como esto falla
                    T' -> * F X'
                    */
                    if(auxSig2.NoTerminal)
                        F.addAll(FollowLR0(auxAb,inicio));


                }
                else if(num==3 && auxSig2.simbolo.equals(A.simbolo)){
                    F.addAll(First(auxSig2.siguiente));

                }
                else if(num==3 && auxSig2.siguiente.simbolo.equals(A.simbolo)){
                    F.addAll(First(auxSig2.siguiente.siguiente));
                }
                auxSig2=auxSig2.abajo;
            
            }
            auxAb=auxAb.abajo;
        }
        
        
        
        
        return F;      
    }

            
}    