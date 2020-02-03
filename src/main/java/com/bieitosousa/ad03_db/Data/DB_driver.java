/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bieitosousa.ad03_db.Data;

import com.bieitosousa.ad03_db.Data.Empleado;
import com.bieitosousa.ad03_db.Data.Tienda;
import com.bieitosousa.ad03_db.Data.Producto;
import com.bieitosousa.ad03_db.Json.JSonMake;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author bieito
 */

public class DB_driver {

    protected File dbHome;
    public static Connection con = null;
    private static DB_driver db = null;
    private static String[] DBsql = {
        
        "CREATE TABLE IF NOT EXISTS PROVINCIA (\n"
        + "   PROVINCIA_id INTEGER PRIMARY KEY UNIQUE,\n"
        + "   PROVINCIA_name TEXT NOT NULL UNIQUE \n"
        + ");",
        
        "CREATE TABLE IF NOT EXISTS TIENDA (\n"
        + "   TIENDA_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
        + "   TIENDA_name TEXT NOT NULL UNIQUE,\n"
        + "   TIENDA_provincia TEXT NOT NULL,\n"
        + "   TIENDA_ciudad TEXT NOT NULL\n"
        + ");",
         "CREATE TABLE IF NOT EXISTS CLIENTE (\n"
        + "    CLIENTE_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
        + "    CLIENTE_name TEXT NOT NULL,\n"
        + "    CLIENTE_apellido TEXT NOT NULL,\n"
        + "    CLIENTE_email TEXT NOT NULL UNIQUE\n"
        + ");",

         "CREATE TABLE IF NOT EXISTS EMPLEADO (\n"
        + "    EMPLEADO_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
        + "    EMPLEADO_name TEXT NOT NULL,\n"
        + "    EMPLEADO_apellido TEXT NOT NULL\n"
        + ");",

         "CREATE TABLE IF NOT EXISTS PRODUCTO (\n"
        + "    PRODUCTO_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
        + "    PRODUCTO_name TEXT NOT NULL,\n"
        + "	   PRODUCTO_price REAL NOT NULL ,\n"
        + "    PRODUCTO_description TEXT NOT NULL \n"
        + ");",

         "CREATE TABLE IF NOT EXISTS TIENDA_EMPLEADO(\n"
        + "   TIENDA_id INTEGER,\n"
        + "   EMPLEADO_id INTEGER,\n"
        + "   nHoras REAL,\n"
        + "   PRIMARY KEY (TIENDA_id, EMPLEADO_id),\n"
        + "   FOREIGN KEY (TIENDA_id) \n"
        + "      REFERENCES TIENDA (TIENDA_id) \n"
        + "         ON DELETE CASCADE \n"
        + "         ON UPDATE NO ACTION,\n"
        + "   FOREIGN KEY (EMPLEADO_id) \n"
        + "      REFERENCES EMPLEADO (EMPLEADO_id) \n"
        + "         ON DELETE CASCADE \n"
        + "         ON UPDATE NO ACTION\n"
        + ");",

         "CREATE TABLE IF NOT EXISTS TIENDA_PRODUCTO(\n"
        + "   TIENDA_id INTEGER,\n"
        + "   PRODUCTO_id INTEGER,\n"
        + "   stock INTEGER,\n"
        + "   PRIMARY KEY (TIENDA_id, PRODUCTO_id),\n"
        + "   FOREIGN KEY (TIENDA_id) \n"
        + "      REFERENCES TIENDA (TIENDA_id) \n"
        + "         ON DELETE CASCADE \n"
        + "         ON UPDATE NO ACTION,\n"
        + "   FOREIGN KEY (PRODUCTO_id) \n"
        + "      REFERENCES PRODUCTO (PRODUCTO_id) \n"
        + "         ON DELETE CASCADE \n"
        + "         ON UPDATE NO ACTION\n"
        + ");"

    };

    public void DB_driver() {

    }

    public static void main(String[] args) {

    }

    /**
     * ******************************
     * = METODOS STATIC =  
********************************
     */
    /**
     * ************************************************************
     * ========== Connexion SQLITE =================== = getConn --> si no exite
     * conexion la crea (startDB()). = finishDB --> elimina la conexion con DB
     * [desconnetDatabase(con)] = startDB --> define un DB y una conxion
     * [connectDatabase(db)] = connectDatabase --> se conecta a una DB y devulve
     * la conexion = desconnetDatabase(con) --> se desconecta de DB y elimna la
     * conexion
     **********************************************************
     */
    public static Connection getConn() {
        if (con == null) {
            startDB();
        }
        return con;
    }

    public static void finishDB() {
        desconnetDatabase(con);
        con = null;
    }

    private static void startDB() {
        String db = "novaBaseDeDatos.db";
        con = connectDatabase(db);
    }

    //Creamos a conexión a base de datos
    private static Connection connectDatabase(String filename) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
            //System.out.println("Conexión realizada con éxito");
            return connection;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Este método desconectase dunha base de datos en SQLLite a que se lle pasa a conexión
    private static void desconnetDatabase(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                //System.out.println("Desconexión realizada con éxito");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * ************************************************************
     * ========== INSTANCIAR DB_driver =================== = instance --> si no
     * exite instancia el driver. |-> inicia la conexion |-> evalua si esta
     * creada la base de datos |__ finaliza la conexion = getData --> evalua si
     * esta creada la base de datos (dbExist) => creada : no hace nada => no
     * creada : crea las tablas (createTable) = dbExist --> consulta si hay
     * tablas creadas en la DB. = createTable --> Crea las tablas de la DB [Si
     * no existen].
     *
     **********************************************************
     */
    public static DB_driver getInstance() {
        if (db == null) {
            db = new DB_driver();
            getData();
            finishDB();
        }
        return db;
    }

    private static void getData() {
        
        if (!dbExist()) {// si no esta creada la base de datos la creamos   
            for (int i = 0; i < DBsql.length; i++) {
                if(createTable(getConn(), DBsql[i])){
                 System.out.println("Tabla ["+i+"] cargada correctamente");
                    
                }else{
                System.out.println("NO se han cargado las tablas correctamente : es necesario que se cargen ["+DBsql.length+ "] tablas");

                }
            }
        if(JSonMake.CargarFileProvincias(new File(".\\provincias.json"))){
            System.out.println("Cargadas provincias en DB");
        }else {
            System.out.println("NO se ha cargado las provincias en la DB");;    
        }
        }
    }

    private static boolean dbExist() {
        boolean exist = false;
        try {
            Statement statement = getConn().createStatement();
            ResultSet rs = statement.executeQuery("SELECT name, sql FROM sqlite_master WHERE type='table'");
            if (rs.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DB_driver.finishDB();
        }
        return exist;
    }

    private static boolean createTable(Connection con, String sql) {
        try {
            /* String sql = "CREATE TABLE IF NOT EXISTS person (\n" +
                    "id integer PRIMARY KEY,\n"+
                    "nome text NOT NULL\n"+
                    ");";
             */
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " SQL FALLIDO {{______\n " + sql + "\n______}}");
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    /**
     * ******************************
     * = METODOS PUBLIC =  
********************************
     */
    /**
     * ************************************************************
     * ========== DELETE PUBLIC =================== = deleteTienda --> llama
     * metodo privado -> Elimina una Tienda. = deleteCliente --> llama metodo
     * privado -> Elimina un Cliente. = deleteEmpleado --> llama metodo privado
     * -> Elimina un Empleado. = deleteProducto --> llama metodo privado ->
     * Elimina un Producto. = deleteTiendaProducto --> llama metodo privado ->
     * Eliminar un Producto de una Tienda. = deleteTiendaEmpleado --> llama
     * metodo privado -> Eliminar un Empleado de una Tienda.
     *
     **********************************************************
     */
    public boolean deleteTienda(Tienda t) {
        return deleteTienda(getConn(), t.getName());
    }

    public boolean deleteProducto(Producto p) {
        return deleteProducto(getConn(), p.getName());
    }

    public boolean deleteEmpleado(Empleado em) {
        return deleteEmpleado(getConn(), em.getName());
    }

    public boolean deleteCliente(Cliente cli) {
        return deleteCliente(getConn(), cli.getName());
    }

    public boolean deleteTiendaProducto(Tienda t, Producto p) {
        return deleteTiendaProducto(getConn(), t.getId(), p.getId());
    }

    public boolean deleteTiendaEmpleado(Tienda t, Empleado em) {
        return deleteTiendaEmpleado(getConn(), t.getId(), em.getId());
    }

    /**
     * ************************************************************
     * ========== INSERT PUBLIC =================== = insertTienda --> llama
     * metodo privado -> inserta los registros del objeto Tienda. =
     * insertCliente --> llama metodo privado -> inserta los registros del
     * objeto Cliente. = insertEmpleado --> llama metodo privado -> inserta los
     * registros del objeto Empleado. = insertProducto --> llama metodo privado
     * -> inserta los registros del objeto Producto. = insertTiendaProducto -->
     * llama metodo privado -> inserta los registros del objeto Producto de una
     * Tienda. = insertTiendaEmpleado --> llama metodo privado -> inserta los
     * registros del objeto Empleado de una Tienda.
     *
     **********************************************************
     */
    public  boolean insertProvincia(int id, String name) {
        return insertProvincia(getConn(),id, name);
    }
    
    
    public boolean insertTienda(Tienda t) {
        return insertTienda(getConn(), t.getName(), t.getProvincia(), t.getCiudad());
    }

    public boolean insertCliente(Cliente c) {
        return insertCliente(getConn(), c.getName(), c.getApellido(), c.getEmail());
    }

    public boolean insertEmpleado(Empleado e) {
        return insertEmpleado(getConn(), e.getName(), e.getApellidos());
    }

    public boolean insertProducto(Producto p) {
        return insertProducto(getConn(), p.getName(), p.getPrice(), p.getDescription());
    }

    public boolean insertTiendaProducto(int id_Tienda, int id_Producto, int stock) {
        
        return insertTiendaProducto(getConn(), id_Tienda, id_Producto, stock);
    }

    public boolean insertTiendaProducto(int id_Tienda, int id_Producto) {
   
        return insertTiendaProducto(getConn(), id_Tienda, id_Producto);
    }

    public boolean insertTiendaEmpleado(int idTienda, int idEmpleado, float nHoras) {
        
        return insertTiendaEmpleado(getConn(), idTienda, idEmpleado, nHoras);
    }

    public boolean insertTiendaEmpleado(int idTienda, int idEmpleado) {
        return insertTiendaEmpleado(getConn(), idTienda, idEmpleado);
    }

    /**
     * ************************************************************
     * ========== UPDATE PRIVATE =================== = updateTiendaEmpleado -->
     * inserta el registro de numero de Horas = updateTiendaProducto --> inserta
     * el registro de stock    
     ***************************************************************
     */
    public boolean updateTiendaEmpleado(Tienda t, Empleado em, float nHoras) {
        return updateTiendaEmpleado(getConn(), t.getId(), em.getId(), nHoras);
    }

    public boolean updateTiendaEmpleado(int idt, int idem, float nHoras) {
        return updateTiendaEmpleado(getConn(), idt, idem, nHoras);
    }

    public boolean updateTiendaProducto(Tienda t, Producto p, int stock) {
        return updateTiendaProducto(getConn(), t.getId(), p.getId(), stock);
    }

    public boolean updateTiendaProducto(int idt, int idp, int stock) {
       return updateTiendaProducto(getConn(), idt, idp, stock);
    }

    public boolean insertCliente(Tienda t, Cliente c, int stock) throws SQLException {
       return updateTiendaProducto(getConn(), t.getId(), c.getId(), stock);
    }

    /**
     * ************************************************************
     * ========== INSERT PRIVATE =================== = insertTienda -> inserta
     * los registros del objeto Tienda. = insertCliente -> inserta los registros
     * del objeto Cliente. = insertEmpleado -> inserta los registros del objeto
     * Empleado. = insertProducto -> inserta los registros del objeto Producto.
     * = insertTiendaProducto -> inserta los registros del objeto Producto de
     * una Tienda. = insertTiendaEmpleado -> inserta los registros del objeto
     * Empleado de una Tienda.
     *
     **********************************************************
     */
    private boolean insertProvincia(Connection con,int id, String name) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO PROVINCIA (PROVINCIA_id, PROVINCIA_name) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println(" Provincia" + "[" + id + "," + name + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR EN INSERT {{ Provincia" + "[" + id + "," + name + "] }}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }
    private boolean insertTienda(Connection con, String name, String provincia, String ciudad) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA(TIENDA_name, TIENDA_provincia, TIENDA_ciudad) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setString(2, provincia);
            pstmt.setString(3, ciudad);
            pstmt.executeUpdate();
            System.out.println(" Tienda" + "[" + name + "," + provincia + "," + ciudad + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR EN INSERT {{ Tienda" + "[" + name + "," + provincia + "," + ciudad + "] }}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean insertCliente(Connection con, String name, String apellido, String email) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO CLIENTE(CLIENTE_name, CLIENTE_apellido, CLIENTE_email) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setString(2, apellido);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println(" Cliente" + "[" + name + "," + apellido + "," + email + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR EN INSERT {{ Cliente" + "[" + name + "," + apellido + "," + email + "]}}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean insertEmpleado(Connection con, String name, String apellido) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO EMPLEADO(EMPLEADO_name, EMPLEADO_apellido) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setString(2, apellido);
            pstmt.executeUpdate();
            System.out.println("Empleado" + "[" + name + "," + apellido + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " ERROR EN INSERT {{ Empleado" + "[" + name + "," + apellido + "]}}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean insertProducto(Connection con, String name, float price, String description) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO PRODUCTO(PRODUCTO_name, PRODUCTO_price, PRODUCTO_description) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setFloat(2, price);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
            System.out.println("Producto" + "[" + name + "," + price + "," + description + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " ERROR EN INSERT {{ Producto" + "[" + name + "," + price + "," + description + "] }}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean insertTiendaProducto(Connection con, int id_Tienda, int id_Producto, int stock) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_PRODUCTO(TIENDA_id, PRODUCTO_id, stock) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, id_Tienda);
            pstmt.setInt(2, id_Producto);
            pstmt.setInt(3, stock);
            pstmt.executeUpdate();
            System.out.println("Tienda ->> Producto" + "[" + stock + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR INSERT {{ Tienda ->> Producto" + "[" + stock + "] }}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean insertTiendaProducto(Connection con, int id_Tienda, int id_Producto) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_PRODUCTO(TIENDA_id, PRODUCTO_id, stock) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, id_Tienda);
            pstmt.setInt(2, id_Producto);
            pstmt.executeUpdate();
            System.out.println("Tienda ->> Producto" + "[" + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR INSERT {{ Tienda ->> Producto" + "[" + "] }}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean insertTiendaEmpleado(Connection con, int idTienda, int idEmpleado, float nHoras) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_EMPLEADO(TIENDA_id, EMPLEADO_id, nHoras) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, idTienda);
            pstmt.setInt(2, idEmpleado);
            pstmt.setFloat(3, nHoras);
            pstmt.executeUpdate();
            System.out.println("Tienda ->> Empleado" + "[" + nHoras + "] ");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR INSERT {{ Tienda ->> Empleado" + "[" + nHoras + "] }}");
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean insertTiendaEmpleado(Connection con, int idTienda, int idEmpleado) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_EMPLEADO(TIENDA_id, EMPLEADO_id) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, idTienda);
            pstmt.setInt(2, idEmpleado);
            pstmt.executeUpdate();
            System.out.println("Tienda ->> Empleado" + "[" + "]");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR INSERT {{ Tienda ->> Empleado" + "[" + "] }}"); return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    /**
     * ************************************************************
     * ========== DELETE PRIVATE =================== = deleteTienda --> Elimina
     * una Tienda. = deleteCliente --> Elimina un Cliente. = deleteEmpleado -->
     * Elimina un Empleado. = deleteProducto --> Elimina un Producto. =
     * deleteTiendaProducto --> Eliminar un Producto de una Tienda. =
     * deleteTiendaEmpleado --> Eliminar un Empleado de una Tienda.
     *
     ***************************************************************
     */
    private boolean deleteTienda(Connection con, String name) {
        try {
            String sql = "DELETE FROM TIENDA WHERE TIENDA_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println(" TIENDA [" + name + "] --> borrada con éxito");
        } catch (SQLException e) {
            System.err.println(" TIENDA [" + name + "] ERROR no se a podido borrar" + e.getMessage());
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean deleteCliente(Connection con, String name) {
        try {
            String sql = "DELETE FROM CLIENTE WHERE CLIENTE_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("CLIENTE [" + name + "] --> borrado con éxito");
        } catch (SQLException e) {
            System.err.println(" CLIENTE [" + name + "] ERROR no se a podido borrar" + e.getMessage());
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean deleteEmpleado(Connection con, String name) {
        try {
            String sql = "DELETE FROM EMPLEADO WHERE EMPLEADO_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("EMPLEADO [" + name + "] --> borrado con éxito");
        } catch (SQLException e) {
            System.err.println(" EMPLEADO [" + name + "] ERROR no se a podido borrar" + e.getMessage());
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean deleteProducto(Connection con, String name) {
        try {
            String sql = "DELETE FROM PRODUCTO WHERE PRODUCTO_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("PRODUCTO [" + name + "] --> borrado con éxito");
        } catch (SQLException e) {
            System.err.println(" PRODUCTO [" + name + "] ERROR no se a podido borrar" + e.getMessage());
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean deleteTiendaProducto(Connection con, int idTiend, int idProd) {
        try {
            String sql = "DELETE FROM TIENDA_PRODUCTO WHERE TIENDA_id = ? and PRODUCTO_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idTiend);
            pstmt.setInt(2, idProd);
            pstmt.executeUpdate();
            System.out.println("TIENDA_PRODUCTO borrada con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private boolean deleteTiendaEmpleado(Connection con, int idTiend, int idEmp) {
        try {
            String sql = "DELETE FROM TIENDA_EMPLEADO WHERE TIENDA_id = ? and EMPLEADO_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idTiend);
            pstmt.setInt(2, idEmp);
            pstmt.executeUpdate();
            System.out.println("TIENDA_EMPLEADO borrada con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage()); return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    /**
     * ************************************************************
     * ========== UPDATE PRIVATE =================== = updateTiendaEmpleado -->
     * inserta el registro de numero de Horas = updateTiendaProducto --> inserta
     * el registro de stock    
     ***************************************************************
     */

    private boolean updateTiendaEmpleado(Connection con, int idT, int idE, float nHoras) {
        try {
            String sql = " UPDATE TIENDA_EMPLEADO SET nHoras = ? "
                    + " WHERE TIENDA_id = ? AND EMPLEADO_id = ? ";
            PreparedStatement pstmt = con.prepareStatement(sql);
           
            pstmt.setFloat(1, nHoras);
            
            pstmt.setInt(2, idT);
            
            pstmt.setInt(3, idE);
            
            
            pstmt.executeUpdate();
          
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
             return false;
        } catch (Exception ee){
            return false;
        } finally {
            DB_driver.finishDB();
        }
        return true;
    }

    private static boolean updateTiendaProducto(Connection con, int idT, int idP, int stock) {

        System.out.println("updateTiendaEmpleado IN DB  intento añadir " + stock);
        String sql = "";
        try {
            sql = " UPDATE TIENDA_PRODUCTO SET stock =? "
                    + " WHERE TIENDA_id =? and PRODUCTO_id =? ;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setFloat(1, stock);
            pstmt.setInt(2, idT);
            pstmt.setInt(3, idP);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Nome da persoa actualizada con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "sql -->" + sql);
            return false;
        } catch (Exception ee){
            return false;
        }finally {
            DB_driver.finishDB();
            
        }
        return true;
    }

}//fin de DB_driver
