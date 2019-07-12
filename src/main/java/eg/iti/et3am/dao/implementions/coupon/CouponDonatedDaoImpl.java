/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.implementions.coupon;

import eg.iti.et3am.dao.interfaces.coupon.CouponDonatedDao;
import eg.iti.et3am.dto.CouponDTO;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.dto.UserReserveCouponDTO;
import eg.iti.et3am.dto.UserUsedCouponDTO;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wael M Elmahask
 */
@Repository
public class CouponDonatedDaoImpl implements CouponDonatedDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    Session session = null;
    private final int pageSize = 10;

    @Override
    public Results getInBalanceCoupon(int pageNumber, String userId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Coupons.class);
        criteria.add(Restrictions.eq("users.userId", userId));
        criteria.add(Restrictions.eq("inBalance", 1));
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        List<Coupons> coupons = criteria.list();

        criteria.setProjection(Projections.rowCount());
//        Long count = (Long) criteria.uniqueResult();

        List<CouponDTO> couponses = new ArrayList<>();
        for (Coupons coupon : coupons) {
            CouponDTO couponDTO = new CouponDTO();
            couponDTO.setCouponId(coupon.getCouponId());
            couponDTO.setCouponBarcode(coupon.getCouponBarcode());
            couponDTO.setCouponValue(coupon.getCouponValue());
            couponDTO.setCreationDate(coupon.getCreationDate());
            couponses.add(couponDTO);
        }
        Results results = new Results();
        results.setPage(pageNumber);
//        results.setTotalPages(count);
//        results.setTotalResults(count);
        results.setResults(couponses);
        return results;
    }

    @Override
    public Results getAllReservedCoupons(int pageNumber, String donatorId) throws Exception {
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
            couponses.add(couponDTO);
        }
        Results results = new Results();
        results.setPage(pageNumber);
//        results.setTotalPages(count);
//        results.setTotalResults(count);
        results.setResults(couponses);
        return results;
    }

    @Override
    public Results getAllUsedCoupons(int pageNumber, String donatorId) throws Exception {
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

            System.out.println("UserUsedCoupon ID " + c.getUserReserveCoupon().getCoupons().getCouponId());

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
        Results results = new Results();
        results.setPage(pageNumber);
//        results.setTotalPages(count);
//        results.setTotalResults(count);
        results.setResults(couponses);
        return results;
    }
}
