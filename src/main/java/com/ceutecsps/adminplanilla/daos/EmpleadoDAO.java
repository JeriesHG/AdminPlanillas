/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Empleado;
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
public class EmpleadoDAO implements IDAO{

    private static final Logger LOGGER = Logger.getLogger(EmpleadoDAO.class.getName());

    /**
     * Carga todos los empleados activos
     *
     * @return lista de empleados
     */
    @Override
    public List<Empleado> readAll() {
        List listaEmpleados = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.empleados";
        LOGGER.log(Level.INFO, "Cargando Lista de Empleados");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Empleado empleado = new Empleado();
                    empleado.setId(resultSet.getInt("Id"));
                    empleado.setNombre(resultSet.getString("Nombre"));
                    empleado.setApellido(resultSet.getString("Apellido"));
                    empleado.setFecha_nac(resultSet.getDate("Fecha_Nac"));
                    empleado.setInactive_date(resultSet.getDate("Inactive_Date"));
                    listaEmpleados.add(empleado);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaEmpleados;
    }

    /**
     * Se envia un empleado, ya pasando las validaciones, y se inserta en la
     * tabla Empleados
     *
     * @param object
     * @return true si se inserto correctamente
     */
    @Override
    public boolean insert(Object object) {
        Empleado empleado = (Empleado) object;
        int result = 0;
        String query = "INSERT INTO adminPlanillas.empleados (Nombre,Apellido,Fecha_Nac) values (?,?,?)";
        LOGGER.log(Level.INFO, "Insertando Empleado con ID: {0}", empleado.getId());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setDate(3, new java.sql.Date(empleado.getFecha_nac().getTime()));
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return result > 0;
    }

    /**
     * Se envia un empleado para marcar como borrado el empleado seleccionado Se
     * llena el campo inactive_date para marcar como borrado
     *
     * @param object
     * @return true si se elimino bien
     */
    @Override
    public boolean delete(Object object) {
        Empleado empleado = (Empleado) object;
        int result = 0;
        LOGGER.log(Level.INFO, "Eliminando empleado {0}", empleado.getId());
        String query = "UPDATE adminPlanillas.empleados SET Inactive_Date = ? WHERE Id = " + empleado.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado Eliminar Data - Row Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "eliminar Error: {0}", e);
        }
        return result>0;
    }

    /**
     * Se envia un empleado para actualizar ciertos campos: Nombre, Apellido y
     * Fecha de Nacimiento
     *
     * @param object
     * @return true si se actualizo bien
     */
    @Override
    public boolean update(Object object) {
        Empleado empleado = (Empleado) object;
        int result = 0;
        LOGGER.log(Level.INFO, "Eliminando empleado {0}", empleado.getId());
        String query = "UPDATE adminPlanillas.empleados SET Nombre = ?, Apellido = ?, Fecha_Nac = ? WHERE Id = " + empleado.getId();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setDate(3, new java.sql.Date(empleado.getFecha_nac().getTime()));
            result = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Resultado actualizar Data - Row Result: {0}", result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "actualizar Error: {0}", e);
        }
        return result>0;
    }
    
    @Override
     public Object find(int id) {
        String query = "SELECT * FROM adminPlanillas.empleados"
                + " WHERE Id = " +id;
        LOGGER.log(Level.INFO, "BUSCANDO EMPLEADO CON ID #{0}",id);
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("Inactive_Date") == null) {
                    Empleado empleado = new Empleado();
                    empleado.setId(resultSet.getInt("Id"));
                    empleado.setNombre(resultSet.getString("Nombre"));
                    empleado.setApellido(resultSet.getString("Apellido"));
                    empleado.setFecha_nac(resultSet.getDate("Fecha_Nac"));
                    empleado.setInactive_date(resultSet.getDate("Inactive_Date"));
                    return empleado;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "buscarEmpleado Error: {0}", e);
        }
        return new Empleado();
    }

}
