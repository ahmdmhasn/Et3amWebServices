package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author A7med
 */
@Transactional
@Repository
public class CouponDaoImpl implements CouponDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired(required = true)
    private UserDao userDao;

    Session session = null;
    Transaction tx = null;

    private Session getSession() {
        if (sessionFactory.getCurrentSession() == null) {
            session = sessionFactory.openSession();
        }
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Coupons findByCode(String code) throws Exception {
        session = sessionFactory.openSession();
        Coupons coupon = (Coupons) session.load(Coupons.class, code);
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        session.close();
        return coupon;
    }

    @Override
    public String addCoupon(String userId, Double couponValue) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        Coupons coupon = new Coupons();
        try {
            Users user = userDao.getEntityById(userId);
            coupon.setUsers(user);
            System.out.println(user.getUserName() + "~~~~~~~~~~~");
            coupon.setCouponValue(couponValue);
        } catch (Exception ex) {
            Logger.getLogger(CouponDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        coupon.setCouponBarcode(UUID.randomUUID().toString().substring(24).toUpperCase());
        session.save(coupon);
        tx.commit();
        String id = (String) session.getIdentifier(coupon);
        session.close();
        return id;
    }


    @Override
    public UserReserveCoupon checkCoupon(String code) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(UserReserveCoupon.class).
                createAlias("coupons", "c").
                add(Restrictions.eq("c.couponBarcode", code));
        UserReserveCoupon coupon = (UserReserveCoupon) criteria.uniqueResult();
        
        UserReserveCoupon coupon2 = new UserReserveCoupon(EntityCopier.getCoupon(coupon.getCoupons()), 
                EntityCopier.getUser(coupon.getUsers()), 
                coupon.getReservationDate(), coupon.getStatus());
                
        tx.commit();
        session.close();
        return coupon2;
    }

    @Override
    public Coupons useCoupon(String code, double price) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Coupons coupon = (Coupons) session.load(Coupons.class, couponId);
        Users user = (Users) session.load(Users.class, reserverId);
        UserReserveCoupon userReserveCoupon = new UserReserveCoupon();
        userReserveCoupon.setCoupons(coupon);
        userReserveCoupon.setStatus(1);
        userReserveCoupon.setReservationDate(reservationDate);
        userReserveCoupon.setUsers(user);
        session.save(userReserveCoupon);
        tx.commit();
        System.out.println(coupon.getCouponBarcode());
        Integer id = (Integer) session.getIdentifier(userReserveCoupon);
        session.close();
        return id;
    }
}
