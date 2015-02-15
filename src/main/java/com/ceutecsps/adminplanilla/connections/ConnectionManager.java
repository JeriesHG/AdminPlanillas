/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JeriesHG
 */
public class ConnectionManager {
    
    private final String dbUrl="jdbc:mysql://localhost:3306/";
    private final String dbUser = "root";
    private final String dbPass = "123";
    
    private static final Supplier<ConnectionManager> connectionSupplier = ConnectionManager::new;
    
    public Connection getConnection(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Get Connection", ex);
        }
        return null;
    }
    
    public static Connection produceConnection(){
        return connectionSupplier.get().getConnection();
    }
}
