/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Labor;
import com.ceutecsps.adminplanilla.factories.LaborFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeries
 */
public class LaborDAO implements IDAO{

    private static final Logger LOGGER = Logger.getLogger(LaborDAO.class.getName());

    @Override
    public List<Labor> readAll() {
        List<Labor> listaLabores = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.labores";
        LOGGER.log(Level.INFO, "Cargando Lista de Labores");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Labor labor = new Labor();
                    labor.setId(resultSet.getInt("Id"));
                    labor.setNombre(resultSet.getString("Nombre"));
                    labor.setPrecio(resultSet.getFloat("Precio"));
                    labor.setUnidad(new UnidadDAO().find(resultSet.getInt("Id_Unidad")));
                    listaLabores.add(labor);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaLabores;
    }

    @Override
    public boolean insert(Object object) {
        Labor labor = (Labor) object;
        int result = 0;
        String query = "INSERT INTO adminPlanillas.labores (Nombre,Precio,Id_Unidad) values (?,?,?)";
        LOGGER.log(Level.INFO, "Insertando labores con nombre: {0}", labor.getNombre());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, labor.getNombre());
            pstmt.setFloat(2, labor.getPrecio());
            pstmt.setInt(3, labor.getUnidad().getId());
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "insertarData Error: {0}", e);
        }
        return result > 0;
    }

    @Override
    public boolean update(Object object) {
        Labor labor = (Labor) object;
        int result = 0;
        LOGGER.log(Level.INFO, "Actualizando labores {0}", labor.getId());
        String query = "UPDATE adminPlanillas.labores SET Nombre = ?,Precio = ?, Id_Unidad = ? WHERE Id = " + labor.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, labor.getNombre());
            pstmt.setFloat(2, labor.getPrecio());
            pstmt.setInt(3, labor.getUnidad().getId());
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Actualizar Data - Row Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Actualizar Data Error: {0}", e);
        }
        return result > 0;
    }

    @Override
    public boolean delete(Object object) {
        Labor labor = (Labor) object;
        int count = 0;
         LOGGER.log(Level.INFO, "Eliminando labor {0}", labor.getId());
        String query = "UPDATE adminPlanillas.labores SET Inactive_Date = ? WHERE Id = " + labor.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            count = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Eliminar Data - Row Count: {0}", count);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "eliminarData Error: {0}", e);
        }
        return count > 0;
    }

    @Override
    public Object find(int id) {
       String query = "SELECT * FROM adminPlanillas.labores WHERE Id = " + id;
        LOGGER.log(Level.INFO, "Cargando Lista de Labores");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Labor labor = LaborFactory.produceLabor();
                    labor.setId(resultSet.getInt("Id"));
                    labor.setNombre(resultSet.getString("Nombre"));
                    labor.setPrecio(resultSet.getFloat("Precio"));
                    labor.setUnidad(new UnidadDAO().find(resultSet.getInt("Id_Unidad")));
                    return labor;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return LaborFactory.produceLabor();
    }

}
