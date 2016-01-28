/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

public class ScannerG {

   
        public  int car_Act, car_inicialLexema, car_Anterior;
        public  String Lexema, cadena;
        public  int Tkn;

        public ScannerG(String e)
        {
            car_Act = 0;
            car_Anterior = -1;
            car_inicialLexema = 0;
            Lexema = "";
            cadena = e;
        }
        public ScannerG()
        {
            car_Act = 0;
            car_Anterior = -1;
            car_inicialLexema = 0;
            Lexema = "";
            cadena = "";
        }

        boolean EsSimbolo(char c)
        {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c>= '0' && c<= '9') || (c=='.') ||(c=='\'')||(c=='+') ||(c=='*') ||(c=='@') || (c=='#') || (c=='(') || (c==')') )//# es epsilon
              
                   
                return true;
            
            return false;
        }
       
        boolean EsEspacio(char c)
        {
            if ((c==' '))
              
                   
                return true;
            
            return false;
        }
        public int GetToken()   {
            car_Anterior = car_Act;
        while (car_Act < cadena.length()){
               
            if (car_Act == cadena.length()) {
                Lexema = "";
                Tkn = Token.FIN;
                return Tkn;
            }

            if(EsEspacio(cadena.charAt(car_Act))) {
                
                while(car_Act<cadena.length() && EsEspacio(cadena.charAt(car_Act))){
                    
                    
                    car_Act++;
                }
               
            }
            if(EsSimbolo(cadena.charAt(car_Act))) {
                Lexema="";
                while(car_Act<cadena.length() && EsSimbolo(cadena.charAt(car_Act))){
                    
                    Lexema = Lexema + cadena.charAt(car_Act);
                    car_Act++;
                }
               return Tkn = Token.SIMB;
            }
            switch (cadena.charAt(car_Act))
            {
                case '(':
                    Lexema = "(";
                    car_Act++;
                    Tkn = Token.PIZQ;
                    return Tkn;
                case ')':
                    Lexema = ")";
                    car_Act++;
                    Tkn = Token.PDER;
                    return Tkn;
                case '|':
                    Lexema = "|";
                    car_Act++;
                    Tkn = Token.OR;
                    return Tkn;
                case '&':
                    Lexema = "&";
                    car_Act++;
                    Tkn = Token.AMP;
                    return Tkn;
                case '+':
                    Lexema = "+";
                    car_Act++;
                    Tkn = Token.CPOS;
                    return Tkn;

                case '*':
                    Lexema = "*";
                    car_Act++;
                    Tkn = Token.CKLE;
                    return Tkn ;
                    
                case '-':
                    car_Act++;
                    if(cadena.charAt(car_Act)=='>'){
                        Lexema = "->";
                        car_Act++;
                        Tkn = Token.FLECHA;
                        return Tkn ;
                    }
                     Lexema = "-";
                    return Tkn=Token.SIMB;
                    
                case ';':
                    Lexema = ";";
                    car_Act++;
                    Tkn = Token.PYC;
                    return Tkn ;

                case '?':
                    Lexema = "?";
                    car_Act++;
                    Tkn = Token.INTE;
                    return Tkn;
                    
                
                    
                
               /**     
                case '/0':
                    Lexema = "FIN";
                    
                    car_Act++;
                    Tkn = Token.FIN;
                    return Tkn;
                **/
            }
            if(cadena.charAt(car_Act)=='/'){
                car_Act++;
                switch(cadena.charAt(car_Act)){
                    case '*':
                        Lexema = "*";
                        car_Act++;
                        Tkn = Token.SIMB;
                        return Tkn ;
                    case '+':
                        Lexema = "+";
                        car_Act++;
                        Tkn = Token.SIMB;
                        return Tkn ;
                        
                    case '-':
                        Lexema = "-";
                        car_Act++;
                        Tkn = Token.SIMB;
                        return Tkn ;
                    
                     case '|':
                        Lexema = "|";
                        car_Act++;
                        Tkn = Token.SIMB;
                        return Tkn ;
                    
                     case '&':
                        Lexema = "&";
                        car_Act++;
                        Tkn = Token.SIMB;
                        return Tkn ;
                         
                    case '?':
                        Lexema = "?";
                        car_Act++;
                        Tkn = Token.SIMB;
                        return Tkn;
                    default:
                        Lexema = "/";
                        Tkn = Token.SIMB;
                        return Tkn;

                }
                
            }
        }
            if(car_Act == cadena.length()){
                Tkn = Token.FIN;
                    return Tkn;
             }
            Lexema = cadena.charAt(car_Act)+"";
            car_Act++;
            Tkn = Token.ERROR; 
            return Token.ERROR;
        }
        public String CadenaToken()
        {
            switch (Tkn)
            {
                case Token.OR:
                    return "OR";

                case Token.AMP:
                    return "AMPERSON";

                case Token.CPOS :
                    return "CERRADURA POSITIVA";

                case Token.CKLE :
                    return "CERRADURA DE KLEEN";

                case Token.PIZQ :
                    return "PARENTESIS IZQUIERDO";

                case Token.PDER :
                    return "PARENTESIS DERECHO";

                case Token.SIMB:
                    return "SIMBOLO";
                    
                case Token.INTE:
                    return "INTERROGACIÓN";
                
                case Token.ERROR:
                    return "ERROR";
                    
                case Token.FLECHA:
                    return "FLECHA";
                    


                case Token.FIN:
                    return "FIN";
            }
            return "ERROR";
        }

        public void RegresarToken()
        {
            car_Act = car_Anterior;
        }
        public String  GetLexema()
        {
            return Lexema;
        }
        
        public Integer[] GetEdoScanner(){
            Integer[] a= {car_Anterior,car_Act};
            return a;
   
        }
        
        public void RestauraEdoScanner(Integer[] a){
            car_Anterior=a[0];
            car_Act=a[1];
   
        }
   
    
}
