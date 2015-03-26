/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.factories;

import com.ceutecsps.adminplanilla.documents.Deduccion;
import java.util.function.Supplier;

/**
 *
 * @author jhandal
 */
public class DeduccionFactory {

	private static final Supplier<Deduccion> deduccionSupplier = Deduccion::new;

	public static Deduccion productDeduccion() {
		return deduccionSupplier.get();
	}

}
