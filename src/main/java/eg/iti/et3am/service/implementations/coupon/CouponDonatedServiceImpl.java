/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations.coupon;

import eg.iti.et3am.dao.interfaces.coupon.CouponDonatedDao;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.service.interfaces.coupon.CouponDonatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wael M Elmahask
 */
@Service
@Transactional
public class CouponDonatedServiceImpl implements CouponDonatedService {

    @Autowired
    private CouponDonatedDao couponDao;

    @Override
    public Results getAllReservedCoupons(int pageNumber, String userId) throws Exception {
        return couponDao.getAllReservedCoupons(pageNumber, userId);
    }

    @Override
    public Results getInBalanceCoupon(int pageNumber, String userId) throws Exception {
        return couponDao.getInBalanceCoupon(pageNumber, userId);
    }

    @Override
    public Results getAllUsedCoupons(int pageNumber, String donatorId) throws Exception {
        return couponDao.getAllUsedCoupons(pageNumber, donatorId);
    }

}
