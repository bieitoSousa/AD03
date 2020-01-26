/*
 * The MIT License
 *
 * Copyright 2020 bieito.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.bieitosousa.ad03_db.Help;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bieito
 */
public class HelpFunctions {

    


    // public static void main(String[] args) throws Exception {
    //    int letra1 = inputInt("dimeletra 1");
    //    int letra2 = inputInt("dimeletra 1");
    //    int letra3 = inputInt("dimeletra 1");
    //    int letra4 = inputInt("dimeletra 1");
    //    System.out.println(letra1+letra2+letra3+letra4);
  

    // }



    // public static int inputInt(String mensaje) {
    //     Scanner reader = new Scanner(System.in); // Invocamos un método sobre un objeto Scanner
    //     int op = -1;

    //     boolean salir = false;
    //     while (!salir) {
    //         System.out.println(mensaje);
    //         try {
    //             op = reader.nextInt();
    //         } catch (InputMismatchException e) {
    //             System.out.println("Debes insertar un número");
    //             reader.next();
    //         } catch (Exception f) {
    //             f.getMessage();
    //         }
    //         salir = true;
    //     }
    //     reader.close(); // Cerramos el objeto Scanner

    //     return op;
    // }

    public static String inputString(String mensaje) {
        Scanner reader = new Scanner(System.in);
        System.out.print(mensaje);
    
        while (!reader.hasNextLine()) {
            System.out.print(mensaje);
            reader.next();
        }
        return reader.nextLine();
    }
        //reader.close(); // Cerramos el objeto Scanner

    public static boolean whiteSpace (String msnj){
        if (msnj.equals("")) {
            return false;
        }else if (msnj.contains(" ")){
            return false;
    }else return true;

    }     
    public static int inputInt(String mensaje) {
            Scanner reader = new Scanner(System.in);
            System.out.print(mensaje);
        
            while (!reader.hasNextInt()) {
                System.out.print(mensaje);
                reader.next();
            }
            return reader.nextInt();
        }

    public static void writeOnFile(File f, String[] lineas) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(f);
            // Escribimos linea a linea en el fichero
            for (String linea : lineas) {
                fichero.write(linea + "\n");
            }

        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        
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

    public static void writeNextOnFile(File f, String[] lineas) {
        FileWriter fichero = null;
        try {
            fichero = new FileWriter(f,true);
            // Escribimos linea a linea en el fichero
            for (String linea : lineas) {
                fichero.write(linea + "\n");
            }

        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        
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

    public void readonOnFile(File fichero) {

        // Fichero del que queremos leer
        //File fichero = new File("urlFichero");
        Scanner s = null;

        try
        {
            // Leemos el contenido del fichero
            System.out.println("... Leemos el contenido del fichero ...");
            s = new Scanner(fichero);

            // Leemos linea a linea el fichero
            while (s.hasNextLine()) {
                String linea = s.nextLine(); // Guardamos la linea en un String
                System.out.println(linea); // Imprimimos la linea
            }

        }catch(
        Exception ex)
        {
            System.out.println("Mensaje: " + ex.getMessage());
        }finally
        {
            // Cerramos el fichero tanto si la lectura ha sido correcta o no
            try {
                if (s != null)
                    s.close();
            } catch (Exception ex2) {
                System.out.println("Mensaje 2: " + ex2.getMessage());
            }
        }
    }


    public static String[] readonOnFile(File fichero,boolean bool) {

        // Fichero del que queremos leer
        //File fichero = new File("urlFichero");
        Scanner s = null;
        String arry[] = null; 
        List<String> lineas = new ArrayList<String>(); 

        try
        {
            // Leemos el contenido del fichero
            System.out.println("... Leemos el contenido del fichero ...");
            s = new Scanner(fichero);

            // Leemos linea a linea el fichero
            while (s.hasNextLine()) {
                String linea = s.nextLine(); // Guardamos la linea en un String
                if (bool){
                lineas.add(linea);
               }else {
                System.out.println(linea); // Imprimimos la linea
               } 
            }

        }catch(
        Exception ex)
        {
            System.out.println("Mensaje: " + ex.getMessage());
        }finally
        {
            // Cerramos el fichero tanto si la lectura ha sido correcta o no
            try {
                if (s != null)
                    s.close();
            } catch (Exception ex2) {
                System.out.println("Mensaje 2: " + ex2.getMessage());
            }
        }
         arry = lineas.toArray(new String[lineas.size()]);
        return arry;
    }

}   

