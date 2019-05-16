/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao;

import eg.iti.et3am.model.Users;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    Session session = null;
    Transaction tx = null;

    @Override
    public String addEntity(Users user) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        System.out.println(session.save(user) + "~~~~~~~~~~~~");
        tx.commit();
        String id = (String) session.getIdentifier(user);
        System.out.println(user.getUserId() + "\t" + id + "\t" + "~~~~~~~~~~~~~~~~~~~~");
        session.close();
        return id;
    }

    @Override
    public Users getEntityById(long id) throws Exception {
        session = sessionFactory.openSession();
        Users user = (Users) session.load(Users.class, id);
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        return user;
    }

    @Override
    public List<Users> getEntityList() throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<Users> userList = session.createCriteria(Users.class).list();

        // Create another array to be sent on response
        List<Users> userList2 = new ArrayList<>();
        for (Users user : userList) {
            Users user2 = new Users();
            user2.setUserId(user.getUserId());
            user2.setUserName(user.getUserName());
            user2.setPassword(user.getPassword());
            user2.setUserEmail(user.getUserEmail());
            user2.setVerified(user.getVerified());
            userList2.add(user2);
        }

        tx.commit();
        session.close();
        return userList2;
    }

    @Override
    public boolean updateEntity(long id, Users user) throws Exception {
        session = sessionFactory.openSession();
        Users user2 = (Users) session.load(Users.class, id);
        user2.setUserEmail(user.getUserEmail());
        user2.setVerified(user.getVerified());
        user2.setPassword(user.getPassword());
        user2.setUserName(user.getUserName());
        session.flush();
        return true;
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        session = sessionFactory.openSession();
        Object o = session.load(Users.class, id);
        tx = session.getTransaction();
        session.beginTransaction();
        session.delete(o);
        tx.commit();
        return true;
    }

    @Override
    public boolean isEmailValid(String email) throws Exception {
        session = sessionFactory.openSession();
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
        session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userName", username));

        Users user = (Users) criteria.uniqueResult();

        if (user == null) {
            return true;
        }
        
        return false;
    }
}
