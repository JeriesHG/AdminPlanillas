/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.documents;

import java.util.Date;

/**
 *
 * @author Jeries
 */
public class UnidadDeMedida {

    private int id_unidadMedida;
    private String descripcion;
    private String unidad;
    private Date inactive_date;

    public UnidadDeMedida() {

    }

    public int getId_unidadMedida() {
        return id_unidadMedida;
    }

    public void setId_unidadMedida(int id_unidadMedida) {
        this.id_unidadMedida = id_unidadMedida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Date getInactive_date() {
        return inactive_date;
    }

    public void setInactive_date(Date inactive_date) {
        this.inactive_date = inactive_date;
    }

}
