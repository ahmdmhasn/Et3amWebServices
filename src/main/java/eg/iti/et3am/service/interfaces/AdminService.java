/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.model.Admins;
import eg.iti.et3am.model.RestaurantAdmin;

/**
 *
 * @author Nesma
 */
public interface AdminService {
    
    public String addResturantAdmin(String email,String password,int restaurantId) throws Exception;
    public Admins login(String  email, String password) throws Exception;

}
