/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.service.interfaces.CouponService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public int useCoupon(String code, double price, Date usedDate , int restaurantId) throws Exception {
        return couponDao.useCoupon(code, price ,usedDate,restaurantId);
    }

    @Override
    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception {
        return couponDao.reserveCoupon(reserverId, couponId, reservationDate);
    }

    @Override
    public List<UserUsedCoupon> getUsedCoupon(int restaurantId) throws Exception {
        return couponDao.getUsedCoupon(restaurantId);
    }
    @Override
     public List<UserUsedCoupon> getUserUsedCoupon(String userId) throws Exception {
      return couponDao.getUserUsedCoupon(userId);
     }

  
     
}
