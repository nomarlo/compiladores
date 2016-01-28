/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.SortedSet;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;


/**
 *
 * @author Becario
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */

    static AFN [] automatas;
    static AFD [] automatasD;
    static TablaLL1 [] tablasLL1;
    static Nodo[] Listas;
    //static Nodo[]
    static InputStreamReader leerStr = new InputStreamReader(System.in);
    static BufferedReader leer = new BufferedReader(leerStr);
    static String ruta;
    static int tokenFinal;
    
    
    public static void main(String[] args) throws IOException {
        Integer idAFN=0,idAFD=0;        
        int opc,simbolo,idAFN1,idAFN2;
        String cadena;
        Descenso D;
        DescensoGramaticas DG;
        LR0 tablaLR0=new LR0();
        automatas = new AFN [50];
        automatasD = new AFD [50];
        Listas = new Nodo [50];
        tablasLL1= new TablaLL1[50];
        ruta="C:/Users/nomarlo/Desktop/";
        while (true){
            
            System.out.println("1: Ingrese la ruta del archivo de texto (ER)");
            System.out.println("2: Reconocer cadena");
            System.out.println("3: Ingrese la ruta del archivo de texto (Gramaticas LL1)");
            System.out.println("4: Ingrese la ruta del archivo de texto (Gramaticas LR0)");
            System.out.println("5: Salir");
            
            opc= Integer.parseInt(leer.readLine());
            
            //  Prueba2 p= new Prueba2();
            //p.Metodo2(0);
            
            switch(opc){
                
                case 1:
                    //Archivo.LeeArchivo(leer.readLine());
                    Archivo.LeeArchivo(ruta+"archivo.txt");
                    for(int j=0;j<Archivo.contador;j++){
                        D=new Descenso (Archivo.er[j]);
                        if(j>0)
                            D.idAFN=1;
                        D.AnalizarExpr();
                        if(j==0){
                            for (Integer e : automatas[0].estadosFinales){ //recorremos todos los estados finales para asiganrles su token correspondiente
                                Estado.M.get(e).setToken(Integer.parseInt(Archivo.token[j]));
                            }
                        }
                        else{
                            for (Integer e : automatas[1].estadosFinales){ //recorremos todos los estados finales para asiganrles su token correspondiente
                                Estado.M.get(e).setToken(Integer.parseInt(Archivo.token[j]));
                            }
                            UnionEspecialAutomatas(0,1);
                        }
                        
                        //System.out.println("token:"+j+" "+token[j]+"\n");
                    }
                    automatasD[idAFD]=new AFD(automatas[0],idAFD);
                    //D=new Descenso ("(a|b)*&(c+)");
                    
                    Archivo.escribirArchivo(automatasD[idAFD].MSimbolos,automatasD[idAFD].MEstados, automatasD[idAFD].tablaOptimizada);
                    idAFD++;
                    automatasD[idAFD]=new AFD(1);
                    automatasD[idAFD].MSimbolos=Archivo.LeeMapeoColumnas(ruta+"afd.txt");
                    automatasD[idAFD].MEstados=Archivo.LeeMapeoEstados(ruta+"afd.txt");
                    automatasD[idAFD].tablaOptimizada=Archivo.LeeTabla(ruta+"afd.txt");
                    
                    //automatasD[idAFD].imprimeAFD();
                    
                break;
                    
                case 2:
                    
                    /**    
                    System.out.println("Ingrese el id del automata");
                    idAFN1 = Integer.parseInt(leer.readLine());
                    * */
                    idAFN1=0;
                    while (true){
                        System.out.println("Ingrese la cadena");
                        cadena = leer.readLine();
                        boolean edo=ReconocerCadena(cadena,idAFN1);
                        /**
                        if(edo)
                            System.out.println("Token: "+tokenFinal);
                        else
                            System.out.println("No Aceptado");
                        
                        **/
                        edo=ReconocerCadenaAFD(cadena,1);
/**
                        if(edo)
                            System.out.println("Token: "+tokenFinal);
                        else
                            System.out.println("No Aceptado");
                        **/
                        System.out.println("Desea analizar otra cadena (s/n)?");
                        opc=leer.readLine().charAt(0);
                      
                        if(opc!=115)
                            break;
                    }
                break;
                    
                case 3:
                    //Archivo.LeeArchivo(leer.readLine());
                    Archivo.LeeArchivoG(ruta+"archivoG.txt");
                    String reglas="";
                    for(int j=0;j<Archivo.contador;j++){
                       reglas+= Archivo.er[j];
                        
                    }
                    DG=new DescensoGramaticas (reglas);
                    Listas[0]=new Nodo();
                    if(DG.AnalizarExpr(Listas[0]))
                        System.out.println(reglas+"Gramatica Correcta");
                    else
                        System.out.println("Gramatica Incorrecta");
                    DG.Terminales=Listas[0].setTerminal(DG.NoTerminales);
                    //imprimeLista(Listas[0]);
                   // SortedSet <String>s= DG.First(Listas[0]);
                    /**
                    for (String e : s){
                        System.out.println(e);
                    }
                    **/
                    /**
                    s= DG.Follow(Listas[0].abajo.abajo.abajo.abajo,Listas[0]);
                    
                    for (String e : s){
                        System.out.println(e);
                    }
                    **/
                    tablasLL1[0] = new TablaLL1();
                    tablasLL1[0].setColumnas(DG.Terminales);
                    tablasLL1[0].setFilas(Listas[0]);
                    tablasLL1[0].setTabla(DG, Listas[0]);
                    
                    /**
                    String SX[]=new String[tablasLL1[0].Columnas.size()];
                    for(int i=0;i<tablasLL1[0].Columnas.size();i++){
                        String sy=tablasLL1[0].Columnas.keySet().toArray()[i].toString();
                        SX[tablasLL1[0].Columnas.get(sy)]=sy;
                    }
                    
                    for(int i=0;i<tablasLL1[0].Columnas.size();i++){
                        System.out.print(SX[i]+"\t");
                    }
                    System.out.println();
                    for(int i=0;i<tablasLL1[0].tabla.length;i++){
                        for(int e=0;e<tablasLL1[0].tabla[i].length;e++){
                            System.out.print(tablasLL1[0].tabla[i][e]+"\t");
                        }
                        System.out.println();
                    }
                    **/
                    
                    //Anlisis sintactico con tabla LL1
                    while(true){
                        System.out.println("Ingrese la cadena a analizar");
                        String cdn=leer.readLine();
                        if(cdn=="")
                            break;                        
                        cdn+=" $";
                        StringTokenizer s=new StringTokenizer(cdn);//Dividimos la linea en palabras
                        String simAct=s.nextToken();
                        Stack <String>P = new Stack<>();
                        P.push("$");
                        P.push(Listas[0].simbolo);
                        boolean est=true;
                        while(P.peek()!="$"){
                            if(DG.Terminales.contains(P.peek())){
                                
                                if(!P.pop().equals(simAct)){
                                    System.out.println("Cadena incorrecta");
                                    est=false;
                                    break;
                                }
                                simAct=s.nextToken();
                                continue; 
                            }
                            if(P.peek().equals("#")){
                                P.pop();
                                continue;
                            }
                            int x=tablasLL1[0].Filas.get(P.pop());
                            int y=tablasLL1[0].Columnas.get(simAct);
                            
                            int indice=tablasLL1[0].tabla[x][y];
                            
                          
                            if(indice==-1){
                                System.out.println("Cadena incorrecta");
                                est=false;
                                break;
                            }
                            
                            
                            Nodo auxAb=Listas[0];
                            Nodo aux=auxAb.siguiente;
                            
                            while(aux.indice!=indice){
                                aux=aux.abajo;
                                if(aux==null){
                                    auxAb=auxAb.abajo;
                                    aux=auxAb.siguiente;
                                }
                            }
                            String sAux[]= new String [10];
                            int xAux=0;
                            while(aux!=null){
                                sAux[xAux]=aux.simbolo;
                                aux=aux.siguiente;
                                xAux++;
                            }
                            xAux--;
                            while(xAux>=0){                                
                                P.push(sAux[xAux]);
                                xAux--;
                            }
                        }
                        
                        if(est)
                            System.out.println("Cadena correcta");
                        
                        
                    }
                    
                break;
                
                case 4:
                    Archivo.LeeArchivoG(ruta+"archivoGLR0.txt");
                    reglas="";
                    for(int j=0;j<Archivo.contador;j++){
                       reglas+= Archivo.er[j];
                        
                    }
                    DG=new DescensoGramaticas (reglas);
                    Listas[0]=new Nodo();
                    if(DG.AnalizarExpr(Listas[0]))
                        System.out.println(reglas+"Gramatica Correcta");
                    else
                        System.out.println("Gramatica Incorrecta");
                    DG.Terminales=Listas[0].setTerminal(DG.NoTerminales);
                    tablaLR0.setConjuntos(DG.NoTerminales, DG.Terminales);
                    tablaLR0.setColumnas(Listas[0]);
                    tablaLR0.setFilas();
                    tablaLR0.setTabla();
                    tablaLR0.doItems(Listas[0], DG.reglas);
                    tablaLR0.fillTabla(DG,Listas[0]);
                    
                    SortedSet <String>s ;//DG.Follow(Listas[0],Listas[0]);
                  //  s=DG.FollowLR0(Listas[0].siguiente, Listas[0]);
                    //s=DG.FollowLR0(Listas[0].abajo.abajo, Listas[0]);
                    //s=DG.FollowLR0(Listas[0].abajo.abajo.abajo, Listas[0]);
                    
                    /**
                    String SX[]=new String[tablaLR0.Columnas.size()];
                    for(int i=0;i<tablaLR0.Columnas.size();i++){
                        String sy=tablaLR0.Columnas.keySet().toArray()[i].toString();
                        SX[tablaLR0.Columnas.get(sy)]=sy;
                    }
                    
                    for(int i=0;i<tablaLR0.Columnas.size();i++){
                        System.out.print(SX[i]+"\t");
                    }
                    System.out.println();
                    for(int i=0;i<tablaLR0.nFilas;i++){
                        for(int e=0;e<tablaLR0.Columnas.size();e++){
                            System.out.print(tablaLR0.tabla[i][e]+"\t");
                        }
                        System.out.println();
                    }
                    
                    /**/
                     
                    Stack <Integer> P= new Stack<>();
                    Stack <String> Sim= new Stack<>();
                    
                    String cdn=leer.readLine();
                    if(cdn=="")
                        break;                        
                    cdn+=" $";
                    StringTokenizer si=new StringTokenizer(cdn);//Dividimos la linea en palabras
                    String simAct=si.nextToken();
                    P.push(0);
                    while(true){
                        int xyz=1;
                        if(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(simAct)] <99 && tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(simAct)] != -1 ){
                            P.push(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(simAct)]);
                            Sim.push(simAct);
                            simAct=si.nextToken();
                        }
                        else if(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(simAct)] >100){
                            int tAux=tablaLR0.tamano(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(simAct)]-100);
                            int tAux2=tAux;
                            while(tAux2>0){
                                Sim.pop();                                
                                tAux2--;
                            }
                            Sim.push(tablaLR0.ReglasOrigen.get(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(simAct)]-100).simbolo);
                            
                            tAux2=tAux;
                            while(tAux2>0){
                                P.pop();                                
                                tAux2--;
                            }
                            if(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(Sim.peek())]!=-1)
                                P.push(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(Sim.peek())]);
                            
                        }
                        else if(tablaLR0.tabla[P.peek()][tablaLR0.Columnas.get(simAct)] ==99){
                            System.out.println("cadena correcta");
                            break;
                        }
                        else{
                            System.out.println("cadena incorrecta");
                            break;
                        }
                        
                    }
                    
                    
                    //imprimeLista(Listas[0]);
                   // SortedSet <String>s= DG.First(Listas[0]);
                    /**
                    for (String e : s){
                        System.out.println(e);
                    }
                    **/
                    /**
                    s= DG.Follow(Listas[0].abajo.abajo.abajo.abajo,Listas[0]);
                    
                    for (String e : s){
                        System.out.println(e);
                    }
                    
                    tablasLL1[0] = new TablaLL1();
                    tablasLL1[0].setColumnas(DG.Terminales);
                    tablasLL1[0].setFilas(Listas[0]);
                    tablasLL1[0].setTabla(DG, Listas[0]);
                    **/
                break;
                default:
                
                break;
            }            
           
            if (opc==5)
                break;
        }        
        
        
        // TODO code application logic here
      
    }
    
    public static void imprimeLista(Nodo l){
        System.out.print(l.simbolo+" ");
        if(l.siguiente!=null)
            imprimeLista(l.siguiente);
        System.out.println("");
        if(l.abajo!=null)
            imprimeLista(l.abajo);
        
    }

    public static AFN NuevoAutomata(int simbolo, int idAFN){
        
        automatas[idAFN]=new AFN(idAFN);                    
        automatas[idAFN].creaAFN(simbolo);
        //System.out.println(automatas[idAFN].estadoInicial);
        //idAFN++;
        
        return automatas[idAFN];
    }
    
    public static AFN NuevoAutomata(int simbolo, int idAFN, AFN A){
        
        //automatas[idAFN]=new AFN(idAFN);                    
       // automatas[idAFN].creaAFN(simbolo);
        A.idAFN=idAFN;
        A.creaAFN(simbolo);
        automatas[idAFN]=A;
        //System.out.println(automatas[idAFN].estadoInicial);
        //idAFN++;
        
        return A;
    }
    
    
    public static AFN ConcatenacionAutomatas(int idAFN1, int idAFN2){
        automatas[idAFN1].concatenacionAFN(automatas[idAFN2]);     
        
        return automatas[idAFN1];
    }
    
    public static AFN ConcatenacionAutomatas(AFN A1, AFN A2){
        A1.concatenacionAFN(A2);
        
        
        return automatas[A1.idAFN]=A1;
    }
    
    public static AFN UnionAutomatas(int idAFN1, int idAFN2){
        automatas[idAFN1].unionAFN(automatas[idAFN2]);     
        
        return automatas[idAFN1];
    }
    
    public static AFN UnionAutomatas(AFN A1, AFN A2){
        A1.unionAFN(A2);
        
        return automatas[A1.idAFN]=A1;    
        
    }
    
    public static AFN UnionEspecialAutomatas(int idAFN1, int idAFN2){
        automatas[idAFN1].unionEspecialAFN(automatas[idAFN2]);     
        
        return automatas[idAFN1];
    }
    
                   
    public static AFN CerraduraPositivaAutomata(int idAFN1){
        automatas[idAFN1].cerraduraPositiva();
        
        return automatas[idAFN1];
    }   
    
     public static AFN CerraduraPositivaAutomata(AFN A1){
         A1.cerraduraPositiva();        
        
        return automatas[A1.idAFN]=A1;
    }   
    
     
     
    public static AFN CerraduraKleeneAutomata(int idAFN1){
        automatas[idAFN1].cerraduraKleene();
        
        return automatas[idAFN1];
    }
    
    public static AFN CerraduraKleeneAutomata(AFN A1){
        A1.cerraduraKleene();
        
        return automatas[A1.idAFN]=A1;
    } 
    
    public static AFN opcional(AFN A1){
        A1.opcional();
        
        return automatas[A1.idAFN]=A1;
        
    }
    
    public static boolean ReconocerCadena(String c, int idAFN){
        SortedSet <Integer> Ax= new TreeSet<>();
        SortedSet <Integer> R= new TreeSet<>();
        //System.out.println(idAFN);
        //System.out.println(automatas[idAFN].estadoInicial);
        Ax.add(automatas[idAFN].estadoInicial);
        SortedSet <Integer> I = automatas[idAFN].cerraduraEpsilon(Ax);
       // for(Integer e: I)
         //   System.out.println(e);
        int indice=0;
        //System.out.println("tamano"+c.length());
        char ay=(char)228;
        c=c.length()==0?ay+"":c;
        int carAct=c.charAt(indice);
       // System.out.println("tamano"+c.length());
        while(indice<c.length()){
            carAct=c.charAt(indice);
            R=automatas[idAFN].IrA(I, carAct);
            if(R.isEmpty())
                return false;
            I=R;
            indice++;
            
            
        }        
        for(Integer e:I){
            Vector <Integer> eFinales=automatas[idAFN].estadosFinales;
            for (Integer x: eFinales){
                if((int)x==(int)e){
                    tokenFinal=Estado.M.get(x).getToken();
                    return true;
                }
            }                
            
        }
        return false;
    }    
    
    public static boolean ReconocerCadenaAFD(String c, int idAFD){
        /** con la tabla no optimizada
        int indice=0, pos;
        pos=0;
        //System.out.println("tamano"+c.length());
        char ay=(char)228;
        c=c.length()==0?ay+"":c;
        int carAct=c.charAt(indice);
       // System.out.println("tamano"+c.length());
        while(indice<c.length()){
            carAct=c.charAt(indice);
            pos=automatasD[idAFD].tabla[pos][automatasD[idAFD].MSimbolos.get(carAct)];
            if(pos ==-1)
                return false;
            
            indice++;
            
            
        }        
        
        if(automatasD[idAFD].tabla[pos][automatasD[idAFD].MSimbolos.size()]!=-1){
            tokenFinal=automatasD[idAFD].tabla[pos][automatasD[idAFD].MSimbolos.size()];
            return true;
        }
          **/ 
        
        
        int indice=0, pos,posAnt;
        posAnt=pos=0;
        Stack <Integer>P = new Stack<>();
        Stack <String>L = new Stack<>();
        String lexema="";
        //System.out.println("tamano"+c.length());
        char ay=(char)228;
        c=c.length()==0?ay+"":c;
        int carAct=c.charAt(indice);
       // System.out.println("tamano"+c.length());
        while(indice<c.length()){
            carAct=c.charAt(indice);
            pos=automatasD[idAFD].tablaOptimizada.get(automatasD[idAFD].MEstados.get(pos).get(0)).get(automatasD[idAFD].MSimbolos.get(carAct));
            
            if(pos ==-1 ){
                while(automatasD[idAFD].MEstados.get(posAnt).get(1)==-1){
                    posAnt=P.pop();
                    indice--;
                    lexema=L.pop();
                    
                }
                System.out.println(lexema+":"+automatasD[idAFD].MEstados.get(posAnt).get(1));
                posAnt=pos=0;
                lexema="";
                //return false;
            }
            else{
                P.push(posAnt);
                L.push(lexema);
                posAnt=pos;
                lexema+=(char)carAct;
                indice++;
            }
            
            if(indice ==c.length()){
                while(automatasD[idAFD].MEstados.get(posAnt).get(1)==-1){
                    posAnt=P.pop();
                    indice--;
                    lexema=L.pop();
                    
                }
                System.out.println(lexema+":"+automatasD[idAFD].MEstados.get(posAnt).get(1));
                posAnt=pos=0;
                lexema="";
            }
            
            
            
        }
        System.out.println(lexema+":"+automatasD[idAFD].MEstados.get(posAnt).get(1));
        /**
        if(automatasD[idAFD].MEstados.get(pos).get(1)!=-1){
            tokenFinal=automatasD[idAFD].MEstados.get(pos).get(1);
            return true;
        }        
        
        
        return false;**/
        return true;
    }
    
}
