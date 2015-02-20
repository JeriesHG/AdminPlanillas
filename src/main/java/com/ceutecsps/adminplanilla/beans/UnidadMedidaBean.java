/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.UnidadMedida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Jeries
 */
@Named
@ViewScoped
public class UnidadMedidaBean extends AbstractBean {

    private List<UnidadMedida> listaUnidadMedida;

    @PostConstruct
    @Override
    public void inicializarClase() {
        mostrarData();
    }

    @Override
    public void mostrarData() {
        listaUnidadMedida = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.UnidadMedida";
        Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.INFO, "Cargando Lista de Unidad de Medida");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("inactive_date") == null) {
                    UnidadMedida unidadMedida = new UnidadMedida();
                    unidadMedida.setId_unidadMedida(resultSet.getInt("id_unidadmedida"));
                    unidadMedida.setDescripcion(resultSet.getString("descripcion"));
                    unidadMedida.setUnidad(resultSet.getString("unidad"));
                    unidadMedida.setInactive_date(resultSet.getDate("inactive_date"));
                    listaUnidadMedida.add(unidadMedida);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
    }

    @Override
    public String agregarData(Object objeto) {
        UnidadMedida unidadMedida = (UnidadMedida) objeto;
        String query = "INSERT INTO adminPlanillas.UnidadMedida (descripcion,unidad) values (?,?,?)";
        Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.INFO, "Insertando Unidad de Medida con ID: {0}", unidadMedida.getId_unidadMedida());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, unidadMedida.getDescripcion());
            pstmt.setString(2, unidadMedida.getUnidad());
            int count = pstmt.executeUpdate();
            Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.INFO, "Resultado Agregar Data - Row Count: {0}", count);
        } catch (Exception e) {
            Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return "URL";
    }

    @Override
    public String modificarData(Object objeto,boolean saveable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Tempates.
    }

    @Override
    public String eliminarData(Object objeto) {
        UnidadMedida unidadMedida = (UnidadMedida) objeto;
        Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.INFO, "Eliminando UnidadMedida {0}", unidadMedida.getId_unidadMedida());
        String query = "UPDATE adminPlanillas.UnidadMedida SET inactive_date = ? WHERE id_unidadmedida = " + unidadMedida.getId_unidadMedida();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            int count = pstmt.executeUpdate();
            Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.INFO, "Resultado Eliminar Data - Row Count: {0}", count);
        } catch (Exception e) {
            Logger.getLogger(UnidadMedidaBean.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return "URL";
    }

}
