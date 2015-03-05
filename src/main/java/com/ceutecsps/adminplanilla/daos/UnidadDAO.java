/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Unidad;
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
public class UnidadDAO implements IDAO {

    private static final Logger LOGGER = Logger.getLogger(UnidadDAO.class.getName());

    /**
     * Carga todas las unidades de medida activa
     *
     * @return lista de unidades de medida
     */
    @Override
    public List<Unidad> readAll() {
        List listaUnidadMedida = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.unidades";
        LOGGER.log(Level.INFO, "Cargando Lista de Unidades");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Unidad unidadMedida = new Unidad();
                    unidadMedida.setId(resultSet.getInt("Id"));
                    unidadMedida.setDescripcion(resultSet.getString("Descripcion"));
                    unidadMedida.setInactive_date(resultSet.getDate("Inactive_Date"));
                    listaUnidadMedida.add(unidadMedida);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaUnidadMedida;
    }

    /**
     * Se envia una unidad de medida, ya pasando las validaciones, y se inserta
     * en la tabla Unidad
     *
     * @param object
     * @return true si se inserto correctamente
     */
    @Override
    public boolean insert(Object object) {
        int result = 0;
        Unidad unidadMedida = (Unidad) object;
        String query = "INSERT INTO adminPlanillas.unidades (Descripcion) values (?)";
        LOGGER.log(Level.INFO, "Insertando Unidad de Medida con ID: {0}", unidadMedida.getId());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, unidadMedida.getDescripcion());
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "insertarData Error: {0}", e);
        }
        return result > 0;
    }

    /**
     * Se envia un unidad de medida para marcar como borrado la unidad de medida
     * seleccionado Se llena el campo inactive_date para marcar como borrado
     *
     * @param object
     * @return true si se elimino bien
     */
    @Override
    public boolean delete(Object object) {
        int count = 0;
        Unidad unidadMedida = (Unidad) object;
        LOGGER.log(Level.INFO, "Eliminando UnidadMedida {0}", unidadMedida.getId());
        String query = "UPDATE adminPlanillas.unidades SET Inactive_Date = ? WHERE Id = " + unidadMedida.getId();
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

    /**
     * Se envia una unidad de medida para actualizar ciertos descripcion
     *
     * @param object
     * @return true si se actualizo bien
     */
    @Override
    public boolean update(Object object) {
        Unidad unidadMedida = (Unidad) object;
        int result = 0;
        LOGGER.log(Level.INFO, "Actualizando unidad de medida {0}", unidadMedida.getId());
        String query = "UPDATE adminPlanillas.unidades SET Descripcion = ? WHERE Id = " + unidadMedida.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, unidadMedida.getDescripcion());
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Actualizar Data - Row Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Actualizar Data Error: {0}", e);
        }
        return result > 0;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Unidad find(int id) {
        String query = "SELECT * FROM adminPlanillas.unidades WHERE Id = " + id;
        LOGGER.log(Level.INFO, "Buscando unidad con ID {0}", id);
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Unidad unidadMedida = new Unidad();
                unidadMedida.setId(resultSet.getInt("Id"));
                unidadMedida.setDescripcion(resultSet.getString("Descripcion"));
                unidadMedida.setInactive_date(resultSet.getDate("Inactive_Date"));
                return unidadMedida;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return new Unidad();
    }

}
