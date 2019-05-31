package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;
import java.util.ArrayList;
import java.util.List;
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

//    Session session = null;
//    Transaction tx = null;
    @Override
    public String addEntity(Users user) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        String id = null;
        try {
            session.save(user);
            id = (String) session.getIdentifier(user);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public int addDetailsEntity(UserDetails userDetails) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int id = 0;

        try {
            session.save(userDetails);
            tx.commit();
            id = (int) session.getIdentifier(userDetails);
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public Users getEntityById(String id) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Users user2 = null;
        try {
            Users user = (Users) session.load(Users.class, id);
            user2 = EntityCopier.getUser(user);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
        } finally {
            session.close();
        }
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
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        // Create another array to be sent on response
        List<Users> userList2 = new ArrayList<>();

        try {
            List<Users> userList = session.createCriteria(Users.class).list();
            for (Users user : userList) {
                userList2.add(EntityCopier.getUser(user));
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
        } finally {
            session.close();
        }
        return userList2;
    }

    @Override
    public Users updateEntity(Users user) throws Exception {
        Session session = sessionFactory.openSession();
        Users user2 = (Users) session.load(Users.class, user.getUserId());
        user2.setVerified(user.getVerified());
        user2.setUserStatus(user.getUserStatus());
        Users userToReturn = EntityCopier.getUser(user2);

        session.flush();
        session.close();
        return userToReturn;
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Object o = session.load(Users.class, id);
            session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isEmailValid(String email) throws Exception {
        Session session = sessionFactory.openSession();

        try {
            Criteria criteria = session.createCriteria(Users.class);
            criteria.add(Restrictions.eq("userEmail", email));

            Users user = (Users) criteria.uniqueResult();
            if (user == null) {
                return true;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean isUsernameValid(String username) throws Exception {
        Session session = sessionFactory.openSession();

        try {
            Criteria criteria = session.createCriteria(Users.class);
            criteria.add(Restrictions.eq("userName", username));
            Users user = (Users) criteria.uniqueResult();

            if (user == null) {
                return true;
            }
        } catch (RuntimeException ex) {
            return false;
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Users login(String email, String password) throws Exception {
        Session session = sessionFactory.openSession();

        try {
            Criteria criteria = session.createCriteria(Users.class);
            criteria.add(Restrictions.eq("userEmail", email));
            criteria.add(Restrictions.eq("password", password));

            Users user = (Users) criteria.uniqueResult();
            return EntityCopier.getUser(user);
        } catch (RuntimeException ex) {
            return null;
        } finally {
            session.close();
        }
    }

}
