/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Labor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeries
 */
public class LaborDAO {

    private static final Logger LOGGER = Logger.getLogger(LaborDAO.class.getName());

    public List<Labor> readLabores() {
        List<Labor> listaLabores = new ArrayList();
         String query = "SELECT * FROM adminPlanillas.labores";
        LOGGER.log(Level.INFO, "Cargando Lista de Labores");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Labor labor = new Labor();
                    //getLabor
                    listaLabores.add(labor);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaLabores;
    }

    public boolean agregarLabor(Labor labor) {
        return false;
    }

    public boolean actualizarLabor(Labor labor) {
        return false;
    }

    public boolean eliminarLabor(Labor labor) {
        return false;
    }

}
