/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.dto.UserReserveCouponDTO;
import eg.iti.et3am.dto.UserUsedCouponDTO;
import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RestaurantCoupons;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import java.util.Date;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface CouponDao {

    public Coupons findByCode(String code) throws Exception;

    public UserReserveCoupon checkCoupon(String code, boolean changeStatus) throws Exception;

    public int useCoupon(String code, double price, int restaurantId) throws Exception;

    public String addCoupon(String userId, Double couponValue) throws Exception;

    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception;

    public AvailableCoupons getFreeCoupon(String userID) throws Exception;

    public List<UserUsedCoupon> getUserUsedCoupon(String userId) throws Exception;

    public boolean addReservedCoupon(AvailableCoupons c, String userID) throws Exception;

    public boolean noMoreOneReservedCouponAtTheSameTime(String userId) throws Exception;

    public List<RestaurantCoupons> getUsedCoupon(int restaurantId) throws Exception;

    public void validateReserveCoupon() throws Exception;

    public void addCouponFromRemainingBalance() throws Exception;

    public boolean publishCoupon(String coupon_id) throws Exception;

    public List<Coupons> getInBalanceCoupon(int pageNumber, String userId) throws Exception;

    public List<UserReserveCouponDTO> getAllReservedCoupons(String userId) throws Exception;

    public List<UserUsedCouponDTO> getAllUsedCoupons(String donatorId) throws Exception;

    public long getUserUsedCouponsCount(String userId) throws Exception;

    public long getUserDonatedCouponsCount(String id) throws Exception;

    public Date getUserReservedCouponReservationDate(String id) throws Exception;
}
