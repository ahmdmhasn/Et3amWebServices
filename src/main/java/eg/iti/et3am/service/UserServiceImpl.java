/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service;

import eg.iti.et3am.dao.UserDao;
import eg.iti.et3am.model.Users;
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
    public String addEntity(Users user) throws Exception {
        return userDao.addEntity(user);
    }

    @Override
    @Transactional
    public Users getEntityById(long id) throws Exception {
        return userDao.getEntityById(id);
    }

    @Override
    @Transactional
    public List<Users> getEntityList() throws Exception {
        return userDao.getEntityList();
    }

    @Override
    @Transactional
    public boolean updateEntity(long id, Users user) throws Exception {
        return userDao.updateEntity(id, user);
    }

    @Override
    @Transactional
    public boolean deleteEntity(long id) throws Exception {
        return userDao.deleteEntity(id);
    }

    @Override
    @Transactional
    public boolean isEmailValid(String email) throws Exception {
        return userDao.isEmailValid(email);
    }
    
    @Override
    @Transactional
    public boolean isUsernameValid(String username) throws Exception {
        return userDao.isUsernameValid(username);
    }
    
    @Override
    @Transactional
    public Users login(String email, String password) throws Exception {
        return userDao.login(email, password);
    }
}
