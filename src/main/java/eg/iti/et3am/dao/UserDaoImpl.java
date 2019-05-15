/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao;

import eg.iti.et3am.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
    public long addEntity(User user) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        System.out.println(session.save(user) + "~~~~~~~~~~~~");
        tx.commit();
        Long id = (Long) session.getIdentifier(user);
        System.out.println(user.getUserId() +"\t" + id + "\t" + "~~~~~~~~~~~~~~~~~~~~");
        session.close();
        return id;
    }
    
    @Override
    public User getEntityById(long id) throws Exception {
        session = sessionFactory.openSession();
        User user = (User) session.load(User.class, id);
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        return user;
    }
    
    @Override
    public List<User> getEntityList() throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<User> userList = session.createCriteria(User.class).list();
        tx.commit();
        session.close();
        return userList;
    }
    
    @Override
    public boolean updateEntity(long id, User user) throws Exception {
        session = sessionFactory.openSession();
        User user2 = (User) session.load(User.class, id);
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
        Object o = session.load(User.class, id);
        tx = session.getTransaction();
        session.beginTransaction();
        session.delete(o);
        tx.commit();
        return true;
    }
    
}
