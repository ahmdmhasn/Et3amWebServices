/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.model.Coupons;

/**
 *
 * @author A7med
 */
public interface CouponDao {

    public Coupons findByCode(String code);
    
}
