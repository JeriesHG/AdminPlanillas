/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author JeriesHG
 */
@Named
@SessionScoped
public class Testclass implements Serializable{
    
    public String exampleMethod(){
        //ACA USO PARA PROBAR LA CONEXION DE MARIADB TIENE QUE IMPRIMIR org.mariadb.jdbc.MySQLConnection@...
        System.out.println(ConnectionManager.produceConnection());
        return "Testing";
    }
}
