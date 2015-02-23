/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.UnidadDeMedida;
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
public class UnidadDeMedidaDAO {

    /**
     * Carga todas las unidades de medida activa
     *
     * @return lista de unidades de medida
     */
    public List<UnidadDeMedida> readUnidadMedidas() {
        List listaUnidadMedida = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.UnidadMedida";
        Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.INFO, "Cargando Lista de Unidades");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("inactive_date") == null) {
                    UnidadDeMedida unidadMedida = new UnidadDeMedida();
                    unidadMedida.setId(resultSet.getInt("id_unidadmedida"));
                    unidadMedida.setDescripcion(resultSet.getString("descripcion"));
                    unidadMedida.setUnidad(resultSet.getString("unidad"));
                    unidadMedida.setInactive_date(resultSet.getDate("inactive_date"));
                    listaUnidadMedida.add(unidadMedida);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaUnidadMedida;
    }

    /**
     * Se envia una unidad de medida, ya pasando las validaciones, y se inserta
     * en la tabla UnidadDeMedida
     *
     * @param unidadMedida
     * @return true si se inserto correctamente
     */
    public boolean agregarUnidadDeMedida(UnidadDeMedida unidadMedida) {
        boolean result = false;
        String query = "INSERT INTO adminPlanillas.UnidadMedida (descripcion,unidad) values (?,?,?)";
        Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.INFO, "Insertando Unidad de Medida con ID: {0}", unidadMedida.getId());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, unidadMedida.getDescripcion());
            pstmt.setString(2, unidadMedida.getUnidad());
            result = pstmt.execute();
            Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
        } catch (Exception e) {
            Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.SEVERE, "insertarData Error: {0}", e);
        }
        return result;
    }

    /**
     * Se envia un unidad de medida para marcar como borrado la unidad de medida
     * seleccionado Se llena el campo inactive_date para marcar como borrado
     *
     * @param unidadMedida
     * @return true si se elimino bien
     */
    public boolean eliminarUnidadMedida(UnidadDeMedida unidadMedida) {
        boolean result = false;
        Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.INFO, "Eliminando UnidadMedida {0}", unidadMedida.getId());
        String query = "UPDATE adminPlanillas.UnidadMedida SET inactive_date = ? WHERE id_unidadmedida = " + unidadMedida.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            int count = pstmt.executeUpdate();
            Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.INFO, "Resultado Eliminar Data - Row Count: {0}", count);
        } catch (Exception e) {
            Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.SEVERE, "eliminarData Error: {0}", e);
        }
        return result;
    }

    /**
     * Se envia una unidad de medida para actualizar ciertos campos: Nombre,
     * Apellido y Fecha de Nacimiento
     *
     * @param unidadMedida
     * @return true si se actualizo bien
     */
    public boolean actualizarUnidadDeMedida(UnidadDeMedida unidadMedida) {
        boolean result = false;
        Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.INFO, "Actualizando unidad de medida {0}", unidadMedida.getId());
        String query = "UPDATE adminPlanillas.UnidadMedida SET descripcion = ?, unidad = ? WHERE id_unidadmedida = " + unidadMedida.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, unidadMedida.getDescripcion());
            pstmt.setString(2, unidadMedida.getUnidad());
            result = pstmt.execute();
            Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.INFO, "Resultado Eliminar Data - Row Result: {0}", result);
        } catch (Exception e) {
            Logger.getLogger(UnidadDeMedidaDAO.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return result;
    }

}
