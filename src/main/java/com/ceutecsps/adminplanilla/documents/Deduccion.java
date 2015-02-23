/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.documents;

/**
 *
 * @author Jeries
 */
public class Deduccion extends AbstractDoc{

    private String descripcion;
    private String deduccion;
    private int porcentajeDeducir;

    public Deduccion() {

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDeduccion() {
        return deduccion;
    }

    public void setDeduccion(String deduccion) {
        this.deduccion = deduccion;
    }

    public int getPorcentajeDeducir() {
        return porcentajeDeducir;
    }

    public void setPorcentajeDeducir(int porcentajeDeducir) {
        this.porcentajeDeducir = porcentajeDeducir;
    }
}
