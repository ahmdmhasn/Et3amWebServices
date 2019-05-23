/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.service.interfaces.CouponService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author A7med
 */
@Service
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

//        List<Coupons> createdCoupons = new ArrayList<>();
//        for (String id : createdCouponsIds) {
//            createdCoupons.add(couponDao.findByCode(id));
//        }
//        return createdCoupons;
        return createdCouponsIds;
    }

}