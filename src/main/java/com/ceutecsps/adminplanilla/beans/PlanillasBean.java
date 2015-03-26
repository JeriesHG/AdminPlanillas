/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.ActividadDAO;
import com.ceutecsps.adminplanilla.daos.PlanillaDAO;
import com.ceutecsps.adminplanilla.documents.Actividad;
import com.ceutecsps.adminplanilla.documents.Deduccion;
import com.ceutecsps.adminplanilla.documents.Planilla;
import com.ceutecsps.adminplanilla.factories.PlanillaFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Jeries
 */
@Named
@ViewScoped
public class PlanillasBean implements Serializable {

    private Date fromDate, toDate;
    private boolean renderizarTablaActividad;
    private List<Actividad> listaActividades;
    private List<Planilla> listaPlanillas;
    private List<Deduccion> listaDeducciones;
    private ActividadDAO actDAO = new ActividadDAO();
    private PlanillaDAO planDAO = new PlanillaDAO();

    @PostConstruct
    private void inicializarClase(){
        listaPlanillas = planDAO.readAll();
	  listaDeducciones = new ArrayList();
	}

    public void crearPlanilla() {
        if (Days.daysBetween(new DateTime(fromDate), new DateTime(toDate)).getDays() == 4) {
            if (planDAO.findBetweenDates(new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! Ya existe una planilla para esos dias", null));
                return;
            }
            renderizarTablaActividad = true;
            listaActividades = actDAO.findActividadExistente(fromDate, toDate);
        } else {
            renderizarTablaActividad = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! Por favor escoger 1 semana para crear planilla", null));
        }
    }

    public void salvarPlanilla() {
        listaActividades.stream().map((act) -> {
            act.setStatus(true);
            return act;
        }).forEach((act) -> {
            System.out.println("BEEP: " + act.getTrabajoRealizado());
            this.actDAO.update(act);
        });
        Planilla planilla = PlanillaFactory.producePlanilla();
        planilla.setListaActividades(listaActividades);
        planilla.setFecha_inicio(fromDate);
        planilla.setFecha_fin(toDate);
        planilla.setCreada(true);
	   planilla.setListaDeducciones(listaDeducciones);
        boolean result = planDAO.insert(planilla);
        if (result) {
            try {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Planilla Guardada!", null));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().getExternalContext().redirect("planillas.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(PlanillasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public String eliminarData(Object object){
        boolean result = this.planDAO.delete(object);
        return "listaPlanillas?faces-redirect=true&amp;result="+result;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isRenderizarTablaActividad() {
        return renderizarTablaActividad;
    }

    public void setRenderizarTablaActividad(boolean renderizarTablaActividad) {
        this.renderizarTablaActividad = renderizarTablaActividad;
    }

    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    public List<Planilla> getListaPlanillas() {
        return listaPlanillas;
    }

    public void setListaPlanillas(List<Planilla> listaPlanillas) {
        this.listaPlanillas = listaPlanillas;
    }

	public List<Deduccion> getListaDeducciones() {
		return listaDeducciones;
	}

	public void setListaDeducciones(List<Deduccion> listaDeducciones) {
		this.listaDeducciones = listaDeducciones;
	}
    
	
    

}
