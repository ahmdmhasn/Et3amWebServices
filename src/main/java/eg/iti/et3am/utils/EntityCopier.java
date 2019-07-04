/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.utils;

import eg.iti.et3am.model.Admins;

import eg.iti.et3am.model.AvailableCoupons;

import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.Inquiries;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
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
        user2.setUserStatus(user.getUserStatus());
        user2.setCreationDate(user.getCreationDate());
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

    public static Restaurants getRestaurant(Restaurants restaurants) {
        Restaurants restaurants1 = new Restaurants();
        restaurants1.setCity(restaurants.getCity());
        restaurants1.setCountry(restaurants.getCountry());
        restaurants1.setDistance(restaurants.getDistance());
        restaurants1.setLatitude(restaurants.getLatitude());
        restaurants1.setLongitude(restaurants.getLongitude());
        restaurants1.setMealses(restaurants.getMealses());
        restaurants1.setRestaurantAdmins(restaurants.getRestaurantAdmins());
        restaurants1.setRestaurantId(restaurants.getRestaurantId());
        restaurants1.setRestaurantImage(restaurants.getRestaurantImage());
        restaurants1.setRestaurantName(restaurants.getRestaurantName());
        restaurants1.setUserUsedCoupons(restaurants.getUserUsedCoupons());

        return restaurants1;
    }

    public static UserReserveCoupon getReservedCoupon(UserReserveCoupon reserveCoupon) {
        UserReserveCoupon userReserveCoupon2 = new UserReserveCoupon();
        userReserveCoupon2.setCoupons(EntityCopier.getCoupon(reserveCoupon.getCoupons()));
        userReserveCoupon2.setReservationDate(reserveCoupon.getReservationDate());
        userReserveCoupon2.setReservedCouponId(reserveCoupon.getReservedCouponId());
        userReserveCoupon2.setStatus(reserveCoupon.getStatus());
        userReserveCoupon2.setUserUsedCoupons(reserveCoupon.getUserUsedCoupons());
        userReserveCoupon2.setUsers(EntityCopier.getUser(reserveCoupon.getUsers()));

        return userReserveCoupon2;
    }

    
    
    public static Admins getAdmin(Admins admin){
    Admins admin2 = new Admins();
    admin2.setAdminId(admin.getAdminId());
    admin2.setAdminName(admin.getAdminName());
    admin2.setAdminEmail(admin.getAdminEmail());
    admin2.setAdminPassword(admin.getAdminPassword());
    
    return admin2;

    }
    
    public static UserUsedCoupon getUsedCoupon(UserUsedCoupon userUsedCoupon) {
        UserUsedCoupon userUsedCoupon2 = new UserUsedCoupon();
        userUsedCoupon2.setUserReserveCoupon(EntityCopier.getReservedCoupon(userUsedCoupon.getUserReserveCoupon()));
        userUsedCoupon2.setPrice(userUsedCoupon.getPrice());
        userUsedCoupon2.setUsedCouponId(userUsedCoupon.getUsedCouponId());
        userUsedCoupon2.setUseDate(userUsedCoupon.getUseDate());
        
        userUsedCoupon2.setRestaurants(EntityCopier.getRestaurant(userUsedCoupon.getRestaurants()));
        
        userUsedCoupon2.setUserReserveCoupon(EntityCopier.getReservedCoupon(userUsedCoupon.getUserReserveCoupon()));
        return userUsedCoupon2;
    }
    
    public static AvailableCoupons getAvailableCoupons(AvailableCoupons ac1) {
        AvailableCoupons ac2 = new AvailableCoupons();
        ac2.setAvailableCouponId(ac1.getAvailableCouponId());
        ac2.setCoupons(EntityCopier.getCoupon(ac1.getCoupons()));
        ac2.setDate(ac1.getDate());
        ac2.setStatus(ac1.getStatus());
        return ac2;
    }

    public static Inquiries getInquiry(Inquiries inquiry) {
        Inquiries temp = new Inquiries();
        
        temp.setCreationDate(inquiry.getCreationDate());
        temp.setIdInquiries(inquiry.getIdInquiries());
        temp.setImage(inquiry.getImage());
        temp.setMessage(inquiry.getMessage());
        temp.setStatus(inquiry.getStatus());
        temp.setUsers(EntityCopier.getUser(inquiry.getUsers()));
        
        return temp;
    }
}
