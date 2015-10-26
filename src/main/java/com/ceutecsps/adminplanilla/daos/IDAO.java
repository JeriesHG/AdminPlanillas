/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanilla.daos;

import java.util.List;

/**
 *
 * @author Jeries
 */
public interface IDAO {
    
    public List readAll();
    public boolean insert(Object object);
    public boolean delete(Object object);
    public boolean update(Object object);
    public Object find(int id);
}
