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
package com.bieitosousa.ad03_db.Data;

import com.bieitosousa.ad03_db.Data.DB_driver;
import com.bieitosousa.ad03_db.Data.Empleado;
import com.bieitosousa.ad03_db.Data.Tienda;
import com.bieitosousa.ad03_db.Data.Producto;
import com.bieitosousa.ad03_db.Json.Provincia;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author bieito
 */
public class Franquicia {

    HashMap<String, Tienda> mapTienda = new HashMap<String, Tienda>();
    HashMap<String, Producto> mapProd = new HashMap<String, Producto>();
    HashMap<String, Empleado> mapEmp = new HashMap<String, Empleado>();
    HashMap<String, Cliente> mapCli = new HashMap<String, Cliente>();
    HashMap<Integer, Provincia> mapProv = new HashMap<Integer, Provincia>();
    private static Franquicia f = null;
    private String name;
    protected DB_driver db = DB_driver.getInstance();
    protected static boolean opCli = true;//operaciones de escritura sobre Cliente
    protected static boolean opTi = true;//operaciones de escritura sobre Tienda
    protected static boolean opEmp = true;//operaciones de escritura sobre Empleado
    protected static boolean opProd = true;//operaciones de escritura sobre Producto
    protected static boolean opProv = true;//operaciones de escritura sobre Provincias

    public static Franquicia getInstance() {
        if (f == null) {
            f = new Franquicia();
        }
        return f;
    }

    public Franquicia() {
    }

    /**
     * * ... METODOS PUBLICOS ...    **
     */
    /**
     * ************************************************************
     * METODOS toString
     *
     **************************************************************
     */
    @Override
    public String toString() {
        return "Franquicia{" + "mapTienda=" + mapTienda + ", mapProd=" + mapProd + ", mapEmp=" + mapEmp + ", mapCli=" + mapCli + ", name=" + name + ", db=" + db + '}';
    }

    /**
     * ************************************************************
     * METODOS GET # evalua op{name} : determina si hubo operaciones de
     * escritura en la DB # true : Carga los datos de la DB. # false : Carga los
     * datos de la memoria. = getMapEmp = getMapCli = getMapProd = getMapTienda
     *
     ***************************************************************
     */
    public HashMap<String, Tienda> getMapTienda() {
        if (opTi) {
            cargarTiendas();
        }
        return mapTienda;
    }
    public HashMap<Integer, Provincia> getMapProvincias() {
        if (opProv) {
            if(!cargarProv())System.out.println("ERROR AL LEER PROVINCIAS EN LA DB");;
        }
        return mapProv;
    }
    public HashMap<String, Producto> getMapProd() {
        if (opProd) {
            cargarProductos();
        }
        return mapProd;
    }

    public HashMap<String, Empleado> getMapEmp() {
        if (opEmp) {
            cargarEmpleados();
        }
        return mapEmp;
    }

    public HashMap<String, Cliente> getMapCli() {
        if (opCli) {
            cargarClientes();
        }
        return mapCli;
    }

    /**
     * ************************************************************
     * METODOS SET # guarda los datos en memoria = setMapEmp = setMapCli =
     * setMapProd = setMapTienda
     *
     ***************************************************************
     */
    public void setMapTienda(HashMap<String, Tienda> mapTienda) {
        this.mapTienda = mapTienda;
    }

    public void setMapProd(HashMap<String, Producto> mapProd) {
        this.mapProd = mapProd;
    }

    public void setMapEmp(HashMap<String, Empleado> mapEmp) {
        this.mapEmp = mapEmp;
    }

    public void setListCli(HashMap<String, Cliente> mapCli) {
        this.mapCli = mapCli;
    }

    /**
     * ************************************************************
     * METODOS ADD # llama a un metodo privado para inserta los Objetos datos en
     * la DB = addEmpleado => Obj Empleado = addClient => Obj Cliente =
     * addProdducto => Obj Producto = addTienda => Obj Tienda # En el metodo
     * privado se determina op{name} = true se ha escrito en la DB 
     ***************************************************************
     */
    public void addClient(Cliente cliente) {
        Cliente cli = getMapCli().get(cliente.getName());
        if (cli == null || (cli != null && !cli.equals(cliente))) {
            insertCliente(cliente);
        } else {
            System.out.println("El cliente " + cli.toString() + " ya existe, introduzca un nuevo cliente");
        }
    }

    public void addEmpleado(Empleado empleado) {
        Empleado em = getMapEmp().get(empleado.getName());
        if (em == null || (em != null && !em.equals(empleado))) {
            insertEmpleado(empleado);
        } else {
            System.out.println("El Empleado " + em.toString() + " ya existe, introduzca un nuevo Empleado");
        }
    }

    public void addProducto(Producto producto) {
        Producto p = getMapProd().get(producto.getName());
        if (p == null || (p != null && !p.equals(producto))) {
            insertProducto(producto);
        } else {
            System.out.println("El producto " + p.toString() + " ya existe, introduzca un nuevo Producto");
        }
    }

    public void addTienda(Tienda tienda) {
        Tienda t = getMapTienda().get(tienda.getName());
        if (t == null || (t != null && !t.equals(tienda))) {
            if (t != null) {
                System.out.println(" name  => " + t.getName() + " ; " + tienda.getName());
                System.out.println(" ciudad  => " + t.getCiudad() + " ; " + tienda.getCiudad());
                System.out.println(" provincia  => " + t.getProvincia() + " ; " + tienda.getProvincia());
            }
            insertTienda(tienda);
        } else {
            System.out.println("La tienda " + t.toString() + " ya existe, introduzca una nueva Tienda");
        }
    }

    /**
     * ************************************************************
     * METODOS DEL # llama a un metodo privado para eliminar los Objetos datos
     * en la DB = delEmpleado => Obj Empleado = delClient => Obj Cliente =
     * delProducto => Obj Producto = delTienda => Obj Tienda # En el metodo
     * privado se determina op{name} = true se ha escrito en la DB 
     ***************************************************************
     */

    public void delClient(Cliente cliente) {
        Cliente cli = getMapCli().get(cliente.getName());
        if (cli != null && cli.equals(cliente)) {
            deleteCliente(cliente);
        } else {
            System.out.println("seleccione un Cliente de la Franquicia");
        }
    }

    public void delClient(String name) {
        Cliente cli = getMapCli().get(name);
        if (cli != null) {
            deleteCliente(cli);
        } else {
            System.out.println("seleccione un Cliente de la Franquicia");
        }
    }

    public void delEmpleado(Empleado empleado) {
        Empleado em = getMapEmp().get(empleado.getName());
        if (em != null && em.equals(empleado)) {
            deleteEmpleado(empleado);
        } else {
            System.out.println("seleccione un Empleado de la Franquicia");
        }
    }

    public void delEmpleado(String name) {
        Empleado em = getMapEmp().get(name);
        if (em != null) {
            deleteEmpleado(em);
        } else {
            System.out.println("seleccione un Empleado de la Franquicia");
        }
    }

    public void delProducto(Producto producto) {
        Producto p = getMapProd().get(producto.getName());
        if (p != null && p.equals(producto)) {
            deleteProducto(producto);
        } else {
            System.out.println("seleccione un Producto de la Franquicia");
        }
    }

    public void delProducto(String name) {
        Producto p = getMapProd().get(name);
        if (p != null) {
            deleteProducto(p);
        } else {
            System.out.println("seleccione un Producto de la Franquicia");
        }
    }

    public void delTienda(Tienda tienda) {
        Tienda t = getMapTienda().get(tienda.getName());
        if (t != null && t.equals(tienda)) {
            deleteTienda(tienda);
        } else {
            System.out.println("seleccione una Tienda de la Franquicia");
        }
    }

    public void delTienda(String name) {
        Tienda t = getMapTienda().get(name);
        if (t != null) {
            deleteTienda(t);
        } else {
            System.out.println("seleccione una Tienda de la Franquicia");
        }
    }

    /**
     * ************************************************************
     * METODOS VIEW VIEW{Name} recorre e imprimir los mapas = mapEmp = mapCli =
     * mapProd = mapTienda =================== evalua : op{Name} = true --> Los
     * datos se han modificado : \-> hay que cargar los datos en memoria. false
     * --> Los datos no se modificaron : \-> no hay que cargar datos en memoria.
     ***************************************************************
     */
        public void viewProvincias() {
        if (opProv) {
            System.out.println("Cargando Provincias [........]");
            if (cargarProv()){
                
        
        
                
            } else {
                System.out.print(" NO se han podido cargar las Provincias desde la DB  ");}
        }
                        System.out.println("_____________ FRANQUICIA : Provincias _____________");

        for (Provincia p  : getMapProvincias().values()) {
            System.out.println("["+p.getId()+"] =>> " + p.getNome());
        }
        System.out.println("===================================");
        
    }
    
    
    
    
    public void viewEmpleados() {
        if (opEmp) {
            cargarEmpleados();
            System.out.println("Cargando Empleados [........]");
        }
        System.out.println("_____________ FRANQUICIA : EMPLEADOS _____________");
        for (Empleado em : mapEmp.values()) {
            System.out.println(em.toString());
        }
        System.out.println("===================================");
    }

    public void viewClientes() {
        if (opCli) {
            cargarClientes();
            System.out.println("Cargando Clientes [........]");
        }
        System.out.println("_____________ FRANQUICIA : CLIENTES_____________");
        for (Cliente cli : mapCli.values()) {
            System.out.println(cli.toString());
        }
        System.out.println("===================================");
    }

    public void viewProductos() {
        if (opProd) {
            cargarProductos();
            System.out.println("Cargando Productos [........]");
        }
        System.out.println("_____________ FRANQUICIA : PRODUCTOS_____________");
        for (Producto p : mapProd.values()) {
            System.out.println(p.toString());
        }
        System.out.println("===================================");
    }

    public void viewTiendas() {
        if (opTi) {
            cargarTiendas();
            System.out.println("Cargando Tiendas [........]");
        }
        System.out.println("_____________ FRANQUICIA : TIENDAS_____________");
        for (Tienda t : mapTienda.values()) {
            System.out.println(t.toString());
        }
        System.out.println("===================================");
    }

    /**
     * * ... METODOS PRIVATE ...    **
     */
    //      ==== OPERACIONES LECTURA  SOBRE DB ======== \\
    /**
     * ************************************************************
     * cargar{Name} instancia un mapa de objeto a partir de un consulta en la dB
     * cargarEmpleados() => mapEmp cargarClientes => mapCli cargarProductos =>
     * mapProd cargarTiendas => mapTienda =================== op{Name} = false
     * --> Los datos no se han modificado desde la ultima carga en memoria
     ***************************************************************
     */
    
    private boolean cargarProv(){
     ArrayList< String> auxListProv = null;
        try {
            Connection con = db.getConn();
            Statement statement = con.createStatement();
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from PROVINCIA");
            
            mapProv.clear();
            while (rs.next()) {
                Provincia pv = new Provincia(
                rs.getInt("PROVINCIA_id"),
                rs.getString("PROVINCIA_name"));
                mapProv.put(pv.getId(), pv);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        return false;
        } finally {
            opProv = false;
            DB_driver.finishDB();
        }
        return true;
    }
    
    private void cargarEmpleados() {
        HashMap<String, Empleado> auxMapEmp = null;
        try {
            Connection con = db.getConn();
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from EMPLEADO");
            auxMapEmp = mapEmp;
            mapEmp.clear();
//                mapEmp=null;
            while (rs.next()) {
                Empleado em = new Empleado(
                        rs.getString("EMPLEADO_name"),
                        rs.getString("EMPLEADO_apellido")
                );
                em.setId(rs.getInt("EMPLEADO_id"));
                mapEmp.put(em.getName(), em);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            opEmp = false;
            DB_driver.finishDB();
//                if (mapTienda.isEmpty()){ // si no se cargan nuevos datos
//                        mapEmp=auxMapEmp;
//                        System.out.println(" Error no se han cargado datos en la tienda");
//                    } 
        }
    }

    private void cargarClientes() {
        HashMap<String, Cliente> auxMapCli = null;
        try {
            Connection con = db.getConn();
            Statement statement = con.createStatement();
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from CLIENTE");
            auxMapCli = mapCli;
            mapCli.clear();
            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("CLIENTE_name"),
                        rs.getString("CLIENTE_apellido"),
                        rs.getString("CLIENTE_email")
                );
                c.setId(rs.getInt("CLIENTE_id"));
                mapCli.put(c.getName(), c);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DB_driver.finishDB();
            opCli = false;
//                if (mapTienda.isEmpty()){ // si no se cargan nuevos datos
//                        mapCli=auxMapCli;
//                        System.out.println(" Error no se han cargado datos en la tienda");
//                    } 
        }
    }

    protected void cargarProductos() {
        HashMap<String, Producto> auxMapProd = null;
        try {
            Connection con = db.getConn();
            Statement statement = con.createStatement();
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from PRODUCTO");
            auxMapProd = mapProd;// por seguridad guardamos los datos del roducto
            mapProd.clear();
//                mapProd=null;
            while (rs.next()) {

                Producto p = new Producto(
                        rs.getString("PRODUCTO_name"),
                        rs.getFloat("PRODUCTO_price"),
                        rs.getString("PRODUCTO_description")
                );
                p.setId(rs.getInt("PRODUCTO_id"));
                mapProd.put(p.getName(), p);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DB_driver.finishDB();
            opProd = false;
//            if (mapProd.isEmpty()){// si no se cargan nuevos datos
//                mapProd=auxMapProd;
//                 System.out.println(" Error no se han cargado datos en el producto");
//            }
        }
    }

    private void cargarTiendas() {
        HashMap<String, Tienda> auxMapTienda = null;
        try {
            Connection con = db.getConn();
            Statement statement = con.createStatement();
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from TIENDA");
            auxMapTienda = mapTienda; // por seguridad guardamos los datos de la tienda
            mapTienda.clear();
//                mapTienda=null;
            while (rs.next()) {

                Tienda t = new Tienda(
                        rs.getString("TIENDA_name"),
                        rs.getString("TIENDA_provincia"),
                        rs.getString("TIENDA_ciudad")
                );
                t.setId(rs.getInt("TIENDA_id"));
                mapTienda.put(t.getName(), t);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DB_driver.finishDB();
            opTi = false;
//                if (mapTienda.isEmpty()){ // si no se cargan nuevos datos
//                    mapTienda=auxMapTienda;
//                    System.out.println(" Error no se han cargado datos en la tienda");
//                } 
        }

    }

    //      ==== OPERACIONES ESCRITURA SOBRE DB ======== \\
    /**
     * ************************************************************
     * ========== INSERT =================== insert{Name} llama a el drive
     * pasandole un objeto el drive --> Escribe el objeto en la dB
     * insertEmpleados() => Escribe un Empleado insertClientes => Escribe un
     * Cliente insertProductos => Escribe un Producto insertTiendas => Escribe
     * una Tienda =================== op{Name} = true --> Los datos se han
     * modificado desde la ultima carga en memoria
     ***************************************************************
     */
    private void insertTienda(Tienda t) {
        db.insertTienda(t);
        opTi = true;
    }

    private void insertProducto(Producto p) {
        db.insertProducto(p);
        opProd = true;
    }

    private void insertEmpleado(Empleado em) {
        db.insertEmpleado(em);
        opEmp = true;
    }

    private void insertCliente(Cliente cli) {
        db.insertCliente(cli);
        opCli = true;
    }

    /**
     * ************************************************************
     * ========== DELETE =================== delete{Name} llama a el drive
     * pasandole un objeto el drive --> borra los registros de ese objeto en la
     * dB deleteEmpleado => Escribe un Empleado deleteCliente => Escribe un
     * Cliente deleteProducto => Escribe un Producto deleteTienda => Escribe una
     * Tienda =================== op{Name} = true --> Los datos se han
     * modificado desde la ultima carga en memoria
     ***************************************************************
     */
    private void deleteTienda(Tienda t) {
        db.deleteTienda(t);
        opTi = true;
    }

    private void deleteProducto(Producto p) {
        db.deleteProducto(p);
        opProd = true;
    }

    private void deleteEmpleado(Empleado em) {
        db.deleteEmpleado(em);
        opEmp = true;
    }

    private void deleteCliente(Cliente cli) {
        db.deleteCliente(cli);
        opCli = true;
    }
}
