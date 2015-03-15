/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Actividad;
import com.ceutecsps.adminplanilla.documents.Planilla;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeries
 */
public class PlanillaDAO implements IDAO {
    
    private static final Logger LOGGER = Logger.getLogger(PlanillaDAO.class.getName());
    
    @Override
    public List readAll() {
        List<Planilla> listaPlanillas = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.planillas";
        LOGGER.log(Level.INFO, "Cargando Lista de Planillas");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                Statement statement2 = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Planilla planilla = new Planilla();
                    List<Actividad> listaActividades = new ArrayList();
                    planilla.setId(resultSet.getInt("Id"));
                    planilla.setFecha_inicio(resultSet.getDate("Fecha_Inicio"));
                    planilla.setFecha_fin(resultSet.getDate("Fecha_Fin"));
                    planilla.setCreada(resultSet.getBoolean("Planilla_Creada"));
                    planilla.setInactive_date(resultSet.getDate("Inactive_Date"));
                    try (ResultSet rs = statement2.executeQuery("Select * FROM adminPlanillas.actividades_x_planilla WHERE Id_Planilla = " + planilla.getId())) {
                        while (rs.next()) {
                            listaActividades.add((Actividad) new ActividadDAO().find(rs.getInt("Id_Actividad")));
                        }
                    }
                    planilla.setListaActividades(listaActividades);
                    listaPlanillas.add(planilla);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaPlanillas;
    }
    
    @Override
    public boolean insert(Object object) {
        Planilla planilla = (Planilla) object;
        int result = 0;
        String query = "INSERT INTO adminPlanillas.planillas (Fecha_Inicio,Fecha_Fin,Planilla_Creada,Id_Deduccion) values (?,?,?,?)";
        
        String query2 = "INSERT INTO adminPlanillas.actividades_x_planilla (Id_Actividad,Id_Planilla) values (?,?)";
        LOGGER.log(Level.INFO, "Insertando Planilla con ID: {0}", planilla.getId());
        Connection connection = ConnectionManager.produceConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmt2 = connection.prepareStatement(query2);) {
            
            connection.setAutoCommit(false);
            
            pstmt.setDate(1, new java.sql.Date(planilla.getFecha_inicio().getTime()));
            pstmt.setDate(2, new java.sql.Date(planilla.getFecha_fin().getTime()));
            pstmt.setBoolean(3, planilla.isCreada());
            //temporal
            pstmt.setInt(4, 1);
            result = pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    planilla.setId((int) generatedKeys.getLong(1));
                }
            }
            LOGGER.log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
            for (Actividad act : planilla.getListaActividades()) {
                pstmt2.setInt(1, act.getId());
                pstmt2.setInt(2, planilla.getId());
                pstmt2.addBatch();
            }
            int updates[] = pstmt2.executeBatch();
            System.out.println("Se actualizaron: " + Arrays.toString(updates));
            connection.commit();
        } catch (Exception e) {
            try {
                LOGGER.log(Level.SEVERE, "AgregarData Error: {0}", e);
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PlanillaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            result = 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PlanillaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result > 0;
    }
    
    @Override
    public boolean delete(Object object) {
        Planilla planilla = (Planilla) object;
        int result = 0;
        LOGGER.log(Level.INFO, "Eliminando empleado {0}", planilla.getId());
        String query = "UPDATE adminPlanillas.planillas SET Inactive_Date = ? WHERE Id = " + planilla.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Eliminar Data - Row Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "eliminar Error: {0}", e);
        }
        return result > 0;
    }
    
    @Override
    public boolean update(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Object find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean findBetweenDates(Date fromDate, Date toDate) {
        String query = "SELECT Id"
                + " FROM adminPlanillas.planillas"
                + " WHERE Fecha_Inicio <= '" + toDate + "'"
                + " AND Fecha_Fin >= '" + fromDate + "'"
                + " AND Inactive_Date is null;";
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanillaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
