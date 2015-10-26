/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.EmpleadoDAO;
import com.ceutecsps.adminplanilla.documents.Empleado;
import com.ceutecsps.adminplanilla.factories.EmpleadoFactory;
import com.ceutecsps.adminplanilla.utilities.UtilityClass;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

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
        
//        if(UtilityClass.getURLParameter("updateable") != null){
//           if(Boolean.parseBoolean(UtilityClass.getURLParameter("updateable"))){
//               selectedEmpleado = empleadoDAO.find(UtilityClass.getURLParameter("id"));
//           }
//        }
     
    }

    @Override
    public void mostrarData() {
        listaEmpleados = empleadoDAO.readAll();
    }

    @Override
    public String agregarData(Object objeto) {
        boolean result = empleadoDAO.insert((Empleado) objeto);
        selectedEmpleado = EmpleadoFactory.produceEmpleado();
        return "empleado?faces-redirect=true&amp;result="+result;
    }

    @Override
    public String modificarData(Object objeto, boolean updateable) {
        if(updateable){
            this.setUpdateable(false);
            boolean result = empleadoDAO.update((Empleado) objeto);
            return "empleado?faces-redirect=true&amp;result="+result+"&amp;id=" + ((Empleado) objeto).getId();
        }
         return "empleado?faces-redirect=true&amp;updateable=true&amp;id=" + ((Empleado) objeto).getId();
    }

    @Override
    public String eliminarData(Object objeto) {
        boolean result = empleadoDAO.delete((Empleado) objeto);
        return "empleado?faces-redirect=true&amp;result="+result;
    }
    
    public void onRowEdit(RowEditEvent event) {
        modificarData(event.getObject(),true);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Empleado) event.getObject()).getId()+"");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
