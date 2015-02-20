/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import java.io.Serializable;

/**
 *
 * @author Jeries
 */
public abstract class AbstractBean implements Serializable{
    
    private boolean updateable; 
    
    public abstract void inicializarClase();
    public abstract void mostrarData();
    public abstract String agregarData(Object objeto);
    public abstract String modificarData(Object objeto, boolean saveable);
    public abstract String eliminarData(Object objeto);

    public boolean isUpdateable() {
        return updateable;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }
    
    
}
