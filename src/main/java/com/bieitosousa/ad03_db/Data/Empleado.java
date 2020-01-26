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


import com.bieitosousa.ad03_db.Data.Tienda;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 *
 * @author bieito
 */
public class Empleado {
    private DB_driver db = DB_driver.instance();
    private int id = -1; 
    private String name;        
    private String apellidos;        
    private float nHoras = (float) -1;

    public Empleado( String name, String apellidos) {
        this.name = name;
        this.apellidos = apellidos;
    }

    public int getId() {
        if (id==-1){
        cargarId();
        }
        return id;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public float getnHoras(Tienda t) {
        cargarHoras(t);
        return nHoras;
    }

    public void setnHoras(float nHoras) {
        this.nHoras = nHoras;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", name=" + name + ", apellidos=" + apellidos +  '}';
    }
    
    public String toString(Tienda t) {
        return "Empleado{" + "id=" + id + ", name=" + name + ", apellidos=" + apellidos + ", nHoras=" + getnHoras(t) + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Empleado other = (Empleado) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        return true;
    }
    
     //      ==== OPERACIONES LECTURA  SOBRE DB ======== \\
     /**************************************************************
     * Recupera el id del Objeto : con una conslta en la DB  
     ****************************************************************/
    
    
    private void cargarId(){
     int emplID = -1;
           try
            {
                Connection con = DB_driver.getConn();
                Statement statement = con.createStatement();
                //Probamos a realizar unha consulta
                ResultSet rs = statement.executeQuery("select * from EMPLEADO where EMPLEADO_name = "+this.name );
                while(rs.next()){
                    emplID= rs.getInt("EMPLEADO_id");
                }
             id= emplID;  
            }catch(SQLException e){
                System.err.println(e.getMessage());
             }finally{
            DB_driver.finishDB();
            }
    }  

    private void cargarHoras(Tienda t) {
     this.nHoras = (float)-1;
        System.out.println("comienza valiendo nHoras ="+this.nHoras);
        try{
            Connection con = db.getConn();
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select nHoras from TIENDA_EMPLEADO where TIENDA_id = " +t.getId()+ " and EMPLEADO_id = "+ this.getId() );
            while(rs.next()){
                     this.nHoras = rs.getFloat("nHoras");
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
            
        }finally{  
            DB_driver.finishDB();
            System.out.println("sale saliendo nhoras = "+this.nHoras);
        }   
    }
    
}
