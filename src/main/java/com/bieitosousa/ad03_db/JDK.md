# Conetor JDBC para JAVA de SQLite

[TOCM]

[TOC]

## Crear unha base de datos

```java
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    
    
    public static void main(String[] args){
        String db = "novaBaseDeDatos.db";
        createDatabase(db);
    }
    
    /*
    Este método crea unha nova base de datos en SQLLite co nome que se lle pasa como argunmento
    */
    private static void createDatabase(String filename){
        String databaseFile = "jdbc:sqlite:/home/" + filename;
        
        try{
            Connection connection = DriverManager.getConnection(databaseFile);
            if(connection != null){
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("A base de datos foi creada");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
```

## Conectarse e desconectarse da base de datos
Para poder utilizar a base de datos, primeiro temos que conectarnos a ela. Unha vez finalizadas as operacións na base de datos hai que cerrala conexión.
```java
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    
    
    public static void main(String[] args){
        String db = "novaBaseDeDatos.db";
        Connection con = connectDatabase(db);
        desconnetDatabase(con);
    }
    
    /*
    Esta clase conéctase a base de datos SQLLite que se lle pasa o nome da base de datos
    */
    private static Connection connectDatabase(String filename){
        Connection connection = null;
        try
        {
            //Creamos a conexión a base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:/home/" + filename);
            System.out.println("Conexión realizada con éxito");
            return connection;
             
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    /*
    Este método desconectase dunha base de datos en SQLLite a que se lle pasa a conexión
    */
    private static void desconnetDatabase(Connection connection){
        try{
            if(connection != null){
                connection.close();
                System.out.println("Desconexión realizada con éxito");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
```


## Crear unha táboa
Co seguinte código de exemplo pódese crear unha táboa. neste caso na base de datos que xa temos creado antes, creamos a tabla person.
```java 

    /*
    Método que crea a tabla persona nunha base de datos persoa  
     */
    private static void createTablePerson(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS person (\n" +
                    "id integer PRIMARY KEY,\n"+
                    "nome text NOT NULL\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Táboa persona creada con éxito");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
```


## Insertar datos
Imos proceder a engadir unha persoa a táboa creada no paso anterior.
```java
    
    /*
    Este método isnerta unha nova persoa na tboa Persoa
    */
    private static void insertPerson(Connection con, String nome){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO person(nome) VALUES(?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, nome);
            pstmt.executeUpdate();
            System.out.println("Persoa engadida con éxito");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
```
## Realizar unha consulta a base de datos
Imos facer unha consulta a base de datos. Para iso imos imprimir tódalas persoas que hai na base de datos.
```java
    
    /*
    Método que imprime tódalas persoas
    */
    private static void printPeople(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from person");
            
            while(rs.next()){
                //imprimimos o nome de todolas persoas
                System.out.println("Nome = " + rs.getString("nome"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
}


```
## Realizar unha actualización de datos
Imos ver como se pode facer un UPDATE en SQL utilizando SQLLite. neste caso imos cambiar o nome vello dunha persoa por un novo.
```java
/*
    Método que actualiza o nome dunha persoa
    */
    private static void updateNamePerson(Connection con,String oldName,String newName){
        try{
            String sql = "UPDATE person SET nome = ? "
                + "WHERE nome = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, oldName);
            pstmt.executeUpdate();
            System.out.println("Nome da persoa actualizada con éxito");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
```
## Borrar datos
Neste caso imos realizar un DELETE: Imos borrar unha persoa segundo o seu nome.
```java
 /*
    Este método borra unha persoa da base de datos segundo o seu nome
    */
    private static void deletePerson(Connection con, String nome){
        try{
            String sql = "DELETE FROM person WHERE nome = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.executeUpdate();
            System.out.println("Persoa borrada con éxito");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
```
## Transaccións
Imos comezar a ver as transaccións. Tedes un exemplo no seguinte enlace:
Exemplo Transaccións
Imos ver os aspectos clave. O funcionamento é moi parecido ao visto en exemplos anteriores (insertar,borrar e actualizar datos). Só temos que ter en conta aqueles aspectos que teñen que ver cás transaccións.

O primeiro e deshabilitar o COMMIT automático. Cada vez que se facía unha operación non había que indicar que se realizara o COMMIT, facíase por defecto. Polo tanto, se nos necesitamos facelo manualmente xusto despois de conectarnos a base de datos indicámolo:
```
conn.setAutoCommit(false);
```
Cando preparamos o Statement podemos indicar se queremos obter as claves xeradas nun INSERT por exemplo. Ata o de agora non o fixemos. Para iso utilizamos un argumento máis como se ve no exemplo:
```
pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
```
Tampouco vimos anteriormente que cando se realiza un executeUpdate() (inserción ou actualización de datos) podemos obter o número de filas afectadas. Isto pode ser necesario nalgún caso. Se queremos obtelas podemos facelo do seguinte:
```
int filasAfectadas = pstmt.executeUpdate();
```
Vimos como pedir que se nos devolvan as claves nun executeUpdate(). Tras o executeUpdate() podemos obtelas do obxecto PreparedStatement:
```
keys = pstmt.getGeneratedKeys();
if (keys.next()) {
    id = keys.getInt(1);
}
```
Para cada operación que se realiza necesitamos utilizar un obxecto PreparedStatement* diferente.
Unha vez realizadas as operacións necesarias, facemos o COMMIT do seguinte xeito:
```
conn.commit();
```
Se algo saíu mal, podemos facer un ROLLBACK da seguinte forma:
```
conn.rollback();
```
Ejemplo completo :
[TRANSICIONES](https://www.sqlitetutorial.net/sqlite-java/transaction/"TRANSICIONES")
```java
public void addInventory(String material, int warehouseId, double qty) {
        // SQL for creating a new material
        String sqlMaterial = "INSERT INTO materials(description) VALUES(?)";
        
        // SQL for posting inventory
        String sqlInventory = "INSERT INTO inventory(warehouse_id,material_id,qty)"
                + "VALUES(?,?,?)";
 
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null, pstmt2 = null;
        
        try {
            // connect to the database
            conn = this.connect();
            if(conn == null)
                return;
            
            // set auto-commit mode to false
            conn.setAutoCommit(false);
            
            // 1. insert a new material
            pstmt1 = conn.prepareStatement(sqlMaterial,
                    Statement.RETURN_GENERATED_KEYS);
 
            pstmt1.setString(1, material);
            int rowAffected = pstmt1.executeUpdate();
 
            // get the material id
            rs = pstmt1.getGeneratedKeys();
            int materialId = 0;
            if (rs.next()) {
                materialId = rs.getInt(1);
            }
 
            if (rowAffected != 1) {
                conn.rollback();
            }
            // 2. insert the inventory
            pstmt2 = conn.prepareStatement(sqlInventory);
            pstmt2.setInt(1, warehouseId);
            pstmt2.setInt(2, materialId);
            pstmt2.setDouble(3, qty);
            // 
            pstmt2.executeUpdate();
            // commit work
            conn.commit();
 
        } catch (SQLException e1) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt1 != null) {
                    pstmt1.close();
                }
                if (pstmt2 != null) {
                    pstmt2.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }
```