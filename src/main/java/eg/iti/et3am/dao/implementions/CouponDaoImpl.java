package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RemainingBalance;
import eg.iti.et3am.model.RestaurantCoupons;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouponDaoImpl implements CouponDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired(required = true)
    private UserDao userDao;

    Session session = null;
    Transaction tx = null;

    @Override
    public Coupons findByCode(String code) throws Exception {
        session = sessionFactory.getCurrentSession();
        Coupons coupon = (Coupons) session.load(Coupons.class, code);
        return (coupon != null) ? EntityCopier.getCoupon(coupon) : null;
    }

    @Override
    public String addCoupon(String userId, Double couponValue) throws Exception {
        session = sessionFactory.getCurrentSession();
        Coupons coupon = new Coupons();
        Users user = userDao.getEntityById(userId);

        coupon.setUsers(user);
        coupon.setCouponValue(couponValue);
        coupon.setCouponBarcode(UUID.randomUUID().toString().substring(24).toUpperCase());

        session.save(coupon);
        String id = (String) session.getIdentifier(coupon);
        return id;
    }

    @Override
    public UserReserveCoupon checkCoupon(String code) throws Exception {

        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserReserveCoupon.class).
                createAlias("coupons", "c").
                add(Restrictions.eq("c.couponBarcode", code));

        UserReserveCoupon coupon = (UserReserveCoupon) criteria.uniqueResult();

        if (coupon != null) {
            UserReserveCoupon coupon2 = new UserReserveCoupon(EntityCopier.getCoupon(coupon.getCoupons()),
                    EntityCopier.getUser(coupon.getUsers()),
                    coupon.getReservationDate(), coupon.getStatus());
            coupon2.setReservedCouponId(coupon.getReservedCouponId());
            coupon2.setReservationDate(coupon.getReservationDate());
            coupon2.setStatus(coupon.getStatus());
            return coupon2;
        } else {
            return null;
        }
    }

    @Override
    public int useCoupon(String code, double price, int restaurantId) throws Exception {

        UserReserveCoupon reserveCoupon = checkCoupon(code);
        if (reserveCoupon.getCoupons().getCouponId() != null && reserveCoupon.getStatus() == 1) {
            session = sessionFactory.getCurrentSession();

            Restaurants restaurantAdmin = (Restaurants) session.load(Restaurants.class, restaurantId);
            reserveCoupon.setStatus(0);
            session.update(reserveCoupon);
            System.out.println("update done");
            UserUsedCoupon userUsedCoupon = new UserUsedCoupon(EntityCopier.getRestaurant(restaurantAdmin), EntityCopier.getReservedCoupon(reserveCoupon), Calendar.getInstance().getTime(), (float) price, 1);
            System.out.println("jjj" + userUsedCoupon.getPrice());
            session.save(userUsedCoupon);
            float remainingValue = (float) (reserveCoupon.getCoupons().getCouponValue() - price);
            if (remainingValue > 0) {
                RemainingBalance balance = new RemainingBalance(userUsedCoupon, remainingValue);
                session.save(balance);
            }
            int id = (int) session.getIdentifier(userUsedCoupon);
            return id;
        }
        return -1;
    }

    @Override
    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception {
        session = sessionFactory.getCurrentSession();
        int id = -1;

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
            id = (int) session.getIdentifier(userReserveCoupon);
        }
        return id;
    }

    @Override
    public List<RestaurantCoupons> getUsedCoupon(int restaurantId) throws Exception {
        session = sessionFactory.getCurrentSession();
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

        List<RestaurantCoupons> restaurantCoupons = new ArrayList<>();
        for (UserUsedCoupon coupon : usedCouponsList2) {
            RestaurantCoupons restCoupon = new RestaurantCoupons(EntityCopier.getReservedCoupon(coupon.getUserReserveCoupon()).getCoupons().getCouponBarcode(), coupon.getUseDate(), coupon.getPrice());
            restaurantCoupons.add(restCoupon);
        }

        return restaurantCoupons;
    }

    @Override
    public AvailableCoupons getFreeCoupon(String userID) throws Exception {
        session = sessionFactory.getCurrentSession();
        List<AvailableCoupons> couponList = session.createCriteria(AvailableCoupons.class).
                add(Restrictions.eq("status", 1)).list();

        AvailableCoupons coupon2 = null;
        if (couponList.get(0) != null) {

            coupon2 = EntityCopier.getAvailableCoupons(couponList.get(0));
            couponList.get(0).setStatus(0);
        }
        return coupon2;
    }

    @Override
    public boolean addReservedCoupon(AvailableCoupons c, String userId) throws Exception {
        try {
            session = sessionFactory.getCurrentSession();
            Users user = (Users) session.load(Users.class, userId);
            UserReserveCoupon urc = new UserReserveCoupon();

            urc.setCoupons(c.getCoupons());
            urc.setStatus(1);
            urc.setUsers(user);
            urc.setReservationDate(new Date());
            session.save(urc);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("*************************" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean noMoreOneReservedCouponAtTheSameTime(String userId) throws Exception {
        session = sessionFactory.openSession();
        try {
            List<UserReserveCoupon> urc = session.createCriteria(UserReserveCoupon.class)
                    .add(Restrictions.eq("users.userId", userId))
                    .add(Restrictions.eq("status", 1)).list();
            if (urc.size() > 0) {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public boolean isLastCouponUsedMore48Houres(String userId) {

        UserUsedCoupon c = (UserUsedCoupon) session.createCriteria(AvailableCoupons.class);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -48);
        Date before48Houre = calendar.getTime();

        if (c.getUseDate().compareTo(before48Houre) > 48) {

            return false;
        }

        return true;
    }

    @Override
    public List<UserUsedCoupon> getUserUsedCoupon(String userId) throws Exception {
        session = sessionFactory.getCurrentSession();
        List<UserUsedCoupon> userUsedCoupons = session.createCriteria(UserUsedCoupon.class)
                .createAlias("userReserveCoupon", "r")
                .createAlias("r.users", "u")
                .add(Restrictions.eq("u.userId", userId)).list();
        System.out.println(userUsedCoupons);
        List<UserUsedCoupon> listOfUsedCouponse = new ArrayList<>();

        for (UserUsedCoupon userUsedCoupon : userUsedCoupons) {
            listOfUsedCouponse.add(EntityCopier.getUsedCoupon(userUsedCoupon));
            System.out.println(EntityCopier.getCoupon(EntityCopier.getUsedCoupon(userUsedCoupon).getUserReserveCoupon().getCoupons()));
            //    System.out.println(listOfUsedCouponse);
        }

        return listOfUsedCouponse;
    }

    @Override
    public List<Coupons> getAllCoupons(String userId) throws Exception {
        session = sessionFactory.getCurrentSession();
        List<Coupons> coupons = session.createCriteria(Coupons.class)
                .add(Restrictions.eq("users.userId", userId)).list();
        List<Coupons> couponses = new ArrayList<>();
        for (Coupons coupons1 : coupons) {
            Coupons c = (Coupons) coupons1.clone();
            System.out.println("couponsssssssssss " + coupons1.getCouponId());
        }
        return coupons;
    }

    public List<Coupons> getInBalanceCoupon(int pageNumber, String userId) {
        try {
            session = sessionFactory.getCurrentSession();
            int pageSize = 10;
            Criteria criteria = session.createCriteria(Coupons.class);
            criteria.add(Restrictions.eq("users.userId",userId));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);

            List<Coupons> coupons = (List<Coupons>) criteria.list();

            return coupons;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return null;
    }

}
