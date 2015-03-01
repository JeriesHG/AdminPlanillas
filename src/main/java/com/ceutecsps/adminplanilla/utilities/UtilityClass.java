/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jeries
 */
public class UtilityClass {
    
    private static final Logger LOG = Logger.getLogger(UtilityClass.class.getName());
    
    public static String getURLParameter(String paramName){
       HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       String parameter = request.getParameter(paramName);
       LOG.log(Level.INFO, "Getting Parameter: {0} result = {1}", new Object[]{paramName, parameter});
       return parameter;
    }
    
}
