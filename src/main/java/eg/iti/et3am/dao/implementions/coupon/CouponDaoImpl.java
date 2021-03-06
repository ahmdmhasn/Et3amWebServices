package eg.iti.et3am.dao.implementions.coupon;

import eg.iti.et3am.dao.interfaces.coupon.CouponDao;
import eg.iti.et3am.dao.interfaces.user.UserDao;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.dto.UserReserveCouponDTO;
import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.Meals;
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
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
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
    private final int pageSize = 10;

    @Override
    public List<RestaurantCoupons> getUsedCoupon(int restaurantId ,  int pageNumber) throws Exception {
        session = sessionFactory.getCurrentSession();

        List<UserUsedCoupon> usedCouponsList = session.createCriteria(UserUsedCoupon.class).
                createAlias("restaurants", "r").
                add(Restrictions.eq("r.restaurantId", restaurantId))
          .setFirstResult((pageNumber - 1)* pageSize)
                 
        .setMaxResults(pageSize).setFetchSize(10).list();
        List<RestaurantCoupons> restaurantCoupons = new ArrayList<>();
        for (UserUsedCoupon coupons : usedCouponsList) {
            RestaurantCoupons restCoupon = new RestaurantCoupons(coupons.getUserReserveCoupon().getCoupons().getCouponBarcode(),
                    coupons.getUseDate(), coupons.getPrice(), coupons.getStatus());
            restaurantCoupons.add(restCoupon);

        }
        return restaurantCoupons;
    }

    @Override
    public Results getUserUsedCoupon(int pageNumber, String userId) throws Exception {
        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserUsedCoupon.class);
        criteria.createAlias("userReserveCoupon", "r");
        criteria.createAlias("r.users", "u");
        criteria.add(Restrictions.eq("u.userId", userId));
        criteria.setFirstResult(pageNumber - 1);
        criteria.setMaxResults(pageSize);
        List<UserUsedCoupon> userUsedCoupons = criteria.list();

        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        count = (count != null) ? count : 0;
        
        List<UserUsedCoupon> listOfUsedCouponse = new ArrayList<>();
        
        for (UserUsedCoupon userUsedCoupon : userUsedCoupons) {
            listOfUsedCouponse.add(EntityCopier.getUsedCoupon(userUsedCoupon));
        }
        
        Results result = new Results();
        result.setPage(pageNumber);
        result.setTotalPages(count);
        result.setTotalResults(count);
        result.setResults(listOfUsedCouponse);

        return result;
    }

    @Override
    public Coupons findByCode(String code) throws Exception {
        session = sessionFactory.getCurrentSession();
        Coupons coupon = (Coupons) session.load(Coupons.class, code);
        return (coupon != null) ? EntityCopier.getCoupon(coupon) : null;
    }

    @Override
    public String addCoupon(String userId, Double couponValue) throws Exception {
        Users user = userDao.getEntityById(userId);
        session = sessionFactory.getCurrentSession();
        Coupons coupon = new Coupons();
        coupon.setUsers(user);
        coupon.setInBalance(1);
        coupon.setCreationDate(new Date());
        coupon.setCouponValue(couponValue);
        coupon.setCouponBarcode(UUID.randomUUID().toString().substring(24).toUpperCase());
        session.save(coupon);
        String id = (String) session.getIdentifier(coupon);

        return id;
    }

    @Override

    public UserReserveCouponDTO checkCoupon(String code, boolean changeStatus) throws Exception {
        session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(UserReserveCoupon.class).
                createAlias("coupons", "c").
                add(Restrictions.eq("c.couponBarcode", code)).add(Restrictions.eq("status", 1));
        UserReserveCoupon coupon = (UserReserveCoupon) criteria.uniqueResult();
        if (coupon != null) {
            if (changeStatus) {
                coupon.setStatus(0);
                session.update(coupon);
            }
            System.out.println("update done");
            UserReserveCouponDTO couponDTO = new UserReserveCouponDTO();
            couponDTO.setCouponId(coupon.getCoupons().getCouponId());
            couponDTO.setCouponBarcode(coupon.getCoupons().getCouponBarcode());
            couponDTO.setCouponQrCode(coupon.getCoupons().getCouponQrcode());
            couponDTO.setCouponValue(coupon.getCoupons().getCouponValue());
            couponDTO.setReservationDate(coupon.getReservationDate());
            couponDTO.setStatus(coupon.getStatus());
            System.out.println("user");
            couponDTO.setUserId(coupon.getUsers().getUserId());
            couponDTO.setReservationId(coupon.getReservedCouponId());

            return couponDTO;
        } else {
            return null;
        }
    }

    public boolean updateReserveCouponStatus(UserReserveCoupon reserveCoupon) {
        session = sessionFactory.getCurrentSession();
        reserveCoupon.setStatus(0);
        session.update(reserveCoupon);
        return true;
    }

    @Override
    public int useCoupon(String code, double price, int restaurantId, int mealId) throws Exception {

        UserReserveCouponDTO couponDTO = checkCoupon(code, true);
        if (couponDTO != null) {
//            updateReserveCouponStatus(reserveCoupon);
            session = sessionFactory.getCurrentSession();
            Restaurants restaurantAdmin = (Restaurants) session.createCriteria(Restaurants.class).add(Restrictions.eq("restaurantId", restaurantId)).uniqueResult();
            Meals meal = (Meals) session.createCriteria(Meals.class).add(Restrictions.eq("mealId", mealId)).uniqueResult();
            UserReserveCoupon reserveCoupon1 = (UserReserveCoupon) session.createCriteria(UserReserveCoupon.class).add(Restrictions.eq("reservedCouponId", couponDTO.getReservationId())).uniqueResult();
            UserUsedCoupon userUsedCoupon = new UserUsedCoupon(meal, restaurantAdmin, reserveCoupon1, new Date(), (float) price, 1);
            userUsedCoupon.getUserReserveCoupon().setStatus(0);
            System.out.println("jjj" + userUsedCoupon.getPrice());
            session.saveOrUpdate(userUsedCoupon);
            float remainingValue = (float) (reserveCoupon1.getCoupons().getCouponValue() - price);
            if (remainingValue > 0) {
                RemainingBalance balance = new RemainingBalance(userUsedCoupon, remainingValue);
                session.save(balance);
            }
            int id = (int) session.getIdentifier(userUsedCoupon);
            return id;
        }
        return -1;
    }

    //not used
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
            session.close();
        }
        return id;
    }

    @Override

    public AvailableCoupons getFreeCoupon(String userID) throws Exception {
        session = sessionFactory.getCurrentSession();
        List<AvailableCoupons> couponList = session.createCriteria(AvailableCoupons.class).
                add(Restrictions.eq("status", 1)).list();
        AvailableCoupons coupon2 = null;

        if (!couponList.isEmpty()) {
            coupon2 = EntityCopier.getAvailableCoupons(couponList.get(0));
            couponList.get(0).setStatus(0);

        }
        session.flush();
        return coupon2;
    }

    @Override
    public boolean addReservedCoupon(AvailableCoupons c, String userId) throws Exception {
        try {
            session = sessionFactory.getCurrentSession();

            AvailableCoupons av = EntityCopier.getAvailableCoupons(c);

            Users user = (Users) session.load(Users.class, userId);
            UserReserveCoupon urc = new UserReserveCoupon();
            urc.setCoupons(av.getCoupons());
            urc.setStatus(1);
            urc.setUsers(user);
            urc.setReservationDate(new Date());
            session.save(urc);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            System.out.println("*************************" + ex.getMessage());
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
            if (urc.isEmpty()) {

                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean isLastCouponUsedMore48Houres(String userId) {

        UserUsedCoupon c = (UserUsedCoupon) session.createCriteria(UserUsedCoupon.class);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -48);
        Date before48Houre = calendar.getTime();

        if (c.getUseDate().compareTo(before48Houre) > 48) {

            return false;
        }

        return true;
    }

    @Override
    public long getUserUsedCouponsCount(String userId) throws Exception {
        session = sessionFactory.getCurrentSession();
        return (long) session.createCriteria(UserUsedCoupon.class)
                .createAlias("userReserveCoupon", "r")
                .createAlias("r.users", "u")
                .add(Restrictions.eq("u.userId", userId))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public long getUserDonatedCouponsCount(String id) throws Exception {
        session = sessionFactory.getCurrentSession();
        return (long) session.createCriteria(Coupons.class)
                .createAlias("users", "u")
                .add(Restrictions.eq("u.userId", id))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public Date getUserReservedCouponReservationDate(String id) throws Exception {
        session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(UserReserveCoupon.class)
                .createAlias("users", "u")
                .add(Restrictions.eq("u.userId", id))
                .add(Restrictions.eq("status", 1));

        long rowCount = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();

        if (rowCount > 0) {
            UserReserveCoupon coupon = (UserReserveCoupon) session.createCriteria(UserReserveCoupon.class)
                    .createAlias("users", "u")
                    .add(Restrictions.eq("u.userId", id))
                    .add(Restrictions.eq("status", 1))
                    .uniqueResult();
            return coupon.getReservationDate();
        } else {
            return null;
        }
    }

    @Override
    public void validateReserveCoupon() throws Exception {
        String newBarCode;
        session = sessionFactory.getCurrentSession();
        List<UserReserveCoupon> userReservCoupons = session.createCriteria(UserReserveCoupon.class)
                .add(Restrictions.eq("status", 1)).list();
        System.out.println(userReservCoupons);
        for (UserReserveCoupon userReserveCoupon : userReservCoupons) {
            List<Coupons> coupon;

            if (checkExperation(EntityCopier.getReservedCoupon(userReserveCoupon))) {
                userReserveCoupon.setStatus(0);
                //do {
                newBarCode = randomCode(12);
                //  coupon =  session.createCriteria(Coupons.class)
//                            .add(Restrictions.eq("couponBarcode", newBarCode)).list();
//                } while (coupon == null);
                userReserveCoupon.getCoupons().setCouponBarcode(newBarCode);
                session.update(userReserveCoupon);
            }
        }
    }

    boolean checkExperation(UserReserveCoupon coupon) {

        AvailableCoupons availableCoupon = (AvailableCoupons) session.createCriteria(AvailableCoupons.class).createAlias("coupons", "c").
                add(Restrictions.eq("c.couponId", coupon.getCoupons().getCouponId())).uniqueResult();
        if (availableCoupon != null) {
            System.out.println("test   " + coupon.getReservationDate());
            System.out.println("time" + (coupon.getReservationDate().getTime() - 48));
            long resrveTime = coupon.getReservationDate().getTime();
            long nowTime = new Date().getTime();
            System.out.println("time uu" + coupon.getReservationDate());
            System.err.println("time" + TimeUnit.MILLISECONDS.toHours(nowTime - resrveTime));

            if ((TimeUnit.MILLISECONDS.toHours(nowTime - resrveTime)) - 48 > 0) {
                availableCoupon.setStatus(1);
                session.update(availableCoupon);
                return true;
            }

            return false;
        }
        return false;
    }

    public String randomCode(int count) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = count;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    @Override
    public void addCouponFromRemainingBalance() throws Exception {
        session = sessionFactory.getCurrentSession();
        //tx = session.beginTransaction();
        List<RemainingBalance> balances = session.createCriteria(RemainingBalance.class).list();
        float amount = 0.0f;
        for (RemainingBalance balance : balances) {
            amount += balance.getChangeValue();
        }
        float remainingBalance = amount % 50;
        float coupounNum = (amount - remainingBalance) / 50;
        for (int i = 0; i < balances.size(); i++) {
            if (i == balances.size() - 1) {
                balances.get(i).setChangeValue(remainingBalance);
            } else {
                balances.get(i).setChangeValue(0);
            }
            session.update(balances.get(i));
        }
        //tx.commit();
        for (int i = 0; i < coupounNum; i++) {
            String id = addCoupon("907c14ad-6998-4a0e-8cdb-406097bb4bdb", 50.0);
            publishCoupon(id);
        }

    }

    @Override
    public boolean publishCoupon(String coupon_id) throws Exception {
        session = sessionFactory.getCurrentSession();
        //tx = session.beginTransaction();

        Coupons coupon = (Coupons) session.createCriteria(Coupons.class).add(Restrictions.eq("couponId", coupon_id)).uniqueResult();
        if (coupon != null) {
            if (coupon.getInBalance() == 1) {
                System.out.println("enter func " + coupon.getInBalance());
                AvailableCoupons availableCoupon = new AvailableCoupons(coupon, new Date(), 1);
                availableCoupon.getCoupons().setInBalance(0);
                session.save(availableCoupon);
                //  tx.commit();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean cancleReservation(String coupon_id) throws Exception {
        session = sessionFactory.getCurrentSession();
        UserReserveCoupon reserveCoupon = (UserReserveCoupon) session.createCriteria(UserReserveCoupon.class).createAlias("coupons", "c").add(Restrictions.eq("c.couponId", coupon_id)).add(Restrictions.eq("status", 1)).uniqueResult();
        if (reserveCoupon != null) {
            reserveCoupon.getCoupons().setInBalance(1);
            reserveCoupon.setStatus(0);
            session.update(reserveCoupon);
            return true;
        }
        return false;
    }
}
