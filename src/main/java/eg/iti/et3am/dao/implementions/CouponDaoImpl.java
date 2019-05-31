package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RemainingBalance;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
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

//    Session session = null;
//    Transaction tx = null;
    @Override
    public Coupons findByCode(String code) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Coupons coupon = (Coupons) session.load(Coupons.class, code);
            tx = session.getTransaction();
            session.beginTransaction();
            tx.commit();
            return coupon;
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public String addCoupon(String userId, Double couponValue) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        String id = null;

        try {
            Coupons coupon = new Coupons();
            Users user = userDao.getEntityById(userId);

            coupon.setUsers(user);
            coupon.setCouponValue(couponValue);
            coupon.setCouponBarcode(UUID.randomUUID().toString().substring(24).toUpperCase());

            session.save(coupon);
            tx.commit();

            id = (String) session.getIdentifier(coupon);
            return id;
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
            return id;
        } finally {
            session.close();
        }
    }

    @Override
    public UserReserveCoupon checkCoupon(String code) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        UserReserveCoupon coupon2 = null;

        try {
            Criteria criteria = session.createCriteria(UserReserveCoupon.class).
                    createAlias("coupons", "c").
                    add(Restrictions.eq("c.couponBarcode", code));

            UserReserveCoupon coupon = (UserReserveCoupon) criteria.uniqueResult();

            coupon2 = new UserReserveCoupon(EntityCopier.getCoupon(coupon.getCoupons()),
                    EntityCopier.getUser(coupon.getUsers()),
                    coupon.getReservationDate(), coupon.getStatus());
            coupon2.setReservedCouponId(coupon.getReservedCouponId());
            coupon2.setReservationDate(coupon.getReservationDate());
            coupon2.setStatus(coupon.getStatus());
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
        } finally {
            session.close();
        }
        return coupon2;
    }

    @Override
    public int useCoupon(String code, double price, Date usedDate, int restaurantId) throws Exception {

        UserReserveCoupon reserveCoupon = checkCoupon(code);
        if (reserveCoupon.getCoupons().getCouponId() != null && reserveCoupon.getStatus() == 1) {

            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            try {
                Restaurants restaurantAdmin = (Restaurants) session.load(Restaurants.class, restaurantId);
                reserveCoupon.setStatus(0);
                session.update(reserveCoupon);
                System.out.println("update done");
                UserUsedCoupon userUsedCoupon = new UserUsedCoupon(EntityCopier.getRestaurant(restaurantAdmin), EntityCopier.getReservedCoupon(reserveCoupon), usedDate, (float) price, 1);
                System.out.println("jjj" + userUsedCoupon.getPrice());
                session.save(userUsedCoupon);
                float remainingValue = (float) (reserveCoupon.getCoupons().getCouponValue() - price);
                if (remainingValue > 0) {
                    RemainingBalance balance = new RemainingBalance(userUsedCoupon, remainingValue);
                    session.save(balance);
                }
                tx.commit();
                int id = (int) session.getIdentifier(userUsedCoupon);
                return id;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                    throw ex;
                }
            } finally {
                session.close();
            }
        }
        return -1;
    }

    @Override
    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int id = -1;

        try {
            UserReserveCoupon reserveCoupon = (UserReserveCoupon) session.createCriteria(UserReserveCoupon.class).
                    createAlias("coupons", "c").
                    add(Restrictions.eq("c.couponId", couponId)).uniqueResult();

            if (reserveCoupon == null) {
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
                id = (int) session.getIdentifier(userReserveCoupon);
            }
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
    public List<UserUsedCoupon> getUsedCoupon(int restaurantId) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<UserUsedCoupon> usedCouponsList2 = new ArrayList<>();
        
        try {
            List<UserUsedCoupon> usedCouponsList = session.createCriteria(UserUsedCoupon.class).
                    createAlias("restaurants", "r").
                    add(Restrictions.eq("r.restaurantId", restaurantId)).list();
            
            for (UserUsedCoupon coupons : usedCouponsList) {
                UserUsedCoupon u = new UserUsedCoupon(EntityCopier.getRestaurant(coupons.getRestaurants()), EntityCopier.getReservedCoupon(coupons.getUserReserveCoupon()),
                        coupons.getUseDate(), coupons.getPrice(), coupons.getStatus());
                coupons.setUsedCouponId(coupons.getUsedCouponId());
                coupons.setRemainingBalances(coupons.getRemainingBalances());
                usedCouponsList2.add(u);
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
        return usedCouponsList2;
    }

}
