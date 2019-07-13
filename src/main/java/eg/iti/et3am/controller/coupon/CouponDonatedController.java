/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller.coupon;

import eg.iti.et3am.dto.Results;
import eg.iti.et3am.service.interfaces.coupon.CouponDonatedService;
import java.util.HashMap;
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

/**
 *
 * @author Wael M Elmahask
 */
@RestController
@RequestMapping("/coupon")
public class CouponDonatedController {

    @Autowired
    private CouponDonatedService couponService;

    //get coupons for user
    @RequestMapping(value = "/get_inBalance_coupon", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getInBalanceCoupon(@RequestParam("user_id") String userId, @RequestParam("page") int page) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Results coupons = couponService.getInBalanceCoupon(page, userId);
            if (coupons != null) {
                result.put("code", 1);
                result.put("page", coupons.getPage());
                result.put("total_page", coupons.getTotalPages());
                result.put("total_results", coupons.getTotalResults());
                result.put("Coupons", coupons.getResults());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("code", 0);
                result.put("message", "there are not Coupons");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    //get coupons for user
    @RequestMapping(value = "/get_all_reserved_coupon", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAllReservedCoupon(@RequestParam("user_id") String userId, @RequestParam("page") int page) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Results coupons = couponService.getAllReservedCoupons(page, userId);
            if (coupons != null) {
                result.put("code", 1);
                result.put("page", coupons.getPage());
                result.put("total_page", coupons.getTotalPages());
                result.put("total_results", coupons.getTotalResults());
                result.put("Coupons", coupons.getResults());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("code", 0);
                result.put("message", "there are not Coupons");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception ex) {
            result.put("status", 0);
            result.put("message", ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }

    //get coupons for user
    @RequestMapping(value = "/get_all_used_coupon", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAllUsedCoupon(@RequestParam("user_id") String userId, @RequestParam("page") int page) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Results coupons = couponService.getAllUsedCoupons(page, userId);
            if (coupons != null) {
                result.put("code", 1);
                result.put("page", coupons.getPage());
                result.put("total_page", coupons.getTotalPages());
                result.put("total_results", coupons.getTotalResults());
                result.put("Coupons", coupons.getResults());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("code", 0);
                result.put("message", "there are not Coupons");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception ex) {
            result.put("status", 0);
            result.put("message", ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }

}
