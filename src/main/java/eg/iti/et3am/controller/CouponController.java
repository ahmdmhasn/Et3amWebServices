/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.service.interfaces.CouponService;
import static org.hibernate.jpa.internal.EntityManagerImpl.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Coupons> get(@RequestParam("code") String code){
        System.out.println("getting user with code: " + code);
        Coupons coupon = couponService.findByCode(code);

        if (coupon == null){
            System.out.println("coupon with id "+code+" not found");
            return new ResponseEntity<Coupons>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Coupons>(coupon, HttpStatus.OK);
    }
    
}
