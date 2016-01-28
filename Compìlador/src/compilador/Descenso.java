/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author nomarlo
 */
public class Descenso {
    
    public Scanner Lex;
   
    public Integer idAFN=0;

    public Descenso(String s){
        
        Lex = new Scanner(s);
    }
    
    
    public boolean AnalizarExpr(){
        boolean R;
        AFN A1=new AFN();
        R = E(A1);
        if (R){
            if (Lex.GetToken() == Token.FIN){
                
                return R;
            }
            /**
            else{
                R=false;
            }**/
        }
        return R;
    }
    
    
    public boolean E(AFN A1){
        if(T(A1))
            if(Ep( A1))
                return true;
        return false;
    }
    
    public boolean Ep(AFN A1){
        int t;
        AFN A2=new AFN();
        t=Lex.GetToken();
        if(t==Token.OR){
            if(T( A2))
                if(Ep( A2)){
                    Compilador.UnionAutomatas(A1, A2);
                    return true;
                }
            return false;
        }
        Lex.RegresarToken();
        return true;
    }
    
    public boolean T(AFN A1){
        if(C(A1))
            if(Tp( A1))
                return true;
        return false;
    }
    
    public boolean Tp(AFN A1){
        int t;
        t=Lex.GetToken();
        AFN A2=new AFN();
        if(t==Token.AMP){
            if(C( A2))
                if(Tp(A2)){
                    Compilador.ConcatenacionAutomatas(A1, A2);
                    return true;
                }
            return false;
        }
        Lex.RegresarToken();
        return true;
    }
    
    public boolean C(AFN A1){
        if(F( A1))
            if(Cp( A1))
                return true;
        return false;
    }
    
    public boolean Cp(AFN A1){
        int t;
        t=Lex.GetToken();
        if(t==Token.CPOS || t==Token.CKLE|| t==Token.INTE){
            if(Cp( A1)){
                if(t==Token.CPOS){
                    Compilador.CerraduraPositivaAutomata(A1);
                }
                if(t==Token.CKLE){
                    Compilador.CerraduraKleeneAutomata(A1);
                }
                if(t==Token.INTE){
                    Compilador.opcional(A1);
                }
                    
                return true;
            }
            return false;
        }
        Lex.RegresarToken();
        return true;
            
    }
    
    public boolean F(AFN A1){
        int t;
        t=Lex.GetToken();
        //System.out.println(t);
        if(t==Token.PIZQ){
            
            if(E(A1)){
                t=Lex.GetToken();
                if(t==Token.PDER)
                    return true;
        
            }
            return false;
        }
        if(t==Token.SIMB){
            Compilador.NuevoAutomata(Lex.GetLexema().charAt(0), idAFN, A1);
            idAFN++;
            return true;
        }
        return false;
    }
}