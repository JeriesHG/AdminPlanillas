/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.daos.EmpleadoDAO;
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
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Jeries
 */
@Named
@ViewScoped
public class EmpleadoBean extends AbstractBean {

    private List<Empleado> listaEmpleados;
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    @PostConstruct
    @Override
    public void inicializarClase() {
        mostrarData();
        System.out.println("CALLED POST CONSTRUCT");
    }

    @Override
    public void mostrarData() {
        listaEmpleados = empleadoDAO.readEmpleados();
    }

    @Override
    public String agregarData(Object objeto) {
        Empleado empleado = (Empleado) objeto;
        String query = "INSERT INTO adminPlanillas.Empleados (nombre,apellido,fecha_nac) values (?,?,?)";
        Logger.getLogger(EmpleadoBean.class.getName()).log(Level.INFO, "Insertando Empleado con ID: {0}", empleado.getId_empleado());
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setDate(3, new java.sql.Date(empleado.getFecha_nac().getTime()));
            int count = pstmt.executeUpdate();
            Logger.getLogger(EmpleadoBean.class.getName()).log(Level.INFO, "Resultado Agregar Data - Row Count: {0}", count);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoBean.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return "URL";
    }

    @Override
    public String modificarData(Object objeto, boolean saveable) {
        if(!saveable){
            
            return "empleado";
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminarData(Object objeto) {
        Empleado empleado = (Empleado) objeto;
        Logger.getLogger(EmpleadoBean.class.getName()).log(Level.INFO, "Eliminando empleado {0}", empleado.getId_empleado());
        String query = "UPDATE adminPlanillas.Empleados SET inactive_date = ? WHERE id_empleado = " + empleado.getId_empleado();
        try (Connection connection = ConnectionManager.produceConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            int count = pstmt.executeUpdate();
            Logger.getLogger(EmpleadoBean.class.getName()).log(Level.INFO, "Resultado Eliminar Data - Row Count: {0}", count);
        } catch (Exception e) {
            Logger.getLogger(EmpleadoBean.class.getName()).log(Level.SEVERE, "AgregarData Error: {0}", e);
        }
        return "URL";
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
    
    

}
