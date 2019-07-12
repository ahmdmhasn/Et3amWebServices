/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations.coupon;

import eg.iti.et3am.dao.interfaces.coupon.CouponDao;
import eg.iti.et3am.dao.interfaces.user.UserDao;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.dto.UserReserveCouponDTO;
import eg.iti.et3am.dto.UserUsedCouponDTO;
import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RestaurantCoupons;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import eg.iti.et3am.service.interfaces.coupon.CouponService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author A7med
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<String> addCoupon(String userId, int coupon50, int coupon100, int coupon200) throws Exception {
        List<String> createdCouponsIds = new ArrayList<>();

        // Creating coupons per value
        for (int i = 0; i < coupon50; i++) {
            createdCouponsIds.add(couponDao.addCoupon(userId, 50.00));
        }

        for (int i = 0; i < coupon100; i++) {
            createdCouponsIds.add(couponDao.addCoupon(userId, 100.00));
        }

        for (int i = 0; i < coupon200; i++) {
            createdCouponsIds.add(couponDao.addCoupon(userId, 200.00));
        }
        return createdCouponsIds;
    }

    @Override
    public List<RestaurantCoupons> getUsedCoupon(int restaurantId) throws Exception {
        return couponDao.getUsedCoupon(restaurantId);
    }

    @Override
    public List<UserUsedCoupon> getUserUsedCoupon(int pageNumber, String userId) throws Exception {
        return couponDao.getUserUsedCoupon(pageNumber, userId);
    }

    @Override
    public Coupons findByCode(String code) throws Exception {
        return couponDao.findByCode(code);
    }

    @Override
    public UserReserveCouponDTO checkCouponReservation(String code) throws Exception {
        return couponDao.checkCoupon(code, false);
    }

    @Override
    public int useCoupon(String code, double price, int restaurantId, int mealId) throws Exception {
        return couponDao.useCoupon(code, price, restaurantId,mealId);
    }

    @Override
    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception {
        return couponDao.reserveCoupon(reserverId, couponId, reservationDate);
    }

    @Override
    public AvailableCoupons getFreeCoupon(String userID) throws Exception {

        if (userDao.getEntityById(userID).getVerified() == 1) {
            if (couponDao.noMoreOneReservedCouponAtTheSameTime(userID)) {

                AvailableCoupons availableCoupon = couponDao.getFreeCoupon(userID);

                if (availableCoupon != null) {
                    couponDao.addReservedCoupon(availableCoupon, userID);
                    return availableCoupon;
                }

            } else {
                System.err.println("method is running --===============");
            }
        }
        return null;
    }

    @Override
    public void validateReserveCoupon() throws Exception {
        couponDao.validateReserveCoupon();
    }

    @Override
    public void addCouponFromRemainingBalance() throws Exception {
        couponDao.addCouponFromRemainingBalance();
    }

    @Override
    public boolean publishCoupon(String coupon_id) throws Exception {
        return couponDao.publishCoupon(coupon_id);
    }

    @Scheduled(fixedDelay = 3600000)
    @Override
    public void couponTrigger() throws Exception {
        validateReserveCoupon();
        addCouponFromRemainingBalance();
    }

    @Override
    public boolean cancelReservation(String coupon_id) throws Exception {
        return couponDao.cancleReservation(coupon_id); //To change body of generated methods, choose Tools | Templates.
    }

}
