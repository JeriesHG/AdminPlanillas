/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Actividad;
import com.ceutecsps.adminplanilla.documents.Empleado;
import com.ceutecsps.adminplanilla.documents.Labor;
import com.ceutecsps.adminplanilla.factories.ActividadFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeries
 */
public class ActividadDAO implements IDAO {

    private static final Logger LOGGER = Logger.getLogger(ActividadDAO.class.getName());
    private final SimpleDateFormat formatoEstandar = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public List readAll() {
        List listaActividad = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.actividades";
        LOGGER.log(Level.INFO, "Cargando Lista de Actividades");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Actividad actividad = ActividadFactory.produceActiviad();
                    actividad.setId(resultSet.getInt("Id"));
                    actividad.setFecha(resultSet.getDate("Fecha"));
                    actividad.setLabor((Labor) new LaborDAO().find(resultSet.getInt("Id_Labor")));
                    actividad.setEmpleado((Empleado) new EmpleadoDAO().find(resultSet.getInt("Id_Empleado")));
                    actividad.setInactive_date(resultSet.getDate("Inactive_Date"));
                    listaActividad.add(actividad);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaActividad;
    }

    @Override
    public boolean insert(Object object) {
        int result = 0;
        Actividad actividad = (Actividad) object;
        String query = "INSERT INTO adminPlanillas.actividades (Fecha,Status,Id_Labor,Id_Empleado) values (?,?,?,?)";
        LOGGER.log(Level.INFO, "Insertando Actividad con ID: {0}", actividad.getId());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(actividad.getFecha().getTime()));
            pstmt.setBoolean(2, actividad.isStatus());
            pstmt.setInt(3, actividad.getLabor().getId());
            pstmt.setInt(4, actividad.getEmpleado().getId());
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "insertarData Error: {0}", e);
        }
        return result > 0;
    }

    @Override
    public boolean delete(Object object) {
        int count = 0;
        Actividad actividad = (Actividad) object;
        LOGGER.log(Level.INFO, "Eliminando Actividad {0}", actividad.getId());
        String query = "UPDATE adminPlanillas.actividades SET Inactive_Date = ? WHERE Id = " + actividad.getId();
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
    public boolean update(Object object) {
        int result = 0;
        Actividad actividad = (Actividad) object;
        String query = "UPDATE adminPlanillas.actividades SET Fecha = ?, Status = ? ,Id_Labor = ?, Id_Empleado = ? WHERE Id = " + actividad.getId();
        LOGGER.log(Level.INFO, "Actualizando Actividad con ID: {0}", actividad.getId());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(actividad.getFecha().getTime()));
            pstmt.setBoolean(2, actividad.isStatus());
            pstmt.setInt(3, actividad.getLabor().getId());
            pstmt.setInt(4, actividad.getEmpleado().getId());
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "insertarData Error: {0}", e);
        }
        return result > 0;
    }

    @Override
    public Object find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object findActividadExistente(int id, Date date) {
        String query = "SELECT * FROM adminPlanillas.actividades"
                + " WHERE Id_Empleado = " + id
                + " AND Fecha = '" + new java.sql.Date(date.getTime())+"';";
        
        Actividad actividad = null;
        LOGGER.log(Level.INFO, "Buscando actividad: {0}",id);
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    actividad = ActividadFactory.produceActiviad();
                    actividad.setId(resultSet.getInt("Id"));
                    actividad.setFecha(resultSet.getDate("Fecha"));
                    actividad.setLabor((Labor) new LaborDAO().find(resultSet.getInt("Id_Labor")));
                    actividad.setEmpleado((Empleado) new EmpleadoDAO().find(resultSet.getInt("Id_Empleado")));
                    actividad.setInactive_date(resultSet.getDate("Inactive_Date"));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        System.out.println("ACTIVIDAD ENCONTRADA: " + actividad);
        return actividad;
    }

}
