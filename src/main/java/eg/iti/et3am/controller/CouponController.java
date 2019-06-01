/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.AvailableCoupons;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RestaurantCoupons;
import eg.iti.et3am.model.UserReserveCoupon;
import eg.iti.et3am.model.UserUsedCoupon;
import eg.iti.et3am.service.interfaces.CouponService;
import eg.iti.et3am.utils.EntityCopier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<Coupons> get(@RequestParam("code") String code) {
        try {
            System.out.println("getting user with code: " + code);
            Coupons coupon = couponService.findByCode(code);
            if (coupon == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /*Add coupon - for testing only*/
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> addCoupon(@RequestParam("user_id") String userId,
            @RequestParam("value_50") int value50,
            @RequestParam("value_100") int value100,
            @RequestParam("value_200") int value200) {

        Map<String, Object> result = new HashMap<>();
        try {
            List coupons = couponService.addCoupon(userId, value50, value100, value200);

            result.put("status", 1);
            result.put("result", coupons);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("status", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    //check reservation
    @RequestMapping(value = "/check_reservation", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getReservedCoupon(@RequestParam("code") String code) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("getting user with code: " + code);
            UserReserveCoupon coupon = couponService.checkCouponReservation(code);
            if (coupon != null && coupon.getStatus() == 1) {
                result.put("code", 1);
                result.put("coupon", coupon);
                result.put("id", coupon.getReservedCouponId());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("code", 0);
                result.put("message", "not found");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

    }

    // reserve coupon  
    @RequestMapping(value = "/reserve_coupon", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> reserveCoupon(@RequestParam("reserver_id") String reserver_id,
            @RequestParam("coupon_id") String coupon_id,
            @RequestParam("reservationDate") Date reservationDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int id = couponService.reserveCoupon(reserver_id, coupon_id, reservationDate);
            if (id == -1) {
                result.put("code", 0);
                result.put("message", "coupon is already reserved ");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("code", 1);
                result.put("id", id);
                result.put("message", "coupon is reserved sucessfuly");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // use coupon  
    @RequestMapping(value = "/use_coupon", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> useCoupon(@RequestParam("restaurantId") int restaurantId,
            @RequestParam("barCode") String barCode,
            @RequestParam("price") float price,
            @RequestParam("usedDate") Date usedDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int id = couponService.useCoupon(barCode, price, usedDate, restaurantId);
            if (id != -1) {
                result.put("code", 1);
                result.put("id", id);
                result.put("message", "coupon is reserved sucessfuly");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("code", 0);
                result.put("message", "coupon is not available");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    //get restaurant coupons
    @RequestMapping(value = "/use_coupon_list", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUsedCoupon(@RequestParam("restaurantId") int restaurantId) {
        Map<String, Object> result = new HashMap<>();
        List<UserUsedCoupon> usedCoupon = null;
        try {
            usedCoupon = couponService.getUsedCoupon(restaurantId);
            System.out.println("hh" + usedCoupon.size());
            List<RestaurantCoupons> restaurantCoupons = new ArrayList<>();
            if (usedCoupon != null && !usedCoupon.isEmpty()) {
                for (UserUsedCoupon coupon : usedCoupon) {
                    RestaurantCoupons restCoupon = new RestaurantCoupons(EntityCopier.getReservedCoupon(coupon.getUserReserveCoupon()).getCoupons().getCouponBarcode(), coupon.getUseDate(), coupon.getPrice());
                    restaurantCoupons.add(restCoupon);
                }
                result.put("code", 1);
                result.put("message", "");
                result.put("restaurantCoupons", restaurantCoupons);
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            } else {
                result.put("code", 0);
                result.put("message", "no item found");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);

        }
    }

    // get coupon  
    @RequestMapping(value = "/get_free_coupon", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getFreeCoupon(@RequestParam("user_id") String id) throws Exception {
        Map<String, Object> result = new HashMap<>();

        try {
            AvailableCoupons coupon = couponService.getFreeCoupon(id);
            if (coupon != null) {
                System.out.println(id);
                result.put("coupon", coupon);
                result.put("status", 1);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("status", 0);
                result.put("message", "User is not verified or no coupon exists at the moment.");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception ex) {
            result.put("status", 0);
            result.put("message", ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }
}
