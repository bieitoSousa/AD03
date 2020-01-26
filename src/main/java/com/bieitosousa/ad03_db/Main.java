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
/**
 *
 * @author bieito
 */
public class Main {
     public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in); // Invocamos un método sobre un objeto Scanner
        int op;
        boolean s=true;
        boolean salir = false;
        //Compañia c = Compañia.getInstance();; 
        Franquicia f = Franquicia.getInstance();
        Tienda t = null;
        while (!salir) {
            String encabezado="";
            if(t != null){
                encabezado += "\nTienda selecionada "+t.toString();
                     }else {
                             encabezado += "\n Porfavor cree o seleccione una tienda.";
                            
            }
            System.out.println(
                            encabezado
                            +"\n1. - Selecionar una tenda.\n"
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
                case 0:
                    salir = true;
                    break;
                case 1:
                    s=true;
                    while(s){    
                        f.viewTiendas();
                        System.out.println("[para salir escribe exit] Seleccione una tienda : .\n");
                        String nomb = HelpFunctions.inputString("digame el nombre de la tienda ? ");
                        if(f.getMapTienda().containsKey(nomb)){
                            System.out.println("Tienda Seleccionada ");
                            s=false;
                        }else if(nomb.equals("exit")){
                            s=false;
                        }
                    }
                break;    
                case 2:
                     s = true;
                    while(s){
                    System.out.println("[para salir escribe exit] Creando una tienda dime: .\n");
                     String nom = HelpFunctions.inputString("nombre ? ");
                     String pro = HelpFunctions.inputString("Provincia ? ");
                     String ciu = HelpFunctions.inputString("cidade ? ");
                     if((HelpFunctions.whiteSpace(nom) && HelpFunctions.whiteSpace(ciu) && provinciaOnList(prov))||(nom ==-1 || pro == -1 || ciu == -1)){
                        f.addTienda(new Tienda(nombre,provincia,ciudad));
                        System.out.print("Se a creado unha tenda:\n" + t.toString());
                        s=false;
                     }else if("exit".equals(nom) || "exit".equals(pro) || "exit".equals(ciu)){
                      s=false;
                     }
                    }s=true;
                    break;
                case 3:
                    if (t != null) {
                        c.viewTiendaList();
                        String nomTienda = HelpFunctions.inputString(" introduzca nombre de la tienda.\n");
                        if(c.mapTienda.containsKey(nomTienda)){
                            c.deleteTienda(nomTienda);
                            System.out.print(" Se ha eliminado los productos y empleados de la tienda" + nomTienda + ").\n");
                        }else{ System.out.println("valor incorrecto los productos y empleados de la Tienda NO ELIMINADOS");}
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 4:
                    if (t != null) {
                        c.viewCatProducto();
                        t.viewProductosList();
                        int  idProducto = HelpFunctions.inputInt(" introduzca id del producto.\n");
                        if(c.catalogoProductos.containsKey(idProducto)){
                            t.addProducto(idProducto);
                            System.out.print(" SE ha añadido un producto a de la tienda : "+ t +" .\n");
                        }else{
                            System.out.print(" valor incorrecto PRODUCTO NO AÑADIDO.\n");
                        }
                       
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 5:
                    if (t != null) {
                        t.viewProductosList();
                        int  idProducto = HelpFunctions.inputInt(" introduzca id del producto.\n");
                        if(t.mapProducto.containsKey(idProducto)){
                            t.deleteProducto(idProducto);
                        System.out.print("Se a eliminado un producto de la tienda : "+ t +" .\n");
                        }else{
                            System.out.print(" valor incorrecto PRODUCTO NO elimniado.\n");
                        }
                       
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 6:
                    if (t != null) {
                        t.viewEmpregadoList();
                        String nomEmp = HelpFunctions.inputString(" introduzca nombre del empleado.\n");
                        String apelEmp = HelpFunctions.inputString(" introduzca apellido del empleado.\n");
                        if(HelpFunctions.whiteSpace(nomEmp) && HelpFunctions.whiteSpace(apelEmp)){
                        t.addEmpregado(nomEmp, apelEmp);                      
                        System.out.print("Se añadido un empleado.\n");
                        }
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 7:
                    if (t != null) {
                        t.viewEmpregadoList();
                        String nomEmp =  HelpFunctions.inputString(" introduzca nombre del empleado.\n");
                        if(t.mapEmpleado.containsKey(nomEmp)){
                            t.deleteEmpregado(nomEmp);
                        System.out.print("Se a eliminado el empleado de la tienda : "+ t +" .\n");
                        }else{
                            System.out.print(" valor incorrecto EMPLEADO NO elimniado.\n");
                        }
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 8:
                    if (t != null) {
                        t.viewClienteList();
                        String nomCli = HelpFunctions.inputString(" introduzca nombre del cliente.\n");
                        String apelCli = HelpFunctions.inputString(" introduzca apellido del cliente.\n");
                        String mailCli = HelpFunctions.inputString(" introduzca email del cliente.\n");
                        if(HelpFunctions.whiteSpace(nomCli) && HelpFunctions.whiteSpace(apelCli)&& HelpFunctions.whiteSpace(mailCli)){
                        t.addCliente(nomCli, apelCli, mailCli);
                        System.out.print(" Se ha añadido un cliente.");
                        }
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 9:
                    if (t != null) {
                        t.viewClienteList();
                        String nomCli = HelpFunctions.inputString(" introduzca nombre del cliente.\n");
                        if(t.mapCliente.containsKey(nomCli)){
                            t.deleteCliente(nomCli);
                        System.out.print("Se a eliminado el Cliente de la tienda : "+ t +" .\n");
                        }else{
                            System.out.print(" valor incorrecto CLIENTE NO elimniado.\n");
                        }
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 10:
                    if (t != null) {
                        c.backup();
                        System.out.print(" copia de seguriadade dos datos creada  .");
                    } else {
                        System.out.print("Primero deves de crear una tienda");
                    }
                    break;
                case 20:
                    
                    System.out.print("10. - Ler os titulares do periódico El País. ");
                    c.leerTitulares();
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



}