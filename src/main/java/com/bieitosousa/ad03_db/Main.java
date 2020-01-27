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

                switch (op) {
                    case 0: // 0. - Sair do programa. 
                        salir = true;
                        break;
                    case 1: // 1. - Selecionar una tenda.
                        s = true;
                        while (s) {
                            f.viewTiendas();
                            System.out.println("[para salir escribe exit] Seleccione una tienda : .\n");
                            String nomb = HelpFunctions.inputString("digame el nombre de la tienda ? ");
                            if (f.getMapTienda().containsKey(nomb)) {
                                t = f.getMapTienda().get(nomb);
                                System.out.println("Tienda Seleccionada ");
                                s = false;
                            } else if (nomb.equals("exit")) {
                                s = false;
                            }
                        }
                        break;
                    case 2: // 2. - Engadir unha tenda.
                        s = true;
                        while (s) {
                            System.out.println("[para salir escribe exit] Creando una tienda dime: .\n");
                            String nom = HelpFunctions.inputString("nombre ? ");
                            String pro = provinciaOnList().getNome();
                            String ciu = HelpFunctions.inputString("cidade ? ");
                            if (HelpFunctions.whiteSpace(nom) && HelpFunctions.whiteSpace(ciu)) {
                                f.addTienda(new Tienda(nom, pro, ciu));
                                System.out.print("Se a creado unha tenda:\n" + t.toString());
                                s = false;
                            } else if ("exit".equals(nom) || "exit".equals(pro) || "exit".equals(ciu)) {
                                s = false;
                            }
                        }
                        break;
                    case 3: // 3. - Mostar as tendas. 
                        f.viewTiendas();
                        break;

                    case 4: // 4. - Eliminar unha tenda . 
                        s = true;
                        while (s) {
                            f.viewTiendas();
                            System.out.println("[para salir escribe exit] Seleccione una Tenda para eliminala : .\n");
                            String nomb = HelpFunctions.inputString("digame el nombre de la Tienda ? ");
                            if (f.getMapTienda().containsKey(nomb)) {
                                System.out.println("Tienda [" + nomb + "]  ha sido eliminada ");
                                f.delTienda(nomb);
                                s = false;
                            } else if (nomb.equals("exit")) {
                                s = false;
                            }
                        }
                        break;
                    case 5: // 5. - Engadir un producto a franquicia. 
                        s = true;
                        while (s) {
                            System.out.println("[para salir escribe exit] Creando un Producto dime: .\n");
                            String nom = HelpFunctions.inputString("nombre ? ");
                            float prec = HelpFunctions.inputFloat("precio ? ");
                            String des = HelpFunctions.inputString("descripcion ? ");
                            if (HelpFunctions.whiteSpace(nom)) {
                                f.addProducto(new Producto(nom, prec, des));
                                System.out.print("Se a creado un producto:\n" + f.getMapProd().get(nom).toString());
                                s = false;
                            } else if ("exit".equals(nom)) {
                                s = false;
                            }
                        }

                        break;
                    case 6: // 6. - Mostrar os producto da franquicia.
                        f.viewProductos();
                        break;
                    case 7: // 7. - Engadir un producto a tenda. 
                        s = true;
                        while (s) {
                            f.viewProductos();
                            System.out.println("[para salir escribe exit] Dime un Producto .\n");
                            String n = HelpFunctions.inputString("nombre ? ");
                            if ((f.getMapProd().containsKey(n)) && selectT() != null) {
                                t.addProducto(n);
                                System.out.print("Se ha añadido un producto :\n" + t.toString());
                                s = false;
                            } else if ("exit".equals(n)) {
                                s = false;
                            }
                        }
                        break;
                    case 8: // 8. - Actualizar o stock dun producto nunha determinada tenda.. 
                        t.viewProductos();
                        s = true;
                        while (s) {
                            f.viewProductos();
                            System.out.println("[para salir escribe exit] Dime un Producto .\n");
                            String n = HelpFunctions.inputString("nombre ? ");
                            int stock = HelpFunctions.inputInt("stock ? ");
                            if ((f.getMapProd().containsKey(n)) && selectT() != null) {
                                t.addStock(n, stock);
                                System.out.print("Se ha añadido un producto :\n" + t.toString());
                                s = false;
                            } else if ("exit".equals(n)) {
                                s = false;
                            }
                        }

                        break;
                    case 9: // 9. - Mostrar o stock dun producto dunha tenda.

                        t.viewProductos();
                        s = true;
                        while (s) {
                            f.viewProductos();
                            System.out.println("[para salir escribe exit] Dime un Producto .\n");
                            String n = HelpFunctions.inputString("nombre ? ");
                            if ((f.getMapProd().containsKey(n)) && selectT() != null) {
                                t.viewProductos(n);
                                System.out.print("Se ha añadido un producto :\n" + t.toString());
                                s = false;
                            } else if ("exit".equals(n)) {
                                s = false;
                            }
                        }
                        break;
                    case 10: // 10. - Eliminiar un producto da franquicia.
                        s = true;
                        while (s) {
                            f.viewProductos();
                            System.out.println("[para salir escribe exit] Seleccione un Producto para eliminarlo : .\n");
                            String nomb = HelpFunctions.inputString("digame el nombre del Producto ? ");
                            if (f.getMapProd().containsKey(nomb)) {
                                System.out.println("Producto [" + nomb + "]  ha sido eliminado ");
                                f.delProducto(nomb);
                                s = false;
                            } else if (nomb.equals("exit")) {
                                s = false;
                            }
                        }
                        break;
                    case 11: // 11. - Eliminiar un producto a tenda. 
                        s = true;
                        t = selectT();
                        while (s) {
                            t.viewProductos();
                            System.out.println("[para salir escribe exit] Seleccione un Producto para eliminarlo l .\n");
                            String nomb = HelpFunctions.inputString("digame el nombre del Producto ? ");
                            if (t.getMapProd().containsKey(nomb)) {
                                System.out.println("Producto [" + nomb + "]  ha sido eliminado de " + t.getName());
                                t.delProducto(nomb);
                                s = false;
                            } else if (nomb.equals("exit")) {
                                s = false;
                            }
                        }
                        break;
                    case 12: // 12. - Engadir un empregado a Franquicia.  
                        s = true;
                        while (s) {
                            System.out.println("[para salir escribe exit] Creando un Empleado dime: .\n");
                            String nom = HelpFunctions.inputString("nombre ? ");
                            String apel = HelpFunctions.inputString("apellidos ? ");
                            if (HelpFunctions.whiteSpace(nom)) {
                                f.addEmpleado(new Empleado(nom, apel));
                                System.out.print("Se a creado un Empleado:\n" + f.getMapEmp().get(nom).toString());
                                s = false;
                            } else if ("exit".equals(nom)) {
                                s = false;
                            }
                        }
                        break;
                    case 13: // 13. - Mostar empregados a franquicia.  
                        f.viewEmpleados();
                        break;
                    case 14: // 14. - Eliminar un empregado da Franquicia.
                        s = true;
                        while (s) {
                            f.viewEmpleados();
                            System.out.println("[para salir escribe exit] Seleccione un Empleado para eliminarlo : .\n");
                            String nomb = HelpFunctions.inputString("digame el nombre del Empleado ? ");
                            if (f.getMapEmp().containsKey(nomb)) {
                                System.out.println("Empleado [" + nomb + "]  ha sido eliminado ");
                                f.delEmpleado(nomb);
                                s = false;
                            } else if (nomb.equals("exit")) {
                                s = false;
                            }
                        }
                        break;
                    case 15: // 15. - Engadir horas a un empregado na tenda. 
                        s = true;
                        while (s) {
                            f.viewEmpleados();
                            System.out.println("[para salir escribe exit] Dime un Empleado .\n");
                            String n = HelpFunctions.inputString("nombre ? ");
                            float horas = HelpFunctions.inputFloat("numero de horas ? ");
                            if ((f.getMapProd().containsKey(n)) && selectT() != null) {
                                t.addHoras(n, horas);
                                System.out.print("Se ha añadido un horario al empleado :\n" + n);
                                s = false;
                            } else if ("exit".equals(n)) {
                                s = false;
                            }
                        }

                        break;
                    case 16: // 16. - ver numero de horas dos empregados nunha tenda  
                        selectT().viewEmpleados();
                        break;
                    case 17: // 17. - Engadir un cliente.  
                        s = true;
                        while (s) {
                            System.out.println("[para salir escribe exit] Creando un Cliente dime: .\n");
                            String nom = HelpFunctions.inputString("nombre ? ");
                            String apel = HelpFunctions.inputString("apellidos ? ");
                            String mail = HelpFunctions.inputString("email ? ");
                            if (HelpFunctions.whiteSpace(nom) && HelpFunctions.whiteSpace(apel) && HelpFunctions.whiteSpace(mail)) {
                                f.addClient(new Cliente(nom, apel, mail));
                                System.out.print("Se a creado un Empleado:\n" + f.getMapEmp().get(nom).toString());
                                s = false;
                            } else if (("exit".equals(nom)) || ("exit".equals(apel)) || ("exit".equals(mail))) {
                                s = false;
                            }
                        }
                        break;
                    case 18: // 18. - Mostrar Clientes.  
                        f.viewClientes();
                        break;
                    case 19: // 19. - Eliminar un cliente. 
                        s = true;
                        while (s) {
                            f.viewClientes();
                            System.out.println("[para salir escribe exit] Seleccione un Cliente para eliminarlo : .\n");
                            String nomb = HelpFunctions.inputString("digame el nombre del cliente ? ");
                            if (f.getMapCli().containsKey(nomb)) {
                                System.out.println("Cliente [" + nomb + "]  ha sido eliminado ");
                                f.delClient(nomb);
                                s = false;
                            } else if (nomb.equals("exit")) {
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

    public static Tienda selectT() {
        Scanner read = new Scanner(System.in);
        int ot;
        System.out.println(
                "0. - Seguir con la tienda actual"
                + "1. - Cambiar de tienda"
                + "2. - Crear una tienda"
        );
        try {
            ot = read.nextInt();
            switch (ot) {
                case 0:
                    if (t == null) { // si no existe la tienda
                        if (f.getMapTienda().size() > 0) {// si el Map tiene datos
                            for (Tienda td : f.getMapTienda().values()) {
                                t = td;
                                return td; // me quedo con el primero
                            }
                        } else {// sino lo genero
                            return makeTienda();
                        }
                    } else {
                        return t;
                    }
                case 1:
                    if (f.getMapTienda().size() > 0) {
                        boolean s = true;
                        while (s) {
                            f.viewTiendas();
                            System.out.println("[para salir escribe exit] Seleccione una tienda : .\n");
                            String nomb = HelpFunctions.inputString("digame el nombre de la tienda ? ");
                            if (f.getMapTienda().containsKey(nomb)) {
                                System.out.println("Tienda Seleccionada ");
                                t = f.getMapTienda().get(nomb);
                                s = false;
                            } else if (nomb.equals("exit")) {
                                s = false;
                            }
                        }
                        return t;
                    } else {
                        return makeTienda();
                    }

            }
        } catch (InputMismatchException e) {
            System.out.println("Debes insertar un número");
            read.next();
        } finally {
            read.close(); // Cerramos el objeto Scanner
        }
        return t;
    }

    public static Tienda makeTienda() {
        Tienda td = null;
        String nom = null;
        boolean s = true;
        while (s) {
            System.out.println("[para salir escribe exit] Creando una tienda dime: .\n");
            nom = HelpFunctions.inputString("nombre ? ");
            String pro = provinciaOnList().getNome();
            String ciu = HelpFunctions.inputString("cidade ? ");
            if (HelpFunctions.whiteSpace(nom) && HelpFunctions.whiteSpace(ciu)) {
                f.addTienda(new Tienda(nom, pro, ciu));
                System.out.print("Se a creado unha tenda:\n" + t.toString());
                s = false;
            } else if ("exit".equals(nom) || "exit".equals(pro) || "exit".equals(ciu)) {
                s = false;
            }
        }
        t = f.getMapTienda().get(nom);
        return t;
    }

    private static Provincia provinciaOnList() {
        int i = -1;
        File fileProvincias = new File(".\\src\\main\\java\\com\\bieitosousa\\ad03_db\\XML\\XML_tarea.xml");
        JSonMake.setFile(fileProvincias);
        List<Provincia> pList = ReadObjJsonInFileProvincias();
        for (Provincia p : pList) {
            i++;
            System.out.println("[" + i + "] = { id =>" + p.getId() + "Nombre" + p.getNome() + "}");
        }
        int prov = HelpFunctions.inputInt("que provincia quieres  ? ");
        return pList.get(prov);
    }

}
