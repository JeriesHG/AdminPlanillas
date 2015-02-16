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
public interface BasicOperations extends Serializable{
    
    public void inicializarClase();
    public void mostrarData();
    public String agregarData(Object objeto);
    public String modificarData(Object objeto);
    public String eliminarData(Object objeto);
}
