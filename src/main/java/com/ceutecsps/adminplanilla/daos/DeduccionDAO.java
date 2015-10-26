/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import com.ceutecsps.adminplanilla.connections.ConnectionManager;
import com.ceutecsps.adminplanilla.documents.Deduccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jhandal
 */
public class DeduccionDAO implements IDAO {

	private static final Logger LOGGER = Logger.getLogger(UnidadDAO.class.getName());

	/**
	 * Carga todas las unidades de medida activa
	 *
	 * @return lista de unidades de medida
	 */
	@Override
	public List<Deduccion> readAll() {
		List returnLIst = new ArrayList();
		String query = "SELECT * FROM adminPlanillas.deducciones";
		LOGGER.log(Level.INFO, "Cargando Lista de Deducciones");
		try (Connection connection = ConnectionManager.produceConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
				if (resultSet.getDate("Inactive_Date") == null) {
					Deduccion deduccion = new Deduccion();
					deduccion.setId(resultSet.getInt("Id"));
					deduccion.setDescripcion(resultSet.getString("Descripcion"));
					deduccion.setPorcentajeDeducir(resultSet.getFloat("Porcentaje"));
					deduccion.setInactive_date(resultSet.getDate("Inactive_Date"));
					returnLIst.add(deduccion);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
		}
		return returnLIst;
	}

	/**
	 * Se envia una unidad de medida, ya pasando las validaciones, y se inserta
	 * en la tabla Unidad
	 *
	 * @param object
	 * @return true si se inserto correctamente
	 */
	@Override
	public boolean insert(Object object) {
		int result = 0;
		Deduccion deduccion = (Deduccion) object;
		String query = "INSERT INTO adminPlanillas.deducciones (Descripcion,Porcentaje) values (?,?)";
		LOGGER.log(Level.INFO, "Insertando Deduccion con Descripcion: {0}", deduccion.getDescripcion());
		try (Connection connection = ConnectionManager.produceConnection();
				PreparedStatement pstmt = connection.prepareStatement(query);) {
			pstmt.setString(1, deduccion.getDescripcion());
			pstmt.setFloat(2, deduccion.getPorcentajeDeducir());
			result = pstmt.executeUpdate();
			LOGGER.log(Level.INFO, "Resultado Agregar Data - Result: {0}", result);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "insertarData Error: {0}", e);
		}
		return result > 0;
	}

	/**
	 * Se envia un unidad de medida para marcar como borrado la unidad de medida
	 * seleccionado Se llena el campo inactive_date para marcar como borrado
	 *
	 * @param object
	 * @return true si se elimino bien
	 */
	@Override
	public boolean delete(Object object) {
		int count = 0;
		Deduccion deduccion = (Deduccion) object;
		LOGGER.log(Level.INFO, "Eliminando Deduccion {0}", deduccion.getId());
		String query = "UPDATE adminPlanillas.deducciones SET Inactive_Date = ? WHERE Id = " + deduccion.getId();
		try (Connection connection = ConnectionManager.produceConnection();
				PreparedStatement pstmt = connection.prepareStatement(query);) {
			pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
			count = pstmt.executeUpdate();
			LOGGER.log(Level.INFO, "Resultado Eliminar Data - Row Count: {0}", count);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "eliminarData Error: {0}", e);
		}
		return count > 0;
	}

	/**
	 * Se envia una unidad de medida para actualizar ciertos descripcion
	 *
	 * @param object
	 * @return true si se actualizo bien
	 */
	@Override
	public boolean update(Object object) {
		Deduccion deduccion = (Deduccion) object;
		int result = 0;
		LOGGER.log(Level.INFO, "Actualizando Deduccion {0}", deduccion.getId());
		String query = "UPDATE adminPlanillas.deducciones SET Descripcion = ?, Porcentaje = ? WHERE Id = " + deduccion.getId();
		try (Connection connection = ConnectionManager.produceConnection();
				PreparedStatement pstmt = connection.prepareStatement(query);) {
			pstmt.setString(1, deduccion.getDescripcion());
			pstmt.setFloat(2, deduccion.getPorcentajeDeducir());
			result = pstmt.executeUpdate();
			LOGGER.log(Level.INFO, "Resultado Actualizar Data - Row Result: {0}", result);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Actualizar Data Error: {0}", e);
		}
		return result > 0;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Deduccion find(int id) {
		String query = "SELECT * FROM adminPlanillas.deducciones WHERE Id = " + id;
		LOGGER.log(Level.INFO, "Buscando unidad con ID {0}", id);
		try (Connection connection = ConnectionManager.produceConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
				Deduccion deduccion = new Deduccion();
				deduccion.setId(resultSet.getInt("Id"));
				deduccion.setDescripcion(resultSet.getString("Descripcion"));
				deduccion.setPorcentajeDeducir(resultSet.getFloat("Porcentaje"));
				deduccion.setInactive_date(resultSet.getDate("Inactive_Date"));
				return deduccion;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "MostrarData Error: {0}", e);
		}
		return new Deduccion();
	}

}

