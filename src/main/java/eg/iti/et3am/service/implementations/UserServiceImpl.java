package eg.iti.et3am.service.implementations;

import eg.iti.et3am.service.interfaces.UserService;
import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Users addEntity(Users user) throws Exception {
        user.setUserStatus(1);
        user.setVerified(0);
        
        String userId = userDao.addEntity(user);
        UserDetails ud = new UserDetails();
        ud.setUsers(userDao.getEntityById(userId));
        userDao.addDetailsEntity(ud);

        return userDao.getEntityById(userId);
    }

    @Override
    @Transactional
    public Users getEntityById(String id) throws Exception {
        return userDao.getEntityById(id);
    }

    @Override
    @Transactional
    public List<Users> getEntityList() throws Exception {
        return userDao.getEntityList();
    }
    
    @Override
    @Transactional
    public void updateEntity(Users user) {
        userDao.updateEntity(user);
    }

    @Override
    @Transactional
    public Users updateEntity(UserDetails userDetails, String id) throws Exception {
        return userDao.updateEntity(userDetails, id);
    }
    
    @Override
    @Transactional
    public void updateUserVerification(UserDetails userDetails, String id) throws Exception {
        userDao.updateEntity(userDetails, id);
        userDao.updateUserVerification(id, 2);
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

    @Override
    @Transactional
    public List<Users> getEntityListToBeVerified() throws Exception {
        return userDao.getEntityListToBeVerified();

    }

    @Override
    @Transactional
    public boolean verifyUser(String userID,int verifiedID) throws Exception {
        return userDao.verifyUser(userID,verifiedID);
    }

    @Override
    public boolean requestPasswordReset(String email) {
       return userDao.requestPasswordReset(email);
    }
}
