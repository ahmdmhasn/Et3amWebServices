package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    Session session = null;
    Transaction tx = null;
    
    private Session checkCurrentSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        } else if (!session.isOpen()) {
            session = sessionFactory.openSession();
        } 
        session = sessionFactory.getCurrentSession();
        return session;
    }

    @Override
    public String addEntity(Users user) throws Exception {
        checkCurrentSession();
        tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        String id = (String) session.getIdentifier(user);
        return id;
    }
    
    @Override
    public int addDetailsEntity(UserDetails userDetails) throws Exception {
        checkCurrentSession();
        tx = session.beginTransaction();
        session.save(userDetails);
        tx.commit();
        int id = (int) session.getIdentifier(userDetails);
        return id;
    }

    @Override
    public Users getEntityById(String id) throws Exception {
        checkCurrentSession();
        Users user = (Users) session.load(Users.class, id);
        Users user2 = EntityCopier.getUser(user);
//        tx = session.getTransaction();
//        session.beginTransaction();
//        tx.commit();
        return user2;
    }

    @Override
    public UserDetails getDetailsEntityById(String id) throws Exception {
        throw new UnsupportedOperationException("Not tested yet. #AhmedHassan");
        /*
        checkCurrentSession();
        
        Criteria criteria = session.createCriteria(UserDetails.class).
                createAlias("users", "users").
                createAlias("users.userId", "id").
                add(Restrictions.eq("id", id));
        UserDetails user = (UserDetails) criteria.uniqueResult();
        UserDetails user2 = EntityCopier.getUserDetails(Collections.singleton(user)).
                iterator().next();
        
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        return user2;
*/
    }
    
    @Override
    public List<Users> getEntityList() throws Exception {
        checkCurrentSession();
        List<Users> userList = session.createCriteria(Users.class).list();

        // Create another array to be sent on response
        List<Users> userList2 = new ArrayList<>();
        for (Users user : userList) {
            userList2.add(EntityCopier.getUser(user));
        }
        return userList2;
    }

    @Override
    public Users updateEntity(Users user) throws Exception {
        checkCurrentSession();
        
        Users user2 = (Users) session.load(Users.class, user.getUserId());
        user2.setVerified(user.getVerified());
        user2.setUserStatus(user.getUserStatus());
        Users userToReturn = EntityCopier.getUser(user2);
        
        session.flush();
        return userToReturn;
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        checkCurrentSession();
        
        Object o = session.load(Users.class, id);
        tx = session.getTransaction();
        session.beginTransaction();
        session.delete(o);
        tx.commit();
        return true;
    }

    @Override
    public boolean isEmailValid(String email) throws Exception {
        checkCurrentSession();
                
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userEmail", email));

        Users user = (Users) criteria.uniqueResult();
        if (user == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUsernameValid(String username) throws Exception {
        checkCurrentSession();
        
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userName", username));
        Users user = (Users) criteria.uniqueResult();

        if (user == null) {
            return true;
        }
        return false;
    }

    @Override
    public Users login(String email, String password) throws Exception {
        checkCurrentSession();
        
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userEmail", email));
        criteria.add(Restrictions.eq("password", password));

        Users user = (Users) criteria.uniqueResult();
        return EntityCopier.getUser(user);
    }

}
