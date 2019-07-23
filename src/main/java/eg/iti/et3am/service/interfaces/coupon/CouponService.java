
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.interfaces.coupon;

import eg.iti.et3am.dto.Results;
import eg.iti.et3am.dto.UserReserveCouponDTO;
import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RestaurantCoupons;
import java.util.Date;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface CouponService {

    public List<String> addCoupon(String userId, int coupon50, int coupon100, int coupon200) throws Exception;

    public List<RestaurantCoupons> getUsedCoupon(int restaurantId) throws Exception;

    public Results getUserUsedCoupon(int pageNumber, String userId) throws Exception;

    public Coupons findByCode(String code) throws Exception;

    public UserReserveCouponDTO checkCouponReservation(String code) throws Exception;

    public int useCoupon(String code, double price, int restaurantId, int mealId) throws Exception;

    public int reserveCoupon(String reserverId, String couponId, Date reservationDate) throws Exception;

    public AvailableCoupons getFreeCoupon(String userID) throws Exception;

    public void validateReserveCoupon() throws Exception;

    public void addCouponFromRemainingBalance() throws Exception;

    public boolean publishCoupon(String coupon_id) throws Exception;

    public void couponTrigger() throws Exception;

    public boolean cancelReservation(String coupon_id) throws Exception;
}
