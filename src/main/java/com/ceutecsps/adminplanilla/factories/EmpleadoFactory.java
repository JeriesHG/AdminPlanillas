/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.factories;

import com.ceutecsps.adminplanilla.documents.Empleado;
import java.util.function.Supplier;

/**
 *
 * @author Jeries
 */
public class EmpleadoFactory {
    
    private final static Supplier<Empleado> empleadoSupplier = Empleado::new;
    
    public static Empleado produceEmpleado(){
        return empleadoSupplier.get();
    }
    
}
