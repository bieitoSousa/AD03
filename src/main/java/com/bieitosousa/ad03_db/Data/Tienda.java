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

import com.bieitosousa.ad03_db.Data.Producto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.HashMap;

/**
 *
 * @author bieito
 */
public class Tienda  {
    private HashMap<String, Producto> mapProd = new HashMap<String, Producto>();
    private HashMap<String, Empleado> mapEmp = new HashMap<String, Empleado>(); 
    
    private Franquicia f = null;
    private int id =-1;
    private String name;
    private String provincia;
    private String ciudad;
    private boolean opStock = true;
    private boolean opHoras = true;
   

    public Tienda( String name, String provincia, String ciudad) {
        super();
        this.name = name;
        this.provincia = provincia;
        this.ciudad = ciudad;
    }
    
 /***    ... METODOS PUBLICOS ...    ***/
    
     /**************************************************************
     * METODOS GET/SET atributos del constructor

     ***************************************************************/
    public Franquicia getFranquicia(){
        if (f == null){
            f=Franquicia.getInstance();
//        }else if (!f.mapTienda.containsKey(this.name)){
//            f.addTienda(this);
       }
    return f;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
     /**************************************************************
     * METODOS toString

     ***************************************************************/
    @Override
    public String toString() {
        return "Tienda{" + "mapProd=" + mapProd + ", mapEmp=" + mapEmp + ", id=" + id + ", name=" + name + ", provincia=" + provincia + ", ciudad=" + ciudad + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tienda other = (Tienda) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        if (!Objects.equals(this.ciudad, other.ciudad)) {
            return false;
        }
        return true;
    }

    /**************************************************************
     * METODOS GET
     # evalua super.op{name} : determina si hubo operaciones de escritura en la DB 
     # true : Carga los datos de la DB.
     # false : Carga los datos de la memoria.
     *  = getMapEmp     Devuelve los empleados de la tienda
     *  = getMapProd    Devuelve los productos de la tienda 
     *  = getId     Recupera el id de la tienda
     ****************************************************************/
    
    public HashMap<String, Producto> getMapProd() {
        if(getFranquicia().opProd){
        cargarProductos();
        }
        return mapProd;
    }
    
    public HashMap<String, Empleado> getMapEmp() {
        if(getFranquicia().opEmp){
        cargarEmpleados();
        }
        return mapEmp;
    }
    
        public int getId() {
        if (id==-1){
        cargarId();
        }
        return id;
    }

     /**************************************************************
     * METODOS SET
     # guarda los datos en memoria
     *  = setMapEmp
     *  = setMapProd
         *
     ****************************************************************/
    public void setMapProd(HashMap<String, Producto> mapProd) {
        this.mapProd = mapProd;
    }

    public void setMapEmp(HashMap<String, Empleado> mapEmp) {
        this.mapEmp = mapEmp;
    }
    
     /**************************************************************
     * METODOS ADD
     # llama a un metodo privado para inserta  los registros datos en la DB
     *  = addProducto => inserta Producto_id Tienda_id 
     *  = addStock    => inserta Producto_id Tienda_id stock
     *  = addHoras    => inserta Producto_id Empleado_id nHoras
     # En el metodo privado se determina op{name} = true 
     *      se ha escrito en la DB 
     * @param p
     ****************************************************************/    
    

    public void addProducto( Producto p ){
    if (getFranquicia().getMapProd().get(p.getName())!= null){
        if(p.getStock(this)==-1){
        insertTiendaProducto(p,0);
        }else{System.out.println("El producto ya a sido creado Modifiquelo");}
    }else{
       System.out.println("Producto "+p.getName()+" No es parte de la Franquicia");
       }
    }  

    public void addProducto( String name ){
    Producto p = getFranquicia().getMapProd().get(name);
        if (p!= null){
            if(p.getStock(this)==-1){
            insertTiendaProducto(p,0);
            }else{System.out.println("El producto ya a sido creado Modifiquelo");
            }
        }else{
           System.out.println("Producto "+name+" No es parte de la Franquicia");
           }
    } 
      
 
    public void addEmpleado( Empleado em ){
     if (getFranquicia().getMapEmp().get(em.getName())!= null){
         if(em.getnHoras(this)==(float)-1){
         insertTiendaEmpleado(em,0);
         }else{
         System.out.println("El Empleado ya a sido creado Modifiquelo");
         }
        }else{
               System.out.println("Empleado "+em.getName()+" No es parte de la Franquicia");
           }
    }
    
    public void addEmpleado( String name ){
     Empleado em = getFranquicia().getMapEmp().get(name);
     if (em != null){
         if(em.getnHoras(this)==(float)-1){
         insertTiendaEmpleado(em,0);
         }else{
         System.out.println("El Empleado ya a sido creado Modifiquelo");
         }
        }else{
               System.out.println("Empleado "+name+" No es parte de la Franquicia");
           }
    }
    
    public void addHoras(  Empleado em, float nHoras) {
        if (getMapEmp().get(em.getName())!= null){
          if(em.getnHoras(this)>0 && nHoras >(float)0){
            nHoras = nHoras + em.getnHoras(this);
            updateTiendaEmpleado( em,  nHoras);
          }else{System.out.println("El empleado NO HA SIDO CREEADO o las horas deven de ser > a 0  ");}
        }else{
               System.out.println("Empleado "+em.getName()+" No es parte de la Franquicia");
           }
    }  
    public void addHoras(  String name, float nHoras) {
        Empleado em = getFranquicia().mapEmp.get(name);
        if (em != null){
           if(em.getnHoras(this)>=0 && nHoras > (float)0){
            nHoras = nHoras + em.getnHoras(this);
            updateTiendaEmpleado( em,  nHoras);
          }else{System.out.println("El empleado NO HA SIDO CREEADO o las horas deven de ser > a 0 ");}
        }else{
               System.out.println("Empleado "+name+" No es parte de la Franquicia");
           }
    }  
    public void addStock(  Producto p, int stock) {
        if (getMapProd().get(p.getName())!= null){
            if(p.getStock(this)>=(float)0 && stock > (float)0){ 
                stock = stock + p.getStock(this);    
                updateTiendaProducto(p,stock);
            }else{System.out.println("El producto no ha sido creado o el stock deve ser > que 0");}
       }else{
           System.out.println("Producto "+p.getName()+" No es parte de la Franquicia");
       }
    } 
    public void addStock(  String name, int stock) {
        Producto p = getFranquicia().mapProd.get(name);
        if (p != null){
            if(p.getStock(this)>=(float)0 && stock > (float)0){ 
                    stock = stock + p.getStock(this);    
                    updateTiendaProducto(p,stock);
                }else{System.out.println("El producto no ha sido creado o el stock deve ser > que 0");}

       }else{
           System.out.println("Producto "+name+" No es parte de la Franquicia");
       }
    } 
        
         /**************************************************************
     * METODOS DELETE
     # llama a un metodo privado para Eliminar los registros datos en la DB
     *  = delProducto => elimina Producto_id Tienda_id  elimina el stock y el producto
     *  = delEmpleado    => elimina Producto_id Tienda_id stock elimna las horas del trabajador
     # En el metodo privado se determina op{name} = true 
     *      se ha escrito en la DB 
     * @param p
     ****************************************************************/ 
    
    
 
    public void delProducto( Producto p ){
    if (getFranquicia().mapProd.get(p.getName())!= null){
        deleteTiendaProducto(this,p);
    }else{
       System.out.println("Producto "+p.getName()+" No es parte de la Franquicia");
       }
    }  

    public void delProducto( String name ){
    Producto p = getFranquicia().mapProd.get(name);
        if (p!= null){
           deleteTiendaProducto(this,p);
        }else{
           System.out.println("Producto "+p.getName()+" No es parte de la Franquicia");
           }
    } 
      
 
    public void delEmpleado( Empleado em ){
     if (getFranquicia().mapEmp.get(em.getName())!= null){
         deleteTiendaEmpleado(this,em);
        }else{
               System.out.println("Empleado "+em.getName()+" No es parte de la Franquicia");
           }
    }
    
    public void delEmpleado( String name ){
     Empleado em = getFranquicia().mapEmp.get(name);
     if (em != null){
         deleteTiendaEmpleado(this,em);
        }else{
               System.out.println("Empleado "+em.getName()+" No es parte de la Franquicia");
           }
    }
    
      /**************************************************************
     * METODOS VIEW
     * VIEW{Name} recorre e imprimir los mapas 
     *  = mapEmp
     *  = mapCli
     *  = mapProd
     *  = mapTienda
     * ===================
     *  evalua : op{Name} = true --> Los datos se han modificado :
     *                               \-> hay que cargar los datos en memoria. 
     *                       false --> Los datos no se modificaron :
     *                               \-> no hay que cargar datos en memoria.
     ****************************************************************/
    
    public void viewProductos(){
        if (opStock){
            cargarProductos();
            System.out.println("Cargando PRODUCTOS [........]");
        }
        System.out.println( "_____________ FRANQUICIA : "+this.name+" PRODUCTOS _____________");
        for (Producto p : mapProd.values()){
            System.out.println( p.toString(this) );
        }
        System.out.println( "===================================");
    }
    public void viewEmpleados(){
        if (opHoras){
            cargarEmpleados();
             System.out.println("Cargando EMPLEADOS [........]");
        }
        System.out.println( "_____________ FRANQUICIA : "+this.name+" EMPLEADOS _____________");
        for (Empleado em : mapEmp.values()){
            System.out.println(em.toString(this) );
        }
        System.out.println( "===================================");
    }
    
    
    
     //      ==== OPERACIONES LEECTURA  SOBRE DB ======== \\
     /**************************************************************
     *  = getTiendaId --> Recupera el id del Objeto : con una consulta en la DB  
     ****************************************************************/
    
    private void cargarId() {
        int tID = -1;
           try
            {
                Connection con = getFranquicia().db.getConn();
                Statement statement = con.createStatement();
                //Probamos a realizar unha consulta
                ResultSet rs = statement.executeQuery("select * from TIENDA  where TIENDA_name = "+this.name );
                while(rs.next()){  
                    tID = rs.getInt("TIENDA_id");
                }
            this.id= tID;    
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }finally{
            DB_driver.finishDB();
            }
    }

    private void cargarEmpleados() {
        
                try {
                        DB_driver.finishDB();   
                        Connection con = getFranquicia().db.getConn();
                        Statement statement = con.createStatement();
                        String sql = "SELECT\n" +
                                    "    TIENDA_EMPLEADO.TIENDA_id,\n" +
                                    "    EMPLEADO.EMPLEADO_id,\n" +
                                    "    EMPLEADO_name,\n" +
                                    "    EMPLEADO_apellido,\n" +
                                    "    nHoras\n" +
                                    "FROM\n" +
                                    " EMPLEADO   \n" +
                                    " JOIN TIENDA_EMPLEADO ON\n" +
                                    " EMPLEADO.EMPLEADO_id =TIENDA_EMPLEADO.EMPLEADO_id\n" +
                                    "WHERE TIENDA_EMPLEADO.TIENDA_id = "+this.getId()+"   \n" +
                                    "ORDER BY EMPLEADO_name;"; 
                        ResultSet rs = statement.executeQuery(sql );
                        mapEmp.clear();
                        while(rs.next()){
                               Empleado em = new Empleado( 
                                        rs.getString("EMPLEADO_name"),
                                        rs.getString("EMPLEADO_apellido")
                                    );
                                    em.setId(rs.getInt("EMPLEADO_id")); 
                                    em.setnHoras(rs.getFloat("nHoras"));
                            mapEmp.put(em.getName(),em);   
                        }
                    }catch(SQLException e){
                        System.err.println(e.getMessage());
                    }finally{
                          
                        DB_driver.finishDB();
                    }
          
    }

    

    private void cargarProductos() {
          
                try
                    {
                        Connection con = getFranquicia().db.getConn();
                        Statement statement = con.createStatement();
                        String sql = "SELECT\n" +
                                    "    PRODUCTO.PRODUCTO_id,\n" +
                                    "    PRODUCTO_name,\n" +
                                    "    PRODUCTO_price,\n" +
                                    "    PRODUCTO_description,\n" +
                                    "    stock\n" +
                                    "FROM\n" +
                                    " PRODUCTO   \n" +
                                    " JOIN TIENDA_PRODUCTO ON\n" +
                                    "    PRODUCTO.PRODUCTO_id = TIENDA_PRODUCTO.PRODUCTO_id\n" +
                                    "WHERE TIENDA_PRODUCTO.TIENDA_id = "+this.getId()+"   \n" +
                                    "ORDER BY PRODUCTO_name;"; 
                        ResultSet rs = statement.executeQuery(sql);
                        mapProd.clear();
                        while(rs.next()){

                                Producto p = new Producto( 
                                        rs.getString("PRODUCTO_name"),
                                        rs.getFloat("PRODUCTO_price"),
                                        rs.getString("PRODUCTO_description")
                                );
                                p.setId(rs.getInt("PRODUCTO_id"));
                                p.setStock(rs.getInt("stock"));
                                mapProd.put(p.getName(),p);
                        }
                    } catch(SQLException e){
                        System.err.println(e.getMessage());
                    }finally{
                    DB_driver.finishDB();
                    }
           
    }
    
   
        //      ==== OPERACIONES ESCRITURA  SOBRE DB ======== \\
      /**************************************************************
     * insert{Name} llama a el drive pasandole un objeto 
     *                      el drive --> Escribe el objeto en la dB  
     *  insertTiendaEmpleado   =>   Escribe en la tabla  TIENDA_EMPLEADO
     *                              los datos : TIENDA_id EMPLEADO_id nHoras
     *  insertTiendaProducto   =>   Escribe en la tabla  TIENDA_PRODUCTO
     *                              los datos : TIENDA_id PRODUCTO_id stock
     *  updateTiendaEmpleado   =>   Escribe en la tabla  TIENDA_EMPLEADO
     *                              los datos :  nHoras
     *  updateTiendaProducto   =>   Escribe en la tabla  TIENDA_PRODUCTO
     *                              los datos :  stock
     * ===================
     * op{Name} = true --> Los datos se han modificado desde la ultima carga en memoria
     ****************************************************************/
//    private void operateTiendaProducto(  Producto p, int stock) {
//       if (p.getStock(this)<0){
//           
//           System.out.println("Insertando stock actual :"+p.getStock(this)+"intentando añadir stock "+ stock);
//           insertTiendaProducto(p,stock);
//        }else{
//            System.out.println("Actualizando stock actual :"+p.getStock(this)+"intentando añadir stock "+ stock);
//           updateTiendaProducto(p,stock);
//        }
//    }
//    
//     private void operateTiendaEmpleado(  Empleado em, float nHoras) {
//               System.out.println("em.getnHoras(this)<0 = " +em.getnHoras(this));
//         if (em.getnHoras(this)<0){
//           System.out.println("Insertando horas horas actuales :"+em.getnHoras(this)+"intentando añadir nHoras "+ nHoras);
//           insertTiendaEmpleado(em,nHoras);
//        }else{
//            System.out.println("Actualizando horas horas actuales :"+em.getnHoras(this)+"intentando añadir nHoras "+ nHoras); 
//           updateTiendaEmpleado(em,nHoras);
//           
//
//        }
//    }
    
    
    private void insertTiendaEmpleado(  Empleado em ,float nHoras) {
        
        getFranquicia().db.insertTiendaEmpleado(this.getId(),em.getId(),0);
         this.opHoras=true;
    }
        
    private void insertTiendaProducto(  Producto p,  int stock) {
        
        getFranquicia().db.insertTiendaProducto(this.getId(),p.getId(),stock);
         this.opStock=true;
    } 
    private void updateTiendaEmpleado(  Empleado em, float nHoras) {
        getFranquicia().db.updateTiendaEmpleado(this.getId(),em.getId(),nHoras);
         this.opHoras=true;
    }
        
    private void updateTiendaProducto(  Producto p,  int stock) {
        getFranquicia().db.updateTiendaProducto(this.getId(),p.getId(),stock);
         this.opStock=true;
    } 
    private void  deleteTiendaProducto(Tienda t, Producto p){
       getFranquicia().db.deleteTiendaProducto(t,p); 
        this.opStock=true;
    }
    private void  deleteTiendaEmpleado(Tienda t, Empleado em){
       getFranquicia().db.deleteTiendaEmpleado(t,em); 
       this.opHoras=true;
    }

}
