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
    public Coupons findByCode(String code) throws Exception {
         checkCurrentSession();
        Coupons coupon = (Coupons) session.load(Coupons.class, code);
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        return coupon;
    }

    @Override
    public String addCoupon(String userId, Double couponValue) throws Exception {
        checkCurrentSession();
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
        return id;
    }

    @Override
    public UserReserveCoupon checkCoupon(String code) throws Exception {
        //checkCurrentSession();
        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(UserReserveCoupon.class).
                createAlias("coupons", "c").
                add(Restrictions.eq("c.couponBarcode", code));

        UserReserveCoupon coupon = (UserReserveCoupon) criteria.uniqueResult();

        UserReserveCoupon coupon2 = new UserReserveCoupon(EntityCopier.getCoupon(coupon.getCoupons()),
                EntityCopier.getUser(coupon.getUsers()),
                coupon.getReservationDate(), coupon.getStatus());
        coupon2.setReservedCouponId(coupon.getReservedCouponId());
        coupon2.setReservationDate(coupon.getReservationDate());
        coupon2.setStatus(coupon.getStatus());
        tx.commit();
        return coupon2;
    }

    @Override
    public int useCoupon(String code, double price, Date usedDate, int restaurantId) throws Exception {

        UserReserveCoupon reserveCoupon = checkCoupon(code);
        if (reserveCoupon.getCoupons().getCouponId() != null && reserveCoupon.getStatus() == 1) {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        }
        return -1;
    }

    @Override
    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception {
       session = sessionFactory.openSession();
        int id =-1;
        tx = session.beginTransaction();
        UserReserveCoupon reserveCoupon = (UserReserveCoupon) session.createCriteria(UserReserveCoupon.class).
                createAlias("coupons", "c").
                add(Restrictions.eq("c.couponId", couponId)).uniqueResult();
        System.out.println("kkk"+reserveCoupon);
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
        return id;
    }

    @Override
    public List<UserUsedCoupon> getUsedCoupon(int restaurantId) throws Exception {
        System.out.println("enteeer");

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<UserUsedCoupon> usedCouponsList = session.createCriteria(UserUsedCoupon.class).
                createAlias("restaurants", "r").
                add(Restrictions.eq("r.restaurantId", restaurantId)).list();
        List<UserUsedCoupon> usedCouponsList2 = new ArrayList<>();

        for (UserUsedCoupon coupons : usedCouponsList) {
            UserUsedCoupon u = new UserUsedCoupon(EntityCopier.getRestaurant(coupons.getRestaurants()), EntityCopier.getReservedCoupon(coupons.getUserReserveCoupon()),
                    coupons.getUseDate(), coupons.getPrice(), coupons.getStatus());
            coupons.setUsedCouponId(coupons.getUsedCouponId());
            coupons.setRemainingBalances(coupons.getRemainingBalances());
            usedCouponsList2.add(u);
        }
        System.out.println("rows num = " + usedCouponsList2.size());
        tx.commit();
        //session.close();
        return usedCouponsList2;
    }

}
