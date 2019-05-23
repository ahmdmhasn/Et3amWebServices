/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.service.interfaces.CouponService;
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
}
