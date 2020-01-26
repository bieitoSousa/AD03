/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bieitosousa.ad03_db.Data;
import com.bieitosousa.ad03_db.Data.Empleado;
import com.bieitosousa.ad03_db.Data.Tienda;
import com.bieitosousa.ad03_db.Data.Producto;
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
    public static Connection  con = null;
    public static DB_driver  db = null;
    private static String[] DBsql = {
		"CREATE TABLE IF NOT EXISTS TIENDA (\n"
		 +"   TIENDA_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
		 +"   TIENDA_name TEXT NOT NULL UNIQUE,\n"
		 +"   TIENDA_provincia TEXT NOT NULL,\n"
		 +"   TIENDA_ciudad TEXT NOT NULL\n" 
		 +");"
		 ,
		"CREATE TABLE IF NOT EXISTS CLIENTE (\n"
		 +"    CLIENTE_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" 
		 +"    CLIENTE_name TEXT NOT NULL,\n"
		 +"    CLIENTE_apellido TEXT NOT NULL,\n" 
		 +"    CLIENTE_email TEXT NOT NULL UNIQUE\n"
		 +");"
		 ,
		 
		"CREATE TABLE IF NOT EXISTS EMPLEADO (\n"
		 +"    EMPLEADO_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
		 +"    EMPLEADO_name TEXT NOT NULL,\n"
		 +"    EMPLEADO_apellido TEXT NOT NULL\n"
		 +");"
		,
                
		 "CREATE TABLE IF NOT EXISTS PRODUCTO (\n"
		 +"    PRODUCTO_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
		 +"    PRODUCTO_name TEXT NOT NULL,\n"
		 +"	   PRODUCTO_price REAL NOT NULL UNIQUE,\n"
		 +"    PRODUCTO_description TEXT NOT NULL UNIQUE\n"
		 +");"
		,

		"CREATE TABLE IF NOT EXISTS TIENDA_EMPLEADO(\n"
		 +"   TIENDA_id INTEGER,\n"
		 +"   EMPLEADO_id INTEGER,\n"
		 +"   nHoras REAL,\n"
		 +"   PRIMARY KEY (TIENDA_id, EMPLEADO_id),\n"
		 +"   FOREIGN KEY (TIENDA_id) \n"
		 +"      REFERENCES TIENDA (TIENDA_id) \n"
		 +"         ON DELETE CASCADE \n"
		 +"         ON UPDATE NO ACTION,\n"
		 +"   FOREIGN KEY (EMPLEADO_id) \n"
		 +"      REFERENCES EMPLEADO (EMPLEADO_id) \n"
		 +"         ON DELETE CASCADE \n"
		 +"         ON UPDATE NO ACTION\n"
		 +");"
		,

		"CREATE TABLE IF NOT EXISTS TIENDA_PRODUCTO(\n"
		 +"   TIENDA_id INTEGER,\n"
		 +"   PRODUCTO_id INTEGER,\n"
                +"   stock INTEGER,\n"
		 +"   PRIMARY KEY (TIENDA_id, PRODUCTO_id),\n"
		 +"   FOREIGN KEY (TIENDA_id) \n"
		 +"      REFERENCES TIENDA (TIENDA_id) \n"
		 +"         ON DELETE CASCADE \n"
		 +"         ON UPDATE NO ACTION,\n"
		 +"   FOREIGN KEY (PRODUCTO_id) \n"
		 +"      REFERENCES PRODUCTO (PRODUCTO_id) \n"
		 +"         ON DELETE CASCADE \n"
		 +"         ON UPDATE NO ACTION\n"
		 +");"
 
	};
	
    public void DB_driver(){
    
    }
       public static void main(String[] args){
      
    }
    
/********************************
 * =     METODOS STATIC     =  
*********************************/  

    /**************************************************************
     *   ==========      Connexion SQLITE  =================== 
     *  = getConn   --> si no exite conexion la crea (startDB()).
     *  = finishDB  --> elimina la conexion con DB [desconnetDatabase(con)]
     *  = startDB   --> define un DB y una conxion [connectDatabase(db)]
     *  = connectDatabase --> se conecta a una DB y devulve la conexion
     *  = desconnetDatabase(con) --> se desconecta de DB y elimna la conexion
     ***********************************************************/      

    public static Connection getConn() {
        if (con == null) {
            startDB();
        }
        return con;
    }
    
    public static void finishDB(){
     desconnetDatabase(con);
      con=null;
    }
    
    private static  void startDB(){
        String db = "novaBaseDeDatos.db";
         con = connectDatabase(db); 
    }
    
     //Creamos a conexión a base de datos
    private static Connection connectDatabase(String filename){
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
            //System.out.println("Conexión realizada con éxito");
            return connection;
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    //Este método desconectase dunha base de datos en SQLLite a que se lle pasa a conexión
    private static void desconnetDatabase(Connection connection){
        try{
            if(connection != null){
                connection.close();
               //System.out.println("Desconexión realizada con éxito");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************
     *   ==========      INSTANCIAR DB_driver  =================== 
     *  = instance          --> si no exite instancia el driver.
     *                      |-> inicia la conexion
     *                      |-> evalua si esta creada la base de datos
     *                      |__ finaliza la conexion
     *  = getData   --> evalua si esta creada la base de datos (dbExist)
     *                      => creada : no hace nada
     *                      => no creada : crea las tablas (createTable)
     *  = dbExist   --> consulta si hay tablas creadas en la DB.
     *  = createTable       -->  Crea las tablas de la DB [Si no existen].
     *
     ***********************************************************/    
    protected static DB_driver instance() {
        if (db == null){
            db = new DB_driver(); 
            getData();
            finishDB();
        }
    return db;
    }
    
    private static void getData(){
        if(!dbExist()) {// si no esta creada la base de datos la creamos   
          for(int i=0; i < DBsql.length;i++){
                            createTable(getConn(),DBsql[i]);
                    }
        }
    }
     private static boolean dbExist(){
         boolean exist = false;
         try{
            Statement statement = getConn().createStatement();
            ResultSet rs = statement.executeQuery("SELECT name, sql FROM sqlite_master WHERE type='table'");
            if(rs.next()){
                exist = true;
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
            DB_driver.finishDB();
         }  
        return exist;
     }
     private static void createTable(Connection con, String sql){
        try{
           /* String sql = "CREATE TABLE IF NOT EXISTS person (\n" +
                    "id integer PRIMARY KEY,\n"+
                    "nome text NOT NULL\n"+
                    ");";
            */
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ " SQL FALLIDO {{______\n "+sql+"\n______}}");
        }
    }
    
/********************************
 * =     METODOS PUBLIC    =  
*********************************/  

    /**************************************************************
     *   ==========      DELETE PUBLIC  =================== 
     *  = deleteTienda          --> llama metodo privado -> Elimina una Tienda.
     *  = deleteCliente         --> llama metodo privado -> Elimina un Cliente.
     *  = deleteEmpleado        --> llama metodo privado -> Elimina un Empleado.
     *  = deleteProducto        --> llama metodo privado -> Elimina un Producto.
     *  = deleteTiendaProducto  --> llama metodo privado -> Eliminar un Producto de una Tienda.
     *  = deleteTiendaEmpleado  --> llama metodo privado -> Eliminar un Empleado de una Tienda.
     
     ***********************************************************/
    
    public void  deleteTienda(Tienda t){
       deleteTienda(getConn(),t.getName()); 
    }
    public void  deleteProducto(Producto p){
       deleteProducto(getConn(),p.getName()); 
    }
    public void  deleteEmpleado (Empleado em){
       deleteEmpleado(getConn(),em.getName()); 
    }
    public void  deleteCliente (Cliente cli){
       deleteCliente(getConn(),cli.getName()); 
    }
    public void  deleteTiendaProducto(Tienda t, Producto p){
       deleteTiendaProducto(getConn(),t.getId(), p.getId()); 
    }
    public void  deleteTiendaEmpleado(Tienda t, Empleado em){
       deleteTiendaEmpleado(getConn(),t.getId(),em.getId()); 
    }
    
    /**************************************************************
     *   ==========      INSERT PUBLIC  =================== 
     *  = insertTienda          --> llama metodo privado -> inserta los registros del objeto Tienda.
     *  = insertCliente         --> llama metodo privado -> inserta los registros del objeto Cliente.
     *  = insertEmpleado        --> llama metodo privado -> inserta los registros del objeto Empleado.
     *  = insertProducto        --> llama metodo privado -> inserta los registros del objeto Producto.
     *  = insertTiendaProducto  --> llama metodo privado -> inserta los registros del objeto Producto de una Tienda.
     *  = insertTiendaEmpleado  --> llama metodo privado -> inserta los registros del objeto Empleado de una Tienda.
     
     ***********************************************************/
    
    public void insertTienda (Tienda t){
    insertTienda(getConn(), t.getName(),t.getProvincia(),t.getCiudad());
    }
    public void  insertCliente(Cliente c){
        insertCliente(getConn(), c.getName(),c.getApellido(),c.getEmail());
    }

    public void insertEmpleado(Empleado e){
        insertEmpleado(getConn(), e.getName(),e.getApellidos());
    }

    public void insertProducto (Producto p){
        insertProducto(getConn() , p.getName(),p.getPrice(),p.getDescription());
    }
    public void insertTiendaProducto(int id_Tienda, int id_Producto,int stock ){
    Connection con = getConn();    
    insertTiendaProducto(con, id_Tienda,  id_Producto, stock );
    }
    
    public void insertTiendaProducto(int id_Tienda, int id_Producto ){
    Connection con = getConn();    
    insertTiendaProducto(con, id_Tienda,  id_Producto );
    }
    public void insertTiendaEmpleado( int idTienda, int idEmpleado, float nHoras ){
    Connection con = getConn();    
    insertTiendaEmpleado(con, idTienda, idEmpleado,  nHoras );
    }
    public void insertTiendaEmpleado( int idTienda, int idEmpleado ){
    Connection con = getConn();    
    insertTiendaEmpleado(con, idTienda, idEmpleado );
    }

        /**************************************************************
     *   ==========      UPDATE PRIVATE   =================== 
     *  = updateTiendaEmpleado   --> inserta el registro de numero de Horas
     *  = updateTiendaProducto   --> inserta el registro de stock    
     ****************************************************************/  
    public void updateTiendaEmpleado (Tienda t, Empleado em , float nHoras){
        updateTiendaEmpleado(con, t.getId(),em.getId(),nHoras);
    }
    public void updateTiendaEmpleado (int idt, int idem , float nHoras){
        updateTiendaEmpleado(con, idt, idem ,nHoras);
    }
    public void updateTiendaProducto (Tienda t, Producto p , int stock){
        updateTiendaProducto(con, t.getId(),p.getId(),stock);
    }
    public void updateTiendaProducto (int idt, int idp , int stock){
        updateTiendaProducto(con, idt, idp ,stock);
    }
    public void  insertCliente(Tienda t, Cliente c, int stock){
        updateTiendaProducto(con, t.getId(),c.getId(),stock);
    }

    /**************************************************************
     *   ==========      INSERT PRIVATE  =================== 
     *  = insertTienda          -> inserta los registros del objeto Tienda.
     *  = insertCliente         -> inserta los registros del objeto Cliente.
     *  = insertEmpleado        -> inserta los registros del objeto Empleado.
     *  = insertProducto        -> inserta los registros del objeto Producto.
     *  = insertTiendaProducto  -> inserta los registros del objeto Producto de una Tienda.
     *  = insertTiendaEmpleado  -> inserta los registros del objeto Empleado de una Tienda.
     
     ***********************************************************/
    
    private  void insertTienda(Connection con, String name, String provincia, String ciudad){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA(TIENDA_name, TIENDA_provincia, TIENDA_ciudad) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
			pstmt.setString(2, provincia);
			pstmt.setString(3, ciudad);
            pstmt.executeUpdate();
            System.out.println(" Tienda"+"["+ name+","+ provincia +","+ ciudad +"]" );
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ "ERROR EN INSERT {{ Tienda"+"["+ name+","+ provincia +","+ ciudad +"] }}");
        }finally{
            DB_driver.finishDB();
         }  
    }
    
    private  void insertCliente(Connection con, String name, String apellido, String email){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO CLIENTE(CLIENTE_name, CLIENTE_apellido, CLIENTE_email) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
			pstmt.setString(2, apellido);
			pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println(" Cliente"+"["+ name+","+ apellido +","+ email +"]" );
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ "ERROR EN INSERT {{ Cliente"+"["+ name+","+ apellido +","+ email +"]}}");
        }finally{
            DB_driver.finishDB();
         }  
    }
    
    private void insertEmpleado(Connection con, String name, String apellido){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO EMPLEADO(EMPLEADO_name, EMPLEADO_apellido) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
			pstmt.setString(2, apellido);
            pstmt.executeUpdate();
            System.out.println("Empleado"+"["+ name+","+ apellido+ "]");
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ " ERROR EN INSERT {{ Empleado"+"["+ name+","+ apellido+ "]}}");
        }finally{
            DB_driver.finishDB();
         }  
    }
	
    private  void insertProducto(Connection con, String name, float price, String description){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO PRODUCTO(PRODUCTO_name, PRODUCTO_price, PRODUCTO_description) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
			pstmt.setFloat(2, price);
            pstmt.setString(3, description);
			pstmt.executeUpdate();
            System.out.println("Producto"+"["+ name+","+ price+","+ description + "]");
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ " ERROR EN INSERT {{ Producto"+"["+ name+","+ price+","+ description + "] }}");
        }finally{
            DB_driver.finishDB();
         }  
    }
    
    private  void insertTiendaProducto(Connection con, int id_Tienda, int id_Producto,int stock ){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_PRODUCTO(TIENDA_id, PRODUCTO_id, stock) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, id_Tienda);
	    pstmt.setInt(2, id_Producto);
            pstmt.setInt(3, stock);
			pstmt.executeUpdate();
            System.out.println("Tienda ->> Producto"+"["+ stock + "]");
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ "ERROR INSERT {{ Tienda ->> Producto"+"["+ stock + "] }}");
        }finally{
            DB_driver.finishDB();
         }  
    }
    
    private  void insertTiendaProducto(Connection con, int id_Tienda, int id_Producto){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_PRODUCTO(TIENDA_id, PRODUCTO_id, stock) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, id_Tienda);
	    pstmt.setInt(2, id_Producto);
			pstmt.executeUpdate();
            System.out.println("Tienda ->> Producto"+"["+ "]");
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ "ERROR INSERT {{ Tienda ->> Producto"+"["+  "] }}");
        }finally{
            DB_driver.finishDB();
         }  
    }
    
    private  void insertTiendaEmpleado( Connection con, int idTienda, int idEmpleado, float nHoras){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_EMPLEADO(TIENDA_id, EMPLEADO_id, nHoras) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, idTienda);
			pstmt.setInt(2, idEmpleado);
            pstmt.setFloat(3, nHoras);
			pstmt.executeUpdate();
            System.out.println("Tienda ->> Empleado"+"["+ nHoras + "] ");
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ "ERROR INSERT {{ Tienda ->> Empleado"+"["+ nHoras + "] }}");
        }finally{
            DB_driver.finishDB();
         }  
    }

    private  void insertTiendaEmpleado( Connection con, int idTienda, int idEmpleado){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO TIENDA_EMPLEADO(TIENDA_id, EMPLEADO_id) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, idTienda);
			pstmt.setInt(2, idEmpleado);
			pstmt.executeUpdate();
            System.out.println("Tienda ->> Empleado"+"["+ "]");
        }
        catch(SQLException e){
            System.out.println(e.getMessage()+ "ERROR INSERT {{ Tienda ->> Empleado"+"["+ "] }}");
       }finally{
            DB_driver.finishDB();
         }  
    }

    /**************************************************************
      *   ==========      DELETE PRIVATE   =================== 
     *  = deleteTienda          --> Elimina una Tienda.
     *  = deleteCliente         --> Elimina un Cliente.
     *  = deleteEmpleado        --> Elimina un Empleado.
     *  = deleteProducto        --> Elimina un Producto.
     *  = deleteTiendaProducto  --> Eliminar un Producto de una Tienda.
     *  = deleteTiendaEmpleado  --> Eliminar un Empleado de una Tienda.
     
     ****************************************************************/

    private void deleteTienda(Connection con, String name) {
        try{
            String sql = "DELETE FROM TIENDA WHERE TIENDA_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println(" TIENDA ["+ name + "] --> borrada con éxito");
        }
        catch(SQLException e){
            System.err.println( " TIENDA ["+ name + "] ERROR no se a podido borrar" + e.getMessage());
        }finally{
            DB_driver.finishDB();
         }  
    }

    private void deleteCliente(Connection con, String name) {
        try{
            String sql = "DELETE FROM CLIENTE WHERE CLIENTE_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("CLIENTE ["+ name + "] --> borrado con éxito");
        }
        catch(SQLException e){
            System.err.println(" CLIENTE ["+ name + "] ERROR no se a podido borrar" +e.getMessage());
        }finally{
            DB_driver.finishDB();
         }    
    }

    private void deleteEmpleado(Connection con, String name) {
        try{
            String sql = "DELETE FROM EMPLEADO WHERE EMPLEADO_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("EMPLEADO ["+ name + "] --> borrado con éxito");
        }
        catch(SQLException e){
            System.err.println(" EMPLEADO ["+ name + "] ERROR no se a podido borrar" +e.getMessage());
        }finally{
            DB_driver.finishDB();
         }   
    }

    private void deleteProducto(Connection con, String name) {
         try{
            String sql = "DELETE FROM PRODUCTO WHERE PRODUCTO_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("PRODUCTO ["+ name + "] --> borrado con éxito");
        }
        catch(SQLException e){
            System.err.println(" PRODUCTO ["+ name + "] ERROR no se a podido borrar" +e.getMessage());
        }finally{
            DB_driver.finishDB();
         }    
    }

    private void deleteTiendaProducto(Connection con, int idTiend, int idProd) {
        try{
            String sql = "DELETE FROM TIENDA_PRODUCTO WHERE TIENDA_id = ? and PRODUCTO_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idTiend);
            pstmt.setInt(2, idProd);
            pstmt.executeUpdate();
            System.out.println("TIENDA_PRODUCTO borrada con éxito");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
            DB_driver.finishDB();
         }  
    }

    private void deleteTiendaEmpleado(Connection con, int idTiend, int idEmp) {
         try{
            String sql = "DELETE FROM TIENDA_EMPLEADO WHERE TIENDA_id = ? and EMPLEADO_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idTiend);
            pstmt.setInt(2, idEmp);
            pstmt.executeUpdate();
            System.out.println("TIENDA_EMPLEADO borrada con éxito");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
            DB_driver.finishDB();
         }
    }
    /**************************************************************
      *   ==========      UPDATE PRIVATE   =================== 
     *  = updateTiendaEmpleado   --> inserta el registro de numero de Horas
     *  = updateTiendaProducto   --> inserta el registro de stock    
     ****************************************************************/    
    
    private static void updateTiendaEmpleado(Connection con,int idT,int idE,float nHoras){
        System.out.println("updateTiendaEmpleado IN DB  intento añadir " + nHoras );
        String sql ="";
        try{
            sql = "UPDATE TIENDA_EMPLEADO SET nHoras = ? "
                + " WHERE TIENDA_id = ? AND EMPLEADO_id = ? ";
            System.out.println("sql -->>"+sql);
             System.out.println("__0");
            PreparedStatement pstmt = con.prepareStatement(sql);
            System.out.println("__1");
            pstmt.setFloat(1, nHoras);
            System.out.println("__2");
            pstmt.setInt(2, idT);
            System.out.println("__3");
            pstmt.setInt(3, idE);
            System.out.println("__4");
            System.out.println("hasta aqui perfecto ?¿");
            pstmt.executeUpdate();
            System.out.println("Nome da persoa actualizada con éxito");
        }
        catch(SQLException e){
            System.err.println(e.getMessage()+"sql -->>"+ sql);
        }finally{
            DB_driver.finishDB();
         }  
    }
    private static void updateTiendaProducto(Connection con,int idT,int idP, int stock){
        
        System.out.println("updateTiendaEmpleado IN DB  intento añadir " + stock );
        String sql="";
        try{
             sql = "UPDATE TIENDA_PRODUCTO SET stock =? "
                + " WHERE TIENDA_id =? and PRODUCTO_id =? ;";
              String sql2 = "UPDATE TIENDA_PRODUCTO SET stock = "+stock
                + " WHERE TIENDA_id = "+idT+" and PRODUCTO_id = "+idP+" ;";
             System.out.println("sql -->>"+sql2);
             System.out.println("__0");
             PreparedStatement pstmt = con.prepareStatement(sql);
            System.out.println("__1");
             pstmt.setFloat(1, stock);
            System.out.println("__2");
            pstmt.setInt(2, idT);
            pstmt.setInt(3, idP);
            pstmt.executeUpdate();
            System.out.println("Nome da persoa actualizada con éxito");
        }
        catch(SQLException e){
            System.err.println(e.getMessage() +"sql -->"+ sql );
       }finally{
            DB_driver.finishDB();
         }  
    }
}//fin de DB_driver