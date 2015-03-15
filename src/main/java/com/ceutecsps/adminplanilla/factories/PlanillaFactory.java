/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.factories;

import com.ceutecsps.adminplanilla.documents.Planilla;
import java.util.function.Supplier;

/**
 *
 * @author Jeries
 */
public class PlanillaFactory {
      private static final Supplier<Planilla> planillaFactory = Planilla::new;
    
    public static Planilla producePlanilla(){
        return planillaFactory.get();
    }
}
