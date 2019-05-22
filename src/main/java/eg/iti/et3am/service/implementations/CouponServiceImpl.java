/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.service.interfaces.CouponService;
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
    public Coupons findByCode(String code) {
        return couponDao.findByCode(code);
    }
    
}
