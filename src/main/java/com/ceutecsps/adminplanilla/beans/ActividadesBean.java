/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.ActividadDAO;
import com.ceutecsps.adminplanilla.daos.LaborDAO;
import com.ceutecsps.adminplanilla.documents.Actividad;
import com.ceutecsps.adminplanilla.documents.Empleado;
import com.ceutecsps.adminplanilla.documents.Labor;
import com.ceutecsps.adminplanilla.factories.ActividadFactory;
import com.ceutecsps.adminplanilla.utilities.UtilityClass;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.omnifaces.util.Faces;
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
    private Map<String, Map<String, String>> mapShowSelectedLabor;
    private final ActividadDAO actDAO = new ActividadDAO();

    @PostConstruct
    public void inicializarClase() {
        listaFechas = new ArrayList();
        listaActividades = actDAO.readAll();
    }

    public void generarNuevaActividad() {
        try {
            mapShowSelectedLabor = new HashMap();
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

    public String salvarTodo() {
        listaActividades.stream().forEach((act) -> {
            actDAO.insert(act);
        });
        return "actividades?faces-redirect=true";
    }

    public void onCellEdit(CellEditEvent event) {
        Empleado empleado = Faces.evaluateExpressionGet("#{empleado}");
        String valorSeleccionado = event.getNewValue() + "";
        try {
            Labor labor = (Labor) new LaborDAO().find(Integer.parseInt(valorSeleccionado));
            if ((Actividad) actDAO.findActividadExistente(empleado.getId(), this.formatoEstandar.parse(event.getColumn().getHeaderText())) == null) {
                llenarMapaLaborSeleccionado(valorSeleccionado, event.getColumn().getHeaderText(), empleado);
                Actividad actividad = ActividadFactory.produceActiviad();
                actividad.setEmpleado(empleado);
                actividad.setLabor(labor);
                actividad.setStatus(false);
                actividad.setFecha(formatoEstandar.parse(event.getColumn().getHeaderText()));
                actDAO.findActividadExistente(empleado.getId(), this.formatoEstandar.parse(event.getColumn().getHeaderText()));
                listaActividades.add(actividad);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", empleado.getNombre()+" ya tiene una actividad asignada para ese dia."));
            }

        } catch (ParseException ex) {
            Logger.getLogger(ActividadesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que llena un mapa para mostrar la labor seleccionada, en donde se
     * envia el labor name, la fecha (header text) y el empleado para el nombre.
     * Es un mapa con otro mapa dentro, ya que un nombre de empleado puede tener
     * varias fechas. Se mapea elempleado con un mapa de fechas como KEY y
     * nombre de Labor como VALUE
     *
     * @param valorSeleccionado
     * @param headerText
     * @param empleado
     */
    private void llenarMapaLaborSeleccionado(String valorSeleccionado, String headerText, Empleado empleado) {
        Map<String, String> test = new HashMap();
        Labor labor = (Labor) new LaborDAO().find(Integer.parseInt(valorSeleccionado));
        test.put(headerText, labor.getNombre());
        this.mapShowSelectedLabor.put(empleado.getNombre(), test);
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

    public Map<String, Map<String, String>> getMapShowSelectedLabor() {
        return mapShowSelectedLabor;
    }

    public void setMapShowSelectedLabor(Map<String, Map<String, String>> mapShowSelectedLabor) {
        this.mapShowSelectedLabor = mapShowSelectedLabor;
    }

}
