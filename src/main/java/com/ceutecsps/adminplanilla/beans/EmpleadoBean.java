/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Empleado;
import com.sun.istack.internal.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Jeries
 */
@Named
@SessionScoped
public class EmpleadoBean implements BasicOperations{

    private List<Empleado> listaEmpleados;
    
    @PostConstruct
    @Override
    public void inicializarClase() {
        mostrarData();
    }

    @Override
    public void mostrarData() {
        listaEmpleados = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.Empleados";
        Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.INFO, "Cargando Lista de Empleados");
        try (Connection connection = ConnectionManager.produceConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)){
            Empleado empleado = new Empleado();
            empleado.setId_empleado(resultSet.getInt("id_empleado"));
            empleado.setNombre(resultSet.getString("nombre"));
            empleado.setApellido(resultSet.getString("apellido"));
            empleado.setFecha_nac(resultSet.getDate("fecha_nac"));
            empleado.setInactive_date(resultSet.getDate("inactive_date"));
            listaEmpleados.add(empleado);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.SEVERE, "MostrarData Error: {0}",e);
        }
    }

    @Override
    public String agregarData(Object objeto) {
        Empleado empleado = (Empleado) objeto;
        String query = "INSERT INTO adminPlanillas.Empleados (nombre,apellido,fecha_nac) values (?,?,?)";
        Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.INFO, "Insertando Empleado con ID: {0}", empleado.getId_empleado());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);){
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2,empleado.getApellido());
            pstmt.setDate(3, new java.sql.Date(empleado.getFecha_nac().getTime()));
            int count = pstmt.executeUpdate();
            Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.INFO, "Resultado Agregar Data - Row Count: {0}", count);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.SEVERE, "AgregarData Error: {0}",e);
        }
        return "URL";
    }

    @Override
    public String modificarData(Object objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminarData(Object objeto) {
        Empleado empleado = (Empleado) objeto;
        Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.INFO, "Eliminando empleado {0}", empleado.getId_empleado());
        String query = "UPDATE adminPlanillas.Empleados SET inactive_date = ? WHERE id_empleado = " + empleado.getId_empleado();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);){
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            int count = pstmt.executeUpdate();
            Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.INFO, "Resultado Eliminar Data - Row Count: {0}", count);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoBean.class.getClass()).log(Level.SEVERE, "AgregarData Error: {0}",e);
        }
        return "URL";
    }
    
}
