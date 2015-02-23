/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.EmpleadoDAO;
import com.ceutecsps.adminplanilla.documents.Empleado;
import com.ceutecsps.adminplanilla.factories.EmpleadoFactory;
import java.util.List;
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

    private Empleado selectedEmpleado;
    private List<Empleado> listaEmpleados;
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    @PostConstruct
    @Override
    public void inicializarClase() {
        selectedEmpleado = EmpleadoFactory.produceEmpleado();
        mostrarData();
    }

    @Override
    public void mostrarData() {
        listaEmpleados = empleadoDAO.readEmpleados();
    }

    @Override
    public String agregarData(Object objeto) {
        boolean result = empleadoDAO.insertarEmpleado((Empleado) objeto);
        selectedEmpleado = EmpleadoFactory.produceEmpleado();
        return "URL?faces-redirect=true&amp;result="+result;
    }

    @Override
    public String modificarData(Object objeto, boolean updateable) {
        if(updateable){
            this.setUpdateable(false);
            boolean result = empleadoDAO.actualizarEmpleado((Empleado) objeto);
            return "empleado?faces-redirect=true&amp;result="+result+"&amp;id=" + ((Empleado) objeto).getId();
        }
         return "empleado?faces-redirect=true&amp;saveable=true&amp;id=" + ((Empleado) objeto).getId();
    }

    @Override
    public String eliminarData(Object objeto) {
        boolean result = empleadoDAO.eliminarEmpleado((Empleado) objeto);
        return "URL?faces-redirect=true&amp;result="+result;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Empleado getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Empleado selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }
    
}
