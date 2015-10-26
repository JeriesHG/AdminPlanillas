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
    private Float porcentajeDeducir;

    public Deduccion() {

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPorcentajeDeducir() {
        return porcentajeDeducir;
    }

    public void setPorcentajeDeducir(Float porcentajeDeducir) {
        this.porcentajeDeducir = porcentajeDeducir;
    }
}
