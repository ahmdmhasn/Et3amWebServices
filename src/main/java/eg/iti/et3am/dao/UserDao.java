/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao;

import eg.iti.et3am.model.Users;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface UserDao {
    
    /*---Add new user---*/
    String addEntity(Users user) throws Exception;
    
    /*---Get user by id---*/
    Users getEntityById(long id) throws Exception;
    
    /*---Get all user---*/
    List<Users> getEntityList() throws Exception;
    
    /*---Update a user by id---*/
    boolean updateEntity(long id, Users user) throws Exception;
    
    /*---Delete a user by id---*/
    boolean deleteEntity(long id) throws Exception;
    
    /*---Check if the same email exists---*/
    boolean isEmailValid(String email) throws Exception;
    
    /*---Check if the same username exists---*/
    boolean isUsernameValid(String username) throws Exception;
    
    /*---Login method using email & password---*/
    Users login(String email, String password) throws Exception;
}
