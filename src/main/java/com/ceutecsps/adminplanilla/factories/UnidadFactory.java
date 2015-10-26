/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.factories;

import com.ceutecsps.adminplanilla.documents.Unidad;
import java.util.function.Supplier;

/**
 *
 * @author Jeries
 */
public class UnidadFactory {
    
    private final static Supplier<Unidad> unidadMedidaSupplier = Unidad::new;
    
    public static Unidad produceUnidadDeMedida(){
        return unidadMedidaSupplier.get();
    }
}
