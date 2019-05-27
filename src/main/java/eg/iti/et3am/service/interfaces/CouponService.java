/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.model.Coupons;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface CouponService {

    public Coupons findByCode(String code) throws Exception;
    
    public List<String> addCoupon(String userId, int coupon50, int coupon100, int coupon200) throws Exception;
}
