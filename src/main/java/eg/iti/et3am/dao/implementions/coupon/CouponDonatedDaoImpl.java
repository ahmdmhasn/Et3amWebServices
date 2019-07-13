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
        long count = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        List<Coupons> coupons = criteria.list();

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
        System.out.println("Countssssss  " + count);
        results.setPage(pageNumber);
        results.setTotalPages(count);
        results.setTotalResults(count);
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
        long count = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        List<UserReserveCoupon> couponList = criteria.list();

        List<UserReserveCouponDTO> couponses = new ArrayList<>();
        for (UserReserveCoupon coupons : couponList) {
            UserReserveCouponDTO couponDTO = new UserReserveCouponDTO();
            couponDTO.setUserId(coupons.getUsers().getUserId());
            couponDTO.setCouponBarcode(coupons.getCoupons().getCouponBarcode());
            couponDTO.setReservationDate(coupons.getReservationDate());
            couponDTO.setCouponId(coupons.getCoupons().getCouponId());
            couponDTO.setCouponQrCode(coupons.getCoupons().getCouponBarcode());
            couponDTO.setCouponValue(coupons.getCoupons().getCouponValue());
            couponses.add(couponDTO);
        }
        Results results = new Results();
        System.out.println("Countssssss  " + count);
        results.setPage(pageNumber);
        results.setTotalPages(count);
        results.setTotalResults(count);
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
        long count = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        List<UserUsedCoupon> couponList = criteria.list();

        List<UserUsedCouponDTO> couponses = new ArrayList<>();
        for (UserUsedCoupon coupons : couponList) {
            UserUsedCouponDTO couponDTO = new UserUsedCouponDTO();
            System.out.println("UserUsedCoupon ID " + coupons.getUserReserveCoupon().getCoupons().getCouponId() + "  UserUsed ID " + coupons.getUserReserveCoupon().getUsers().getUserId());
            couponDTO.setCouponId(coupons.getUserReserveCoupon().getCoupons().getCouponId());
            couponDTO.setUserId(coupons.getUserReserveCoupon().getUsers().getUserId());
            couponDTO.setBarCode(coupons.getUserReserveCoupon().getCoupons().getCouponBarcode());
            couponDTO.setRestaurantName(coupons.getRestaurants().getRestaurantName());
            couponDTO.setRestaurantAddress(coupons.getRestaurants().getCity() + ", " + coupons.getRestaurants().getCountry());
            couponDTO.setUseDate(coupons.getUseDate());
            couponDTO.setPrice(coupons.getPrice());
            couponDTO.setUserName(coupons.getUserReserveCoupon().getUsers().getUserName());

            couponses.add(couponDTO);
        }
        Results results = new Results();
        System.out.println("Countssssss  " + count);
        results.setPage(pageNumber);
        results.setTotalPages(count);
        results.setTotalResults(count);
        results.setResults(couponses);
        return results;
    }
}
