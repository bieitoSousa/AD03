/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bieitosousa.ad03_db;

import com.bieitosousa.ad03_db.Data.Franquicia;
import com.bieitosousa.ad03_db.Data.Tienda;
import java.util.*;
import com.bieitosousa.ad03_db.Help.*;
import com.bieitosousa.ad03_db.Data.*;
import com.bieitosousa.ad03_db.Json.*;
import com.bieitosousa.ad03_db.XML.*;
import static com.bieitosousa.ad03_db.Json.JSonMake.ReadObjJsonInFileProvincias;
import java.io.File;

/**
 *
 * @author bieito
 */
public class Main {

    static Franquicia f = Franquicia.getInstance();
    static Tienda t = null;

    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in); // Invocamos un método sobre un objeto Scanner
        int op;
        boolean s = true;
        boolean salir = false;
        //Compañia c = Compañia.getInstance();; 

        while (!salir) {
            String encabezado = "";
            if (t != null) {
                encabezado += "\nTienda selecionada " + t.toString();
            } else {
                encabezado += "\n Porfavor cree o seleccione una tienda.";

            }
            System.out.println(
                    encabezado
                    + "\n1. - Selecionar una tenda.\n"
                    + "2. - Engadir unha tenda.\n"
                    + "3. - Mostar as tendas.\n"
                    + "4. - Eliminar unha tenda (elimínanse tódolos productos e empragados desta).\n"
                    + "5. - Engadir un producto a franquicia.\n"
                    + "6. - Mostrar os producto da franquicia.\n"
                    + "7. - Engadir un producto a tenda.\n"
                    + "8. - Actualizar o stock dun producto nunha determinada tenda..\n"
                    + "9. - Mostrar o stock dun producto dunha tenda..\n"
                    + "10. - Eliminiar un producto da franquicia.\n"
                    + "11. - Eliminiar un producto a tenda.\n"
                    + "12. - Engadir un empregado a Franquicia.\n"
                    + "13. - Mostar empregados a franquicia.\n"
                    + "14. - Eliminar un empregado da Franquicia.\n"
                    + "15. - Engadir horas a un empregado na tenda.\n"
                    + "16. - ver numero de horas dos empregados nunha tenda \n"
                    + "17. - Engadir un cliente.\n"
                    + "18. - Mostrar Clientes.\n"
                    + "19. - Eliminar un cliente.\n"
                    + "20. - Ler os titulares do periódico El País. \n"
                    + "0. - Sair do programa.\n"
            );

            try {
                op = reader.nextInt();
                String n ;
                String a ;
                String e ;

                switch (op) {
                    case 0: // 0. - Sair do programa. 
                        salir = true;
                        break;
                    case 1: // 1. - Selecionar una tenda.
                       if (f.getMapTienda().size()>0){
                           System.out.println("SELECCIONE UNA TIENDA");
                        menuSelectTienda();
                       }else{
                           System.out.println("NO SE HA PODIDO SELLECIONAR UNA TIENDA : CREANDO TIENDA");
                           menuAddTienda(); 
                           System.out.println("se a seleccionado la tienda : "+ t.toString());
                       }  
                       
                        break;
                    case 2: // 2. - Engadir unha tenda.
                        menuAddTienda();
                       
                        break;
                    case 3: // 3. - Mostar as tendas. 
                        f.viewTiendas();
                        break;

                    case 4: // 4. - Eliminar unha tenda . 
                        s = true;
                        n= "";
                        while (s && !"exit".equals(n)) {
                            f.viewTiendas();
                            System.out.println("[para salir escribe exit] Seleccione una Tenda para eliminala : .\n");
                            n = HelpFunctions.inputString("digame el nombre de la Tienda ? ");
                             if ("exit".equals(n))break;
                             
                            if (f.getMapTienda().containsKey(n)) {
                                System.out.println("Tienda [" + n + "]  ha sido eliminada ");
                                f.delTienda(n);
                                s = false;
                            
                            
                            }
                        }
                        break;
                    case 5: // 5. - Engadir un producto a franquicia. 
                        menuAddProd();
                        break;
                    case 6: // 6. - Mostrar os producto da franquicia.
                        f.viewProductos();
                        break;
                    case 7: // 7. - Engadir un producto a tenda. 
                    if(getTienda()){
                        if (f.getMapProd().size()>0){
                         menuAddProdToTiend();
                       }else{
                        menuAddProd(); 
                            if (f.getMapTienda().size()>0){
                                 menuAddProdToTiend();
                            }
                       }
                    }else{
                            System.out.println("No se han encontrado tiendas disponibles : porfavor introduzca o seleccione una tienda");
                        }  
                        break;
                    case 8: // 8. - Actualizar o stock dun producto nunha determinada tenda.. 
                        if(getTienda()){
                        menuAddTiendaStock(s);
                        }else{
                            System.out.println("No se han encontrado tiendas disponibles : porfavor introduzca o seleccione una tienda");
                        }
                        break;
                    case 9: // 9. - Mostrar o stock dun producto dunha tenda.
                        t.viewProductos();
                        s = true;
                        n="";
                        while (s && !"exit".equals(n) ) {
                            f.viewProductos();
                            System.out.println("[para salir escribe exit] Dime un Producto .\n");
                            n = HelpFunctions.inputString("nombre ? ");
                            if ("exit".equals(n))break;
                            if ((f.getMapProd().containsKey(n)) && t != null) {
                                t.viewProductos(n);
                                s = false;
                            }
                            
                        }
                        break;
                    case 10: // 10. - Eliminiar un producto da franquicia.
                        s = true;
                        n="";
                        while (s && !"exit".equals(n)) {
                            f.viewProductos();
                            System.out.println("[para salir escribe exit] Seleccione un Producto para eliminarlo : .\n");
                             n = HelpFunctions.inputString("digame el nombre del Producto ? ");
                             if ("exit".equals(n))break;
                             if (f.getMapProd().containsKey(n)) {
                                System.out.println("Producto [" + n + "]  ha sido eliminado ");
                                f.delProducto(n);
                                s = false;
                        }
                        }
                        break;
                    case 11: // 11. - Eliminiar un producto a tenda. 
                        if (getTienda()){
                        s = true;
                        n="";
                        while (s&& !"exit".equals(n)) {
                            t.viewProductos();
                            System.out.println("[para salir escribe exit] Seleccione un Producto para eliminarlo l .\n");
                            n = HelpFunctions.inputString("digame el nombre del Producto ? ");
                             if ("exit".equals(n))break;
                            if (t.getMapProd().containsKey(n)) {
                                System.out.println("Producto [" + n + "]  ha sido eliminado de " + t.getName());
                                t.delProducto(n);
                                s = false;
                            }} }else{
                            System.out.println("No se han encontrado tiendas disponibles : porfavor introduzca o seleccione una tienda");
                        }
                        break;
                    case 12: // 12. - Engadir un empregado a Franquicia.  
                        s = true;
                        n="";
                        a="";
                        while (s&& !"exit".equals(n) && !"exit".equals(a)) {
                            System.out.println("[para salir escribe exit] Creando un Empleado dime: .\n");
                            n = HelpFunctions.inputString("nombre ? ");
                             if ("exit".equals(n))break;
                            a = HelpFunctions.inputString("apellidos ? ");
                             if ("exit".equals(n))break;
                            if (HelpFunctions.whiteSpace(n)) {
                                f.addEmpleado(new Empleado(n, a));
                                System.out.print("Se a creado un Empleado:\n" + f.getMapEmp().get(n).toString());
                                s = false;
                            
                        }}
                        break;
                    case 13: // 13. - Mostar empregados a franquicia.  
                        f.viewEmpleados();
                        break;
                    case 14: // 14. - Eliminar un empregado da Franquicia.
                        s = true;
                        n="";
                        while (s && !"exit".equals(n)) {
                            f.viewEmpleados();
                            System.out.println("[para salir escribe exit] Seleccione un Empleado para eliminarlo : .\n");
                             n = HelpFunctions.inputString("digame el nombre del Empleado ? ");
                            
                              if ("exit".equals(n))break;
                              if (f.getMapEmp().containsKey(n)) {
                                System.out.println("Empleado [" + n + "]  ha sido eliminado ");
                                f.delEmpleado(n);
                                s = false;
                            } else if (n.equals("exit")) {
                                s = false;
                            }
                        }
                        break;
                    case 15: // 15. - Engadir horas a un empregado na tenda. 
                    if(getTienda()){    
                        
                        s = true;
                        n="";
                        while (s&& !"exit".equals(n)) {
                            f.viewEmpleados();
                            System.out.println("[para salir escribe exit] Dime un Empleado .\n");
                             n = HelpFunctions.inputString("nombre ? ");
                            
                              if ("exit".equals(n))break;
                              float horas = HelpFunctions.inputFloat("numero de horas ? ");
                            if ((f.getMapProd().containsKey(n)) && t != null) {
                                t.addHoras(n, horas);
                                System.out.print("Se ha añadido un horario al empleado :\n" + n);
                                s = false;
                            } 
                        }
                }else{
                            System.out.println("No se han encontrado tiendas disponibles : porfavor introduzca o seleccione una tienda");
                        }
                        break;
                    case 16: // 16. - ver numero de horas dos empregados nunha tenda  
                        if (t != null)
                        t.viewEmpleados();
                        else System.out.println("No se han encontrado tiendas disponibles : porfavor introduzca o seleccione una tienda");
                        break;
                    case 17: // 17. - Engadir un cliente.  
                        s = true;
                        n="";
                        a="";
                        e="";
                        while (s && !"exit".equals(n) && !"exit".equals(a) && !"exit".equals(e)) {
                            System.out.println("[para salir escribe exit] Creando un Cliente dime: .\n");
                             n = HelpFunctions.inputString("nombre ? ");
                              if ("exit".equals(n))break;
                             a = HelpFunctions.inputString("apellidos ? ");
                              if ("exit".equals(a))break;
                             e = HelpFunctions.inputString("email ? ");
                             if ("exit".equals(e))break;
                             if (HelpFunctions.whiteSpace(n) && HelpFunctions.whiteSpace(a) && HelpFunctions.whiteSpace(e)) {
                                f.addClient(new Cliente(n, a, e));
                                System.out.print("Se a creado un Empleado:\n" + f.getMapEmp().get(n).toString());
                                s = false;
                            } 
                        }
                        break;
                    case 18: // 18. - Mostrar Clientes.  
                        f.viewClientes();
                        break;
                    case 19: // 19. - Eliminar un cliente. 
                        s = true;
                        n="";
                        while (s&& !"exit".equals(n)) {
                            f.viewClientes();
                            System.out.println("[para salir escribe exit] Seleccione un Cliente para eliminarlo : .\n");
                            n = HelpFunctions.inputString("digame el nombre del cliente ? ");
                             if ("exit".equals(n))break;
                            if (f.getMapCli().containsKey(n)) {
                                System.out.println("Cliente [" + n + "]  ha sido eliminado ");
                                f.delClient(n);
                                s = false;
                            } 
                        }
                        break;

                    case 20:// 20. - Ler os titulares do periódico El País.  
                        ReadXML.read();
                        break;
                    default:
                        System.out.print(" Opcion no valida inserte [0] si quiere cancelar ");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                reader.next();
            }
        }
        reader.close(); // Cerramos el objeto Scanner

    }

    private static void menuAddTiendaStock(boolean s) {
        t.viewProductos();
        s = true;
        String n="";
        while (s && !"exit".equals(n)) {
            f.viewProductos();
            System.out.println("[para salir escribe exit] Dime un Producto .\n");
             n = HelpFunctions.inputString("nombre ? ");
              if ("exit".equals(n))break;
            int stock = HelpFunctions.inputInt("stock ? ");
            if ((f.getMapProd().containsKey(n)) && t
                    != null) {
                t.addStock(n, stock);
                System.out.print("Se ha añadido un producto :\n" + t.toString());
                s = false;
            } 
        }
    }

    private static void menuAddProdToTiend() {
        System.out.println("Vamos ha añadir un producto a la tienda : "+t.toString());
        boolean s = true;
        String n="";
        while (s&& !"exit".equals(n)) {
            f.viewProductos();
            System.out.println("[para salir escribe exit] Dime un Producto .\n");
            n = HelpFunctions.inputString("nombre ? ");
             if ("exit".equals(n))break;
            if ((f.getMapProd().containsKey(n)) && t != null) {
                t.addProducto(n);
                System.out.print("Se ha añadido un producto :\n" + t.toString());
                s = false;
            } 
        }
    }

//    public static Tienda t {
//        Scanner read = new Scanner(System.in);
//        int ot;
//        System.out.println(
//                "\n0. - Seguir con la tienda actual\n"
//                + "1. - Cambiar de tienda\n"
//                + "2. - Crear una tienda\n"
//        );
//        try {
//            ot = read.nextInt();
//            switch (ot) {
//                case 0:
//                    if (t == null) { // si no existe la tienda
//                        System.out.println("No hay ninguna tienda selaccionada");
//                        if (f.getMapTienda().size() > 0) {// si el Map tiene datos
//                            for (Tienda td : f.getMapTienda().values()) {
//                                t = td;
//                                System.out.println("seleccionado primera tienda por defecto :"+t.toString());
//                                return td; // me quedo con el primero
//                            }
//                        } else {// sino lo genero
//                            System.out.println("No hay ninguna tienda creada : Creando tirnda");
//                            Tienda a = makeTienda();
//                            System.out.println("seleccinado tienda : "+t.toString());
//                            return t;
//                        }
//                    } else {
//                        System.out.println("hay una tienda sellecionada :"+t.toString());
//                        return t;
//                    }
//                case 1:
//                    if (f.getMapTienda().size() > 0) {
//                        boolean s = true;
//                        while (s) {
//                            f.viewTiendas();
//                            System.out.println("[para salir escribe exit] Seleccione una tienda : .\n");
//                            String nomb = HelpFunctions.inputString("digame el nombre de la tienda ? ");
//                            if (f.getMapTienda().containsKey(nomb)) {
//                                System.out.println("Tienda Seleccionada ");
//                                t = f.getMapTienda().get(nomb);
//                                s = false;
//                            } else if (nomb.equals("exit")) {
//                                s = false;
//                            }
//                        }
//                        return t;
//                    } else {
//                        return makeTienda();
//                    }
//
//            }
//        } catch (InputMismatchException e) {
//            System.out.println("Debes insertar un número");
//            read.next();
//        } finally {
//            read.close(); // Cerramos el objeto Scanner
//        }
//        return t;1
//    }

//    public static Tienda makeTienda() {
//        Tienda td = null;
//        String nom = null;
//        boolean s = true;
//        while (s) {
//            System.out.println("[para salir escribe exit] Creando una tienda dime: .\n");
//            nom = HelpFunctions.inputString("nombre ? ");
//            String pro = provinciaOnList().getNome();
//            String ciu = HelpFunctions.inputString("cidade ? ");
//            if (HelpFunctions.whiteSpace(nom) && HelpFunctions.whiteSpace(ciu)) {
//                f.addTienda(new Tienda(nom, pro, ciu));
//                System.out.print("Se a creado unha tenda:\n" + t.toString());
//                s = false;
//            } else if ("exit".equals(nom) && "exit".equals(pro) && "exit".equals(ciu)) {
//                s = false;
//            }
//        }
//        t = f.getMapTienda().get(nom);
//        return t;
//    }

    private static Provincia provinciaOnList() {
        int i = -1;
        File fileProvincias = new File(".\\src\\main\\java\\com\\bieitosousa\\ad03_db\\Json\\provincias.json");
        JSonMake.setFile(fileProvincias);
        List<Provincia> pList = ReadObjJsonInFileProvincias();
        for (Provincia p : pList) {
            i++;
            System.out.println("[" + i + "] ||====>>> { id =>[" + p.getId() + "] Nombre [" + p.getNome() + "] }");
        }
        int prov = HelpFunctions.inputInt("que provincia quieres  ? ");
        return pList.get(prov);
    }

    private static void menuAddTienda() {
                        boolean s = true;
                        String n="";
                        String c="";
                        while (s && !"exit".equals(n)&& !"exit".equals(c))  {
                            System.out.println("[para salir escribe exit] Creando una tienda dime: .\n");
                            n = HelpFunctions.inputString("nombre ? ");
                             if ("exit".equals(n))break;
                            String pro = provinciaOnList().getNome();
                            c = HelpFunctions.inputString("cidade ? ");
                             if ("exit".equals(c))break;
                            if (HelpFunctions.whiteSpace(n) && HelpFunctions.whiteSpace(c)&& !f.getMapTienda().containsKey(n)) {
                                f.addTienda(new Tienda(n, pro, c));
                                t=f.getMapTienda().get(n);
                                if (t!=null)System.out.print("Se a creado unha tenda:\n" + t.toString());
                                s = false;
                            } 
                        }   
    }

    private static void menuSelectTienda() {
                        boolean s = true;
                        String n="";
                        while (s && !"exit".equals(n)) {
                                System.out.println("s vale "+s);
                                System.out.println("n vale "+!"exit".equals(n));
                            f.viewTiendas();
                            System.out.println("[para salir escribe exit] Seleccione una tienda : .\n");
                            n = HelpFunctions.inputString("digame el nombre de la tienda ? ");
                             if ("exit".equals(n))break;
                            if (f.getMapTienda().containsKey(n)) {
                                t = f.getMapTienda().get(n);
                                System.out.println("Tienda Seleccionada ");
                                s = false;
                            } 
                        }    }

    private static void menuAddProd() {
        System.out.println("vamos a añadir un producto a la franquicia");
                        boolean s = true;
                        String n ="";
                        String d ="";
                        float prec=0;
                        while (s && !"exit".equals(n) && !"exit".equals(d)) {
                            System.out.println("[para salir escribe exit] Creando un Producto dime: .\n");
                            n = HelpFunctions.inputString("nombre ? ");
                            if ("exit".equals(n))break;
                            prec = HelpFunctions.inputFloat("precio ? ");
                            d = HelpFunctions.inputString("descripcion ? ");
                            if ("exit".equals(d))break;
                            if (HelpFunctions.whiteSpace(n)) {
                                f.addProducto(new Producto(n, prec, d));
                                System.out.print("Se a creado un producto:\n" + f.getMapProd().get(n).toString());
                                s = false;
                            } }
    }

    private static  boolean getTienda() {
        while (t== null){
        menuSelectTienda();
        }
        return t != null;        

    }

}
