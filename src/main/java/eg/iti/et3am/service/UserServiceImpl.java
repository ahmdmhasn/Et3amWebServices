/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service;

import eg.iti.et3am.dao.UserDao;
import eg.iti.et3am.model.User;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    @Transactional
    public long addEntity(User user) throws Exception {
        return userDao.addEntity(user);
    }

    @Override
    @Transactional
    public User getEntityById(long id) throws Exception {
        return userDao.getEntityById(id);
    }

    @Override
    @Transactional
    public List<User> getEntityList() throws Exception {
        return userDao.getEntityList();
    }

    @Override
    @Transactional
    public boolean updateEntity(long id, User user) throws Exception {
        return userDao.updateEntity(id, user);
    }

    @Override
    @Transactional
    public boolean deleteEntity(long id) throws Exception {
        return userDao.deleteEntity(id);
    }
    
}
