/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.factories;

import com.ceutecsps.adminplanilla.documents.UnidadDeMedida;
import java.util.function.Supplier;

/**
 *
 * @author Jeries
 */
public class UnidadDeMedidaFactory {
    
    private final static Supplier<UnidadDeMedida> unidadMedidaSupplier = UnidadDeMedida::new;
    
    public static UnidadDeMedida produceUnidadDeMedida(){
        return unidadMedidaSupplier.get();
    }
}
