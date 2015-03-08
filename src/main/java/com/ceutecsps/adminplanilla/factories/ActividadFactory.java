/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.factories;

import com.ceutecsps.adminplanilla.documents.Actividad;
import java.util.function.Supplier;

/**
 *
 * @author Jeries
 */
public class ActividadFactory {

    private static final Supplier<Actividad> actividadSupplier = Actividad::new;

    public static Actividad produceActiviad() {
        return actividadSupplier.get();
    }

}
