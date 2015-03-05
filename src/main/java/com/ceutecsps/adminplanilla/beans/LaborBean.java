/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.LaborDAO;
import com.ceutecsps.adminplanilla.documents.Labor;
import com.ceutecsps.adminplanilla.factories.LaborFactory;
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
public class LaborBean extends AbstractBean {

    private Labor selectedLabor;
    private List<Labor> listaLabores;
    private LaborDAO laborDAO = new LaborDAO();

    @PostConstruct
    @Override
    public void inicializarClase() {
        selectedLabor = LaborFactory.produceLabor();
        mostrarData();
    }

    @Override
    public void mostrarData() {
        listaLabores = laborDAO.readAll();
    }

    @Override
    public String agregarData(Object objeto) {
        boolean result = laborDAO.insert(objeto);
        return "labores?faces-redirect=true&amp;result=" + result;
    }

     @Override
    public String modificarData(Object objeto, boolean updateable) {
        if(updateable){
            this.setUpdateable(false);
            boolean result = laborDAO.update((Labor) objeto);
            return "empleado?faces-redirect=true&amp;result="+result+"&amp;id=" + ((Labor) objeto).getId();
        }
         return "empleado?faces-redirect=true&amp;updateable=true&amp;id=" + ((Labor) objeto).getId();
    }

    @Override
    public String eliminarData(Object objeto) {
        boolean result = laborDAO.delete((Labor) objeto);
        return "empleado?faces-redirect=true&amp;result="+result;
    }
    
    @Override
    public void onRowEdit(RowEditEvent event) {
        modificarData(event.getObject(),true);
    }

    @Override
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Labor) event.getObject()).getId()+"");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Labor getSelectedLabor() {
        return selectedLabor;
    }

    public void setSelectedLabor(Labor selectedLabor) {
        this.selectedLabor = selectedLabor;
    }

    public List<Labor> getListaLabores() {
        return listaLabores;
    }

    public void setListaLabores(List<Labor> listaLabores) {
        this.listaLabores = listaLabores;
    }
    
    
}
