/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service;

import eg.iti.et3am.model.User;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface UserService {
    
    long addEntity(User user) throws Exception;
    User getEntityById(long id) throws Exception;
    List<User> getEntityList() throws Exception;
    boolean updateEntity(long id, User user) throws Exception;
    boolean deleteEntity(long id) throws Exception;
    
}
