/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.factories;

import com.ceutecsps.adminplanilla.documents.Labor;
import java.util.function.Supplier;

/**
 *
 * @author Jeries
 */
public class LaborFactory {
    
    private static final Supplier<Labor> laborFactory = Labor::new;
    
    public static Labor produceLabor(){
        return laborFactory.get();
    }
}
