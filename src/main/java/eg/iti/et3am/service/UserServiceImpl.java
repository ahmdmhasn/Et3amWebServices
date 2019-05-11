/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service;

import eg.iti.et3am.dao.UserDao;
import eg.iti.et3am.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    @Override
    public long addEntity(User user) throws Exception {
        return userDao.addEntity(user);
    }

    @Override
    public User getEntityById(long id) throws Exception {
        return userDao.getEntityById(id);
    }

    @Override
    public List<User> getEntityList() throws Exception {
        return userDao.getEntityList();
    }

    @Override
    public boolean updateEntity(long id, User user) throws Exception {
        return userDao.updateEntity(id, user);
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        return userDao.deleteEntity(id);
    }
    
}
