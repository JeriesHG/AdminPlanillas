/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.documents;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jeries
 */
public class Planilla extends AbstractDoc{

    private Date fecha_inicio;
    private Date fecha_fin;
    private boolean creada;
    private List<Actividad> listaActividades;
    private List<Deduccion> listaDeducciones;

    public Planilla() {

    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = new java.sql.Date(fecha_inicio.getTime());
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = new java.sql.Date(fecha_fin.getTime());
    }

    public boolean isCreada() {
        return creada;
    }

    public void setCreada(boolean creada) {
        this.creada = creada;
    }

    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    public List<Deduccion> getListaDeducciones() {
        return listaDeducciones;
    }

    public void setListaDeducciones(List<Deduccion> listaDeducciones) {
        this.listaDeducciones = listaDeducciones;
    }
    
    public float getTotal(){
        float total = 0;
        for(Actividad act : listaActividades){
            total += Float.parseFloat(act.getTotal());
        }
		for(Deduccion deduccion : listaDeducciones){
			total -= total/deduccion.getPorcentajeDeducir();
		}
        return total;
    }

}
