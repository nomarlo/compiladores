/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;
import java.io.*;
import java.util.Vector;
/**
 *
 * @author Prodigy
 */
public class Archivo {
    static String[] er =new String[20];
    static String[] token=new String[20];
    static int contador=0;
    public static void LeeArchivo(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        contador=0;
        int j;
        
       
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        
        //leemos archivo linea por linea
        while((cadena = b.readLine())!=null) {
            StringTokenizer s=new StringTokenizer(cadena,":");//Dividimos la linea en palabras
            //mientras existan palabras (tokens)
            while (s.hasMoreTokens()){
                er[contador]=s.nextToken();
                token[contador]=s.nextToken();
                contador++;
            }
        }
        
        b.close();//terminamos lectura
        
        //imprimimos cadenas con er. y tokens
        /**
        for(j=0;j<contador;j++){
            System.out.println("exp reg:"+j+" "+er[j]+"\n");
            System.out.println("token:"+j+" "+token[j]+"\n");
        }**/
    }
    
    public static void LeeArchivoG(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        contador=0;
        int j;
        
       
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        
        //leemos archivo linea por linea
        while((cadena = b.readLine())!=null) {
            er[contador]=cadena;
            contador++;
            /**
            StringTokenizer s=new StringTokenizer(cadena,":");//Dividimos la linea en palabras
            //mientras existan palabras (tokens)
            while (s.hasMoreTokens()){
                er[contador]=s.nextToken();
                token[contador]=s.nextToken();
                contador++;
            }**/
        }
        
        b.close();//terminamos lectura
        
        //imprimimos cadenas con er. y tokens
        /**
        for(j=0;j<contador;j++){
            System.out.println("exp reg:"+j+" "+er[j]+"\n");
            System.out.println("token:"+j+" "+token[j]+"\n");
        }**/
    }
    
    public static void escribirArchivo(Map<Integer,Integer>map,Vector<Vector<Integer>>mapeo2,Vector<Vector<Integer>>tabla)throws FileNotFoundException,IOException{
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(Compilador.ruta+"afd.txt");
            pw = new PrintWriter(fichero);
 
 //           for (int i = 0; i < 10; i++)
   //             pw.println("Linea " + i);
             
              for (Map.Entry<Integer,Integer> entry : map.entrySet()){
                  pw.println(+ entry.getKey()+" "+entry.getValue());
              }
             pw.println(";");
              for(int i=0;i<mapeo2.size();i++){
                  for(int j=0;j<mapeo2.get(i).size();j++){
                    pw.print(mapeo2.get(i).get(j)+" ");
                  }
                  pw.println("");
              }
              pw.println(";");
              
              for(int i=0;i<tabla.size();i++){
                  for(int j=0;j<tabla.get(i).size();j++){
                    pw.print(tabla.get(i).get(j)+" ");
                  }
                  pw.println("");
              }
              
              
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
    }
    }
    
    
    public static SortedMap LeeMapeoColumnas(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        SortedMap <Integer, Integer>  MSimbolos;
        MSimbolos=  new TreeMap<>();
        int j;
        
       
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        
        //leemos archivo linea por linea
        while((cadena = b.readLine())!=null && cadena.charAt(0)!=';') {
            //System.out.println(cadena);
            StringTokenizer s=new StringTokenizer(cadena);//Dividimos la linea en palabras
            //mientras existan palabras (tokens)
            while (s.hasMoreTokens() ){
                
                MSimbolos.put(Integer.parseInt(s.nextToken()), Integer.parseInt(s.nextToken()));
            }
        }
        
        b.close();//terminamos lectura
        
        return MSimbolos;
    }
    
    public static Vector LeeMapeoEstados(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        Vector <Vector <Integer>>  MEstados;
        Vector <Integer> Ax;
        MEstados=  new Vector<>();
        int j;
        
       
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
         while((cadena = b.readLine())!=null && cadena.charAt(0)!=';') {
             System.out.println(cadena);
         }
        //leemos archivo linea por linea
         cadena="";
        while((cadena = b.readLine())!=null && cadena.charAt(0)!=';') {
            StringTokenizer s=new StringTokenizer(cadena);//Dividimos la linea en palabras
            //mientras existan palabras (tokens)
            Ax=new Vector<>();
            while (s.hasMoreTokens()){
                Ax.add(Integer.parseInt(s.nextToken()));
                
            }
            MEstados.add(Ax);
        }
        
        b.close();//terminamos lectura
        
        return MEstados;
    }
    
    
    
    
     public static Vector LeeTabla(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        Vector <Vector <Integer>>  Tabla;
        Vector <Integer> Ax;
        Tabla=  new Vector<>();
        int j;
        
       
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
         while((cadena = b.readLine())!=null && cadena.charAt(0)!=';') {
             
         }
         while((cadena = b.readLine())!=null && cadena.charAt(0)!=';') {
             
         }
        //leemos archivo linea por linea
        while((cadena = b.readLine())!=null ) {
            StringTokenizer s=new StringTokenizer(cadena);//Dividimos la linea en palabras
            //mientras existan palabras (tokens)
            Ax=new Vector<>();
            while (s.hasMoreTokens()){
                Ax.add(Integer.parseInt(s.nextToken()));
                
            }
            Tabla.add(Ax);
        }
        
        b.close();//terminamos lectura
        
        return Tabla;
    }

}