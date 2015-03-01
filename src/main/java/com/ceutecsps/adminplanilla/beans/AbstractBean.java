/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import java.io.Serializable;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Jeries
 */
public abstract class AbstractBean implements Serializable {

    private boolean updateable;

    public abstract void inicializarClase();

    public abstract void mostrarData();

    public abstract String agregarData(Object objeto);

    public abstract String modificarData(Object objeto, boolean updateable);

    public abstract String eliminarData(Object objeto);

    
    //ESTOS DOS METODOS SON USADOS PARA ACTUALZIACION DE DATOS EN TABLAS
    public abstract void onRowEdit(RowEditEvent event);

    public abstract void onRowCancel(RowEditEvent event);

    public boolean isUpdateable() {
        return updateable;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

}
