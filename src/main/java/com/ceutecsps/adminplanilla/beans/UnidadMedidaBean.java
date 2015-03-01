/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.UnidadDAO;
import com.ceutecsps.adminplanilla.documents.Unidad;
import com.ceutecsps.adminplanilla.factories.UnidadFactory;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Jeries
 */
@Named
@ViewScoped
public class UnidadMedidaBean extends AbstractBean {

    private List<Unidad> listaUnidadMedida;
    private Unidad selectedUnidadDeMedida;
    private UnidadDAO unidadDAO = new UnidadDAO();

    @PostConstruct
    @Override
    public void inicializarClase() {
        mostrarData();
        selectedUnidadDeMedida = UnidadFactory.produceUnidadDeMedida();
    }

    @Override
    public void mostrarData() {
        listaUnidadMedida = unidadDAO.readUnidadMedidas();
    }

    @Override
    public String agregarData(Object objeto) {
        boolean result = unidadDAO.agregarUnidadDeMedida(((Unidad) objeto));
        selectedUnidadDeMedida = UnidadFactory.produceUnidadDeMedida();
        return "unidadMedida?faces-redirect=true&amp;result=" + result;
    }

    @Override
    public String modificarData(Object objeto, boolean updateable) {
        if (updateable) {
            this.setUpdateable(false);
            boolean result = unidadDAO.actualizarUnidadDeMedida(((Unidad) objeto));
            return "unidadMedida?faces-redirect=true&amp;result=" + result + "&amp;id=" + ((Unidad) objeto).getId();
        }
        return "unidadMedida?faces-redirect=true&amp;saveable=true&amp;id=" + ((Unidad) objeto).getId();
    }

    @Override
    public String eliminarData(Object objeto) {
        boolean result = unidadDAO.eliminarUnidadMedida(((Unidad) objeto));
        return "unidadMedida?faces-redirect=true&amp;result=" + result;
    }

    @Override
    public void onRowEdit(RowEditEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onRowCancel(RowEditEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Unidad> getListaUnidadMedida() {
        return listaUnidadMedida;
    }

    public void setListaUnidadMedida(List<Unidad> listaUnidadMedida) {
        this.listaUnidadMedida = listaUnidadMedida;
    }

    public Unidad getSelectedUnidadDeMedida() {
        return selectedUnidadDeMedida;
    }

    public void setSelectedUnidadDeMedida(Unidad selectedUnidadDeMedida) {
        this.selectedUnidadDeMedida = selectedUnidadDeMedida;
    }

}
