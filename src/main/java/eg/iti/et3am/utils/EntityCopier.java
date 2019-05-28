/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.utils;

import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.Users;

/**
 *
 * @author A7med
 */
public class EntityCopier {

    public static Users getUser(Users user) {
        Users user2 = new Users();
        user2.setUserId(user.getUserId());
        user2.setUserName(user.getUserName());
        user2.setPassword(user.getPassword());
        user2.setUserEmail(user.getUserEmail());
        user2.setVerified(user.getVerified());
        return user2;
    }
    
    public static Coupons getCoupon(Coupons coupon) {
        Coupons coupon2 = new Coupons();
        coupon2.setCouponId(coupon.getCouponId());
        coupon2.setCouponBarcode(coupon.getCouponBarcode());
        coupon2.setCouponQrcode(coupon.getCouponQrcode());
        coupon2.setCouponValue(coupon.getCouponValue());
        coupon2.setCreationDate(coupon.getCreationDate());
        coupon2.setIsBalance(coupon.getIsBalance());
        coupon2.setUsers(getUser(coupon.getUsers()));
        
        return coupon2;
    }

}
