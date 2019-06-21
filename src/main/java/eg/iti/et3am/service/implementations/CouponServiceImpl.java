/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.dao.interfaces.UserDao;
import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RestaurantCoupons;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import eg.iti.et3am.service.interfaces.CouponService;
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
    public Coupons findByCode(String code) throws Exception {
        return couponDao.findByCode(code);
    }

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
    public UserReserveCoupon checkCouponReservation(String code) throws Exception {
        return couponDao.checkCoupon(code);
    }

    @Override
    public int useCoupon(String code, double price, int restaurantId) throws Exception {
        return couponDao.useCoupon(code, price, restaurantId);
    }

    @Override
    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception {
        return couponDao.reserveCoupon(reserverId, couponId, reservationDate);
    }

    @Override
    public List<RestaurantCoupons> getUsedCoupon(int restaurantId) throws Exception {
        return couponDao.getUsedCoupon(restaurantId);
    }

    @Override
    public List<UserUsedCoupon> getUserUsedCoupon(String userId) throws Exception {
        return couponDao.getUserUsedCoupon(userId);
    }

    @Override
    public AvailableCoupons getFreeCoupon(String userID) throws Exception {

        if (userDao.getEntityById(userID).getVerified() == 1) {
            System.out.println("verified~~~~~~~~~~");

            if (couponDao.noMoreOneReservedCouponAtTheSameTime(userID)) {

                AvailableCoupons availableCoupon = couponDao.getFreeCoupon(userID);

                if (availableCoupon != null) {
                    couponDao.addReservedCoupon(availableCoupon, userID);
                    return availableCoupon;
                }

            } else {
                System.err.println("method is running --==============="
                        + "====================================="
                        + "===================================="
                        + "=============================================");
            }
        }
        System.out.println("new~~~~~~~~~~");
        return null;
    }


    @Override
    public List<Coupons> getAllCoupon(String userId) throws Exception {
        return couponDao.getAllCoupons(userId);
    }


    @Override
    public void validateReserveCoupon() throws Exception {
        couponDao.validateReserveCoupon();
        System.out.println("hdhdhd");
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

//          validateReserveCoupon();
//          addCouponFromRemainingBalance();

        validateReserveCoupon();
        addCouponFromRemainingBalance();
    }

    @Override
    public List<Coupons> getInBalanceCoupon(int pageNumber, String userId) throws Exception {
        return couponDao.getInBalanceCoupon(pageNumber, userId);

    }

}
