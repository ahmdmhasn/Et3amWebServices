package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    Session session = null;
    Transaction tx = null;

    @Override
    public String addEntity(Users user) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        session.save(user);
        String id = (String) session.getIdentifier(user);
        
        tx.commit();
        return id;
    }

    @Override
    public int addDetailsEntity(UserDetails userDetails) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        session.save(userDetails);
        int id = (int) session.getIdentifier(userDetails);

        tx.commit();
        return id;
    }

    @Override
    public Users getEntityById(String id) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        Users user = (Users) session.load(Users.class, id);
        Users user2 = EntityCopier.getUser(user);
        
        tx.commit();
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
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        // Create another array to be sent on response
        List<Users> userList2 = new ArrayList<>();

        List<Users> userList = session.createCriteria(Users.class).list();
        for (Users user : userList) {
            userList2.add(EntityCopier.getUser(user));
        }
        tx.commit();
        return userList2;
    }

    @Override
    public Users updateEntity(UserDetails ud, String id) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        Users user = (Users) session.load(Users.class, id);
        Criteria criteria = session.createCriteria(UserDetails.class).
                add(Restrictions.eq("users", user));
        UserDetails userDetails = (UserDetails) criteria.uniqueResult();

        userDetails.setBirthdate(ud.getBirthdate());
        userDetails.setJob(ud.getJob());
        userDetails.setMobileNumber(ud.getMobileNumber());
        userDetails.setNationalId(ud.getNationalId());
        userDetails.setNationalIdBack(ud.getNationalIdBack());
        userDetails.setNationalIdFront(ud.getNationalIdFront());
        userDetails.setProfileImage(ud.getProfileImage());

        session.update(userDetails);
        Users tempUser = EntityCopier.getUser(user);
        tx.commit();
        return tempUser;
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        Object o = session.load(Users.class, id);
        session.beginTransaction();
        session.delete(o);
        tx.commit();
        return true;
    }

    @Override
    public boolean isEmailValid(String email) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userEmail", email));

        Users user = (Users) criteria.uniqueResult();
        tx.commit();
        
        if (user == null) {
            return true;
        }
        
        return false;
    }

    @Override
    public boolean isUsernameValid(String username) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userName", username));
        Users user = (Users) criteria.uniqueResult();
        tx.commit();
        
        if (user == null) {
            return true;
        }
        
        return false;
    }

    @Override
    public Users login(String email, String password) throws Exception {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        Users user = null;
        try {
            Criteria criteria = session.createCriteria(Users.class);
            criteria.add(Restrictions.eq("userEmail", email));
            criteria.add(Restrictions.eq("password", password));

            user = EntityCopier.getUser((Users) criteria.uniqueResult());
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }

        return user;

    }

    @Override
    public List<Users> getEntityListToBeVerified() throws Exception {
        int verified = 0;
        session = sessionFactory.getCurrentSession();

        tx = session.beginTransaction();
        List<Users> userList = new ArrayList<>();
        Criteria criteria = session.createCriteria(Users.class);
        criteria.createAlias("userDetailses", "uDetails")
                .add(Restrictions.eq("verified", verified)).add(Restrictions.isNotNull("uDetails.nationalIdFront"))
                .add(Restrictions.isNotNull("uDetails.nationalIdBack"));

        List<Users> users = criteria.list();
        System.out.println(users.size());
        for (Users user : users) {
            userList.add(EntityCopier.getUser(user));
        }
        tx.commit();
        System.out.println(users.size());

        return userList;
    }

    @Override
    public boolean verifyUser(String id) throws Exception {
     session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        Users user = (Users) session.load(Users.class, id);
        user.setVerified(1);
        session.update(user);
        tx.commit();
        return true;
    }
    

}
