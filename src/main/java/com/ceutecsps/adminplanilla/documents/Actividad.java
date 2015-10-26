/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.documents;

import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Jeries
 */
public class Actividad extends AbstractDoc {

    private Date fecha;
    private boolean status;
    private Empleado empleado;
    private Labor labor;
    private String trabajoRealizado;

    public Actividad() {

    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = new java.sql.Date(fecha.getTime());
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Labor getLabor() {
        return labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }

    public String getTrabajoRealizado() {
        return trabajoRealizado;
    }

    public void setTrabajoRealizado(String trabajoRealizado) {
        this.trabajoRealizado = trabajoRealizado;
    }

    public String getTotal() {
        if (!StringUtils.isBlank(trabajoRealizado)) {
            float workDone = Float.parseFloat(trabajoRealizado);
            float laborWorth = this.labor.getPrecio();
            return String.valueOf((workDone * laborWorth));
        } else {
            return "";
        }
    }

}
