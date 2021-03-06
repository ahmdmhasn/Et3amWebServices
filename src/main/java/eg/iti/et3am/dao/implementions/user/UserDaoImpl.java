package eg.iti.et3am.dao.implementions.user;

import eg.iti.et3am.dao.interfaces.user.UserDao;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;
import eg.iti.et3am.utils.Mail;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
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

    @Autowired(required = true)
    private Mail mail;

    Session session = null;
    Transaction tx = null;

    @Override
    public String addEntity(Users user) throws Exception {
        session = sessionFactory.getCurrentSession();

        session.save(user);
        return (String) session.getIdentifier(user);

    }

    @Override
    public int addDetailsEntity(UserDetails userDetails) throws Exception {
        session = sessionFactory.getCurrentSession();
        session.save(userDetails);
        return (int) session.getIdentifier(userDetails);
    }

    @Override
    public Users getEntityById(String id) throws Exception {
        session = sessionFactory.getCurrentSession();
        Users user = (Users) session.load(Users.class, id);
        return (user != null) ? EntityCopier.getUser(user) : null;

    }

    @Override
    public List<Users> getEntityList() throws Exception {
        session = sessionFactory.getCurrentSession();

        List<Users> userList2 = new ArrayList<>();
        List<Users> userList = session.createCriteria(Users.class).list();
        for (Users user : userList) {
            userList2.add(EntityCopier.getUser(user));
        }
        return userList2;
    }

    @Override
    public void updateEntity(Users user) {
        session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void updateDetailsEntity(UserDetails ud, String id) throws Exception {
        session = sessionFactory.getCurrentSession();

        UserDetails userDetails = (UserDetails) session.createCriteria(UserDetails.class)
                .createAlias("users", "u")
                .add(Restrictions.eq("u.userId", id))
                .uniqueResult();

        String mobNumber = ud.getMobileNumber();
        String nationaId = ud.getNationalId();

        if (!mobNumber.isEmpty()) {
            userDetails.setMobileNumber(mobNumber);
        }

        if (!nationaId.isEmpty()) {
            userDetails.setNationalId(nationaId);
        }

        userDetails.setBirthdate(ud.getBirthdate());
        userDetails.setJob(ud.getJob());
        userDetails.setNationalIdBack(ud.getNationalIdBack());
        userDetails.setNationalIdFront(ud.getNationalIdFront());
        userDetails.setProfileImage(ud.getProfileImage());

        session.update(userDetails);
    }

    @Override
    public int updateUserVerification(String id, int newValue) throws Exception {
        String hql = "UPDATE Users set verified = :verified "
                + "WHERE userId = :id";
        Query query = session.createQuery(hql);
        query.setParameter("verified", newValue);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        session = sessionFactory.getCurrentSession();

        Object o = session.load(Users.class, id);
        session.beginTransaction();
        session.delete(o);
        return true;
    }

    @Override
    public boolean isEmailValid(String email) throws Exception {
        session = sessionFactory.getCurrentSession();

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
        session = sessionFactory.getCurrentSession();

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
        session = sessionFactory.getCurrentSession();
        Users user = null;
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userEmail", email));
        criteria.add(Restrictions.eq("password", password));

        user = EntityCopier.getUser((Users) criteria.uniqueResult());
        return user;

    }

    @Override
    public List<Users> getEntityListToBeVerified() throws Exception {
        int verified = 2;
        session = sessionFactory.getCurrentSession();
//
//        tx = session.beginTransaction();
        List<Users> userList = new ArrayList<>();
        Criteria criteria = session.createCriteria(Users.class);
        criteria.createAlias("userDetailses", "uDetails")
                .add(Restrictions.eq("verified", verified)).add(Restrictions.neOrIsNotNull("uDetails.nationalIdFront", ""))
                .add(Restrictions.neOrIsNotNull("uDetails.nationalIdBack", ""));

        List<Users> users = criteria.list();
        System.out.println(users.size());
        for (Users user : users) {
            userList.add(EntityCopier.getUser(user));
        }
//        tx.commit();
        System.out.println(users.size());

        return userList;
    }

    @Override
    public boolean verifyUser(String userID, int verifiedID) throws Exception {
        session = sessionFactory.getCurrentSession();

        Users user = (Users) session.load(Users.class, userID);
        user.setVerified(verifiedID);
        session.update(user);
        return true;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userEmail", email));
        Users user = (Users) criteria.uniqueResult();
        if (user == null) {
            return false;
        }
        String newPassword = randomPassword();
        user.setPassword(newPassword);
        session.update(user);
        String receiver = email;
        String subject = user.getUserName() + ", your password was successfully reset";
        String msg = "\n Hi " + user.getUserName() + ",\n your new password is " + user.getPassword() + "\n Thanks for using Et3am App!\nThe Et3am Team";
        mail.sendMail(receiver, subject, msg);
        System.out.println("success");
        return true;
    }

    public String randomPassword() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String newPassword = RandomStringUtils.random(8, characters);
        return newPassword;

    }
}
