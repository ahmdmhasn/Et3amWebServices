/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.utils;

import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import java.util.HashSet;
import java.util.Set;

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
        user2.setUserDetailses(EntityCopier.getUserDetails(user.getUserDetailses()));
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

    public static Set<UserDetails> getUserDetails(Set<UserDetails> uds) {
        Set<UserDetails> userDetailses = new HashSet<>(0);
        for (UserDetails ud : uds) {
            UserDetails userDetails = new UserDetails();
            userDetails.setBirthdate(ud.getBirthdate());
            userDetails.setJob(ud.getJob());
            userDetails.setMobileNumber(ud.getMobileNumber());
            userDetails.setNationalId(ud.getNationalId());
            userDetails.setNationalIdBack(ud.getNationalIdBack());
            userDetails.setNationalIdFront(ud.getNationalIdFront());
            userDetails.setProfileImage(ud.getProfileImage());
            userDetails.setUserDetailId(ud.getUserDetailId());
            userDetailses.add(ud);
        }
        return userDetailses;
    }
}
