/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.documents;

import com.google.gson.Gson;
import java.util.Date;

/**
 *
 * @author Jeries
 */
public class AbstractDoc {
    
    private int id;
    private Date inactive_date;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getInactive_date() {
        return inactive_date;
    }

    public void setInactive_date(Date inactive_date) {
        this.inactive_date = inactive_date;
    }

    public String getValue() {
        return new Gson().toJson(this);
    }
    
}
