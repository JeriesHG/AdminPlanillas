/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.documents.Labor;
import com.ceutecsps.adminplanilla.factories.LaborFactory;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Jeries
 */
@Named
@ViewScoped
public class LaborBean extends AbstractBean {

    private Labor selectedLabor;
    private List<Labor> listaLabores;

    @Override
    public void inicializarClase() {
        selectedLabor = LaborFactory.produceLabor();
        mostrarData();
    }

    @Override
    public void mostrarData() {
        listaLabores = new ArrayList();
    }

    @Override
    public String agregarData(Object objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String modificarData(Object objeto, boolean updateable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminarData(Object objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
