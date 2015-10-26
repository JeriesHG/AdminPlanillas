/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.beans;

import com.ceutecsps.adminplanilla.daos.DeduccionDAO;
import com.ceutecsps.adminplanilla.documents.Deduccion;
import com.ceutecsps.adminplanilla.factories.DeduccionFactory;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author jhandal
 */
@Named
@ViewScoped
public class DeduccionBean extends AbstractBean {

	private List<Deduccion> listaDeduccion;
	private Deduccion selectedDeduccion;
	private DeduccionDAO deduccionDAO = new DeduccionDAO();

	@PostConstruct
	@Override
	public void inicializarClase() {
		mostrarData();
		selectedDeduccion = DeduccionFactory.productDeduccion();
	}

	@Override
	public void mostrarData() {
		listaDeduccion = deduccionDAO.readAll();
	}

	@Override
	public String agregarData(Object objeto) {
		boolean result = deduccionDAO.insert(((Deduccion) objeto));
		selectedDeduccion = DeduccionFactory.productDeduccion();
		return "deduccion?faces-redirect=true&amp;result=" + result;
	}

	@Override
	public String modificarData(Object objeto, boolean updateable) {
		if (updateable) {
			this.setUpdateable(false);
			boolean result = deduccionDAO.update(((Deduccion) objeto));
			return "deduccion?faces-redirect=true&amp;result=" + result + "&amp;id=" + ((Deduccion) objeto).getId();
		}
		return "deduccion?faces-redirect=true&amp;saveable=true&amp;id=" + ((Deduccion) objeto).getId();
	}

	@Override
	public String eliminarData(Object objeto) {
		boolean result = deduccionDAO.delete(((Deduccion) objeto));
		return "deduccion?faces-redirect=true&amp;result=" + result;
	}

	@Override
	public void onRowEdit(RowEditEvent event) {
		modificarData(event.getObject(), true);
	}

	@Override
	public void onRowCancel(RowEditEvent event) {
	}

	public List<Deduccion> getListaDeduccion() {
		return listaDeduccion;
	}

	public void setListaDeduccion(List<Deduccion> listaDeduccion) {
		this.listaDeduccion = listaDeduccion;
	}

	public Deduccion getSelectedDeduccion() {
		return selectedDeduccion;
	}

	public void setSelectedDeduccion(Deduccion selectedDeduccion) {
		this.selectedDeduccion = selectedDeduccion;
	}

}
