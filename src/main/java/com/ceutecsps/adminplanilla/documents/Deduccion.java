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
public class Deduccion {

    private int id_deduccion;
    private String descripcion;

    //Verificar esto...no es certero..este tipo deduccion
    private String deduccion;
    private Date inactive_date;

    public Deduccion() {

    }

    public int getId_deduccion() {
        return id_deduccion;
    }

    public void setId_deduccion(int id_deduccion) {
        this.id_deduccion = id_deduccion;
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

    public Date getInactive_date() {
        return inactive_date;
    }

    public void setInactive_date(Date inactive_date) {
        this.inactive_date = inactive_date;
    }

}
