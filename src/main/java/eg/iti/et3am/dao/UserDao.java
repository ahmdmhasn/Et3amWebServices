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
    
    String addEntity(Users user) throws Exception;
    Users getEntityById(long id) throws Exception;
    List<Users> getEntityList() throws Exception;
    boolean updateEntity(long id, Users user) throws Exception;
    boolean deleteEntity(long id) throws Exception;
    
}
