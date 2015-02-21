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
public class EmpleadoDAO {

    /**
     * Carga todos los empleados activos
     * @return lista de empleados
     */
    public List<Empleado> readEmpleados() {
        List listaEmpleados = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.Empleados";
        Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.INFO, "Cargando Lista de Empleados");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getDate("inactive_date") == null) {
                    Empleado empleado = new Empleado();
                    empleado.setId_empleado(resultSet.getInt("id_empleado"));
                    empleado.setNombre(resultSet.getString("nombre"));
                    empleado.setApellido(resultSet.getString("apellido"));
                    empleado.setFecha_nac(resultSet.getDate("fecha_nac"));
                    empleado.setInactive_date(resultSet.getDate("inactive_date"));
                    listaEmpleados.add(empleado);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaEmpleados;
    }

    /**
     * Se envia un empleado, ya pasando las validaciones, y se inserta en la
     * tabla Empleados
     *
     * @param empleado
     * @return true si se inserto correctamente
     */
    public boolean insertarEmpleado(Empleado empleado) {
        boolean result = false;
        String query = "INSERT INTO adminPlanillas.Empleados (nombre,apellido,fecha_nac) values (?,?,?)";
        Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.INFO, "Insertando Empleado con ID: {0}", empleado.getId_empleado());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setDate(3, new java.sql.Date(empleado.getFecha_nac().getTime()));
            result = pstmt.execute();
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return result;
    }

    /**
     * Se envia un empleado para marcar como borrado el empleado seleccionado Se
     * llena el campo inactive_date para marcar como borrado
     *
     * @param empleado
     * @return true si se elimino bien
     */
    public boolean eliminarEmpleado(Empleado empleado) {
        boolean result = false;
        Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.INFO, "Eliminando empleado {0}", empleado.getId_empleado());
        String query = "UPDATE adminPlanillas.Empleados SET inactive_date = ? WHERE id_empleado = " + empleado.getId_empleado();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            result = pstmt.execute();
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.INFO, "Resultado Eliminar Data - Row Result: {0}", result);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return result;
    }

    /**
     * Se envia un empleado para actualizar ciertos campos: Nombre, Apellido y Fecha de Nacimiento
     * @param empleado
     * @return true si se actualizo bien
     */
    public boolean actualizarEmpleado(Empleado empleado) {
        boolean result = false;
        Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.INFO, "Eliminando empleado {0}", empleado.getId_empleado());
        String query = "UPDATE adminPlanillas.Empleados SET inactive_date = ? WHERE id_empleado = " + empleado.getId_empleado();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setDate(3, new java.sql.Date(empleado.getFecha_nac().getTime()));
            result = pstmt.execute();
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.INFO, "Resultado Eliminar Data - Row Result: {0}", result);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return result;
    }

}
