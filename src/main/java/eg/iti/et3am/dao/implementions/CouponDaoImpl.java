package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.dto.UserReserveCouponDTO;
import eg.iti.et3am.dto.UserUsedCouponDTO;
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
    //Transaction tx = null;
    private final int pageSize = 10;

    @Override
    public Results getInBalanceCouponTrial(int pageNumber, String userId) throws Exception {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Coupons.class);
        criteria.add(Restrictions.eq("users.userId", userId));
        criteria.add(Restrictions.eq("isBalance", 1));
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        List<Coupons> coupons = criteria.list();

        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();

        List<Coupons> couponses = new ArrayList<>();
        for (Coupons coupon : coupons) {
            Coupons c = (Coupons) coupon.clone();
            couponses.add(c);
        }
        Results results = new Results();
        results.setPage(pageNumber);
        results.setTotalPages(count);
        results.setTotalResults(count);
        results.setResults(couponses);
        return results;
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
            RestaurantCoupons restCoupon = new RestaurantCoupons(EntityCopier.getReservedCoupon(coupon.getUserReserveCoupon()).getCoupons().getCouponBarcode(), coupon.getUseDate(), coupon.getPrice(), coupon.getStatus());
            restaurantCoupons.add(restCoupon);
        }

        return restaurantCoupons;
    }

    @Override
    public List<UserUsedCoupon> getUserUsedCoupon(int pageNumber, String userId) throws Exception {
        //int pageSize = 10;
        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserUsedCoupon.class);
        criteria.createAlias("userReserveCoupon", "r");
        criteria.createAlias("r.users", "u");
        criteria.add(Restrictions.eq("u.userId", userId));
        criteria.setFirstResult(pageNumber - 1);
        criteria.setMaxResults(pageSize);
        List<UserUsedCoupon> userUsedCoupons = criteria.list();

        List<UserUsedCoupon> listOfUsedCouponse = new ArrayList<>();

        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();

        for (UserUsedCoupon userUsedCoupon : userUsedCoupons) {
            listOfUsedCouponse.add(EntityCopier.getUsedCoupon(userUsedCoupon));
        }

        return listOfUsedCouponse;
    }

    //reserved coupon
    @Override
    public List<UserReserveCouponDTO> getAllReservedCoupons(int pageNumber, String donatorId) throws Exception {

        //int pageSize = 10;
        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserReserveCoupon.class);
        criteria.createAlias("coupons", "coupon");
        criteria.createAlias("coupon.users", "donator");
        criteria.add(Restrictions.eq("donator.userId", donatorId));
        criteria.add(Restrictions.eq("status", 1));
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        List<UserReserveCoupon> coupons = criteria.list();
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();

        List<UserReserveCouponDTO> couponses = new ArrayList<>();

        for (UserReserveCoupon coupons1 : coupons) {
            UserReserveCoupon c = (UserReserveCoupon) coupons1.clone();
            UserReserveCouponDTO couponDTO = new UserReserveCouponDTO();
            couponDTO.setUserId(c.getUsers().getUserId());
            couponDTO.setCouponBarcode(c.getCoupons().getCouponBarcode());
            couponDTO.setReservationDate(c.getReservationDate());
            couponDTO.setCouponId(c.getCoupons().getCouponId());
            couponDTO.setCouponQrCode(c.getCoupons().getCouponBarcode());
            couponDTO.setCouponValue(c.getCoupons().getCouponValue());
//            couponDTO.set
            couponses.add(couponDTO);
        }
        return couponses;
    }

    //used coupon
    @Override
    public List<UserUsedCouponDTO> getAllUsedCoupons(int pageNumber, String donatorId) throws Exception {

        session = sessionFactory.getCurrentSession();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserUsedCoupon.class);
        criteria.createAlias("userReserveCoupon", "urc");
        criteria.createAlias("urc.coupons", "coupon");
        criteria.createAlias("coupon.users", "donator");
        criteria.add(Restrictions.eq("donator.userId", donatorId));
        criteria.add(Restrictions.eq("status", 1));
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        List<UserUsedCoupon> coupons = criteria.list();

        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();

        List<UserUsedCouponDTO> couponses = new ArrayList<>();

        for (UserUsedCoupon coupons1 : coupons) {

            UserUsedCouponDTO couponDTO = new UserUsedCouponDTO();

            UserUsedCoupon c = (UserUsedCoupon) coupons1.clone();

            couponDTO.setCouponId(c.getUserReserveCoupon().getCoupons().getCouponId());
            couponDTO.setUserId(c.getUserReserveCoupon().getUsers().getUserId());
            couponDTO.setBarCode(c.getUserReserveCoupon().getCoupons().getCouponBarcode());
            couponDTO.setRestaurantName(c.getRestaurants().getRestaurantName());
            couponDTO.setRestaurantAddress(c.getRestaurants().getCity() + ", " + c.getRestaurants().getCountry());
            couponDTO.setUseDate(c.getUseDate());
            couponDTO.setPrice(c.getPrice());
            couponDTO.setUserName(c.getUserReserveCoupon().getUsers().getUserName());

            couponses.add(couponDTO);
        }

        return couponses;
    }

    @Override
    public List<Coupons> getInBalanceCoupon(int pageNumber, String userId) throws Exception {
        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Coupons.class);
            criteria.add(Restrictions.eq("users.userId", userId));
            criteria.add(Restrictions.eq("isBalance", 1));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);

            List<Coupons> coupons = criteria.list();

            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();

            List<Coupons> couponses = new ArrayList<>();
            for (Coupons coupon : coupons) {
                Coupons c = (Coupons) coupon.clone();
                System.out.println("ufesksfkeueibusgeiulbsdiugesfiugaefiugafeiub " + c.getCreationDate());
                couponses.add(c);
            }
            return couponses;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return null;
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
        coupon.setIsBalance(1);
        coupon.setCreationDate(new Date());
        coupon.setCouponValue(couponValue);
        coupon.setCouponBarcode(UUID.randomUUID().toString().substring(24).toUpperCase());
        session.save(coupon);
        String id = (String) session.getIdentifier(coupon);

        return id;
    }

    @Override
    public UserReserveCoupon checkCoupon(String code, boolean changeStatus) throws Exception {
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
            UserReserveCoupon coupon2 = EntityCopier.getReservedCoupon(coupon);;
            return coupon2;
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
    public int useCoupon(String code, double price, int restaurantId) throws Exception {

        UserReserveCoupon reserveCoupon = checkCoupon(code, true);
        if (reserveCoupon != null) {
//            updateReserveCouponStatus(reserveCoupon);
            session = sessionFactory.getCurrentSession();
            Restaurants restaurantAdmin = (Restaurants) session.createCriteria(Restaurants.class).add(Restrictions.eq("restaurantId", restaurantId)).uniqueResult();
            UserUsedCoupon userUsedCoupon = new UserUsedCoupon(restaurantAdmin, reserveCoupon, new Date(), (float) price, 1);
            userUsedCoupon.getUserReserveCoupon().setStatus(0);
            System.out.println("jjj" + userUsedCoupon.getPrice());
            session.saveOrUpdate(userUsedCoupon);
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
            if (coupon.getIsBalance() == 1) {
                System.out.println("enter func " + coupon.getIsBalance());
                AvailableCoupons availableCoupon = new AvailableCoupons(coupon, new Date(), 1);
                availableCoupon.getCoupons().setIsBalance(0);
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
            reserveCoupon.getCoupons().setIsBalance(1);
            reserveCoupon.setStatus(0);
            session.update(reserveCoupon);
            return true;
        }
        return false;
    }
}
