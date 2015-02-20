/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.beans.EmpleadoBean;
import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Empleado;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeries
 */
public class EmpleadoDAO {

    public List<Empleado> readEmpleados() {
        List listaEmpleados = new ArrayList();
        String query = "SELECT * FROM adminPlanillas.Empleados";
        Logger.getLogger(EmpleadoBean.class.getName()).log(Level.INFO, "Cargando Lista de Empleados");
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
            Logger.getLogger(EmpleadoBean.class.getName()).log(Level.SEVERE, "MostrarData Error: {0}", e);
        }
        return listaEmpleados;
    }

}
