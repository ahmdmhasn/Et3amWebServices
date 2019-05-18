package eg.iti.et3am.service.implementations;

import eg.iti.et3am.service.interfaces.UserService;
import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.Users;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
