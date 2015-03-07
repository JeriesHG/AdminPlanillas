/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.documents.Actividad;
import com.ceutecsps.adminplanilla.utilities.UtilityClass;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Jeries
 */
@Named
@ViewScoped
public class ActividadesBean implements Serializable {

    private Date fromDate, toDate;
    private List<String> listaFechas;
    private List<Actividad> listaActividades;
    private final SimpleDateFormat formatoEstandar = new SimpleDateFormat("yyyy/MM/dd");
    private boolean renderizarTablaActividad;

    @PostConstruct
    public void inicializarClase() {
        listaFechas = new ArrayList();
        listaActividades = new ArrayList();
    }

    public void generarNuevaActividad() {
        try {
            if (Days.daysBetween(new DateTime(fromDate), new DateTime(toDate)).getDays() == 4) {
                generarListaFechas(fromDate, toDate);
                renderizarTablaActividad = true;
            } else {
                renderizarTablaActividad = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Por favor escoger 1 semana solamente (5 dias)"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ActividadesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarListaFechas(Date from, Date to) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        while (cal.getTime().before(to) || cal.getTime().equals(to)) {
            listaFechas.add(formatoEstandar.format(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        UtilityClass.deleteDuplicate(listaFechas);
    }

    public void onCellEdit(CellEditEvent event) {
        System.out.println(event.getNewValue());
    }

    public List<String> getListaFechas() {
        return listaFechas;
    }

    public void setListaFechas(List<String> listaFechas) {
        this.listaFechas = listaFechas;
    }

    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
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

}
