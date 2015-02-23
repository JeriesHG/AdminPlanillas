/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.UnidadDeMedidaDAO;
import com.ceutecsps.adminplanilla.documents.UnidadDeMedida;
import com.ceutecsps.adminplanilla.factories.UnidadDeMedidaFactory;
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
public class UnidadMedidaBean extends AbstractBean {

    private List<UnidadDeMedida> listaUnidadMedida;
    private UnidadDeMedida selectedUnidadDeMedida;
    private UnidadDeMedidaDAO unidadDAO = new UnidadDeMedidaDAO();

    @PostConstruct
    @Override
    public void inicializarClase() {
        mostrarData();
        selectedUnidadDeMedida = UnidadDeMedidaFactory.produceUnidadDeMedida();
    }

    @Override
    public void mostrarData() {
        listaUnidadMedida = unidadDAO.readUnidadMedidas();
    }

    @Override
    public String agregarData(Object objeto) {
        boolean result = unidadDAO.agregarUnidadDeMedida(((UnidadDeMedida) objeto));
        selectedUnidadDeMedida = UnidadDeMedidaFactory.produceUnidadDeMedida();
        return "unidadMedida?faces-redirect=true&amp;result=" + result;
    }

    @Override
    public String modificarData(Object objeto, boolean updateable) {
        if (updateable) {
            this.setUpdateable(false);
            boolean result = unidadDAO.actualizarUnidadDeMedida(((UnidadDeMedida) objeto));
            return "unidadMedida?faces-redirect=true&amp;result=" + result + "&amp;id=" + ((UnidadDeMedida) objeto).getId();
        }
        return "unidadMedida?faces-redirect=true&amp;saveable=true&amp;id=" + ((UnidadDeMedida) objeto).getId();
    }

    @Override
    public String eliminarData(Object objeto) {
        boolean result = unidadDAO.eliminarUnidadMedida(((UnidadDeMedida) objeto));
        return "unidadMedida?faces-redirect=true&amp;result=" + result;
    }

    public List<UnidadDeMedida> getListaUnidadMedida() {
        return listaUnidadMedida;
    }

    public void setListaUnidadMedida(List<UnidadDeMedida> listaUnidadMedida) {
        this.listaUnidadMedida = listaUnidadMedida;
    }

    public UnidadDeMedida getSelectedUnidadDeMedida() {
        return selectedUnidadDeMedida;
    }

    public void setSelectedUnidadDeMedida(UnidadDeMedida selectedUnidadDeMedida) {
        this.selectedUnidadDeMedida = selectedUnidadDeMedida;
    }

}
