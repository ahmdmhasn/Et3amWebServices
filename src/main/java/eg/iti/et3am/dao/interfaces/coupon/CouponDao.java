/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces.coupon;

import eg.iti.et3am.dto.Results;
import eg.iti.et3am.dto.UserReserveCouponDTO;
import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RestaurantCoupons;
import eg.iti.et3am.model.UserUsedCoupon;
import java.util.Date;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface CouponDao {

    List<RestaurantCoupons> getUsedCoupon(int restaurantId) throws Exception;

    Results getUserUsedCoupon(int pageNumber, String userId) throws Exception;

//    List<Coupons> getInBalanceCoupon(int pageNumber, String userId) throws Exception;
//
//    Results getInBalanceCouponTrial(int pageNumber, String userId) throws Exception;
//
//    List<UserReserveCouponDTO> getAllReservedCoupons(int pageNumber, String userId) throws Exception;
//
//    List<UserUsedCouponDTO> getAllUsedCoupons(int pageNumber, String donatorId) throws Exception;
    Coupons findByCode(String code) throws Exception;

    UserReserveCouponDTO checkCoupon(String code, boolean changeStatus) throws Exception;

    int useCoupon(String code, double price, int restaurantId, int mealId) throws Exception;

    String addCoupon(String userId, Double couponValue) throws Exception;

    int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception;

    AvailableCoupons getFreeCoupon(String userID) throws Exception;

    boolean addReservedCoupon(AvailableCoupons c, String userID) throws Exception;

    boolean noMoreOneReservedCouponAtTheSameTime(String userId) throws Exception;

    void validateReserveCoupon() throws Exception;

    void addCouponFromRemainingBalance() throws Exception;

    boolean publishCoupon(String coupon_id) throws Exception;

    long getUserUsedCouponsCount(String userId) throws Exception;

    long getUserDonatedCouponsCount(String id) throws Exception;

    Date getUserReservedCouponReservationDate(String id) throws Exception;

    boolean cancleReservation(String coupon_id) throws Exception;
}
