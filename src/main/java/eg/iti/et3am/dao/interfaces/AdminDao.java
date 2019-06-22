/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.model.Admins;
import eg.iti.et3am.model.Users;

/**
 *
 * @author Nesma
 */
public interface AdminDao {
       /*---Login method using email & password---*/
    Admins login(String email, String password) throws Exception;
    
    
    
}
