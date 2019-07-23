/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces.coupon;

import eg.iti.et3am.dto.Results;

/**
 *
 * @author Wael M Elmahask
 */
public interface CouponDonatedDao {

    Results getInBalanceCoupon(int pageNumber, String userId) throws Exception;

    Results getAllReservedCoupons(int pageNumber, String userId) throws Exception;

    Results getAllUsedCoupons(int pageNumber, String donatorId) throws Exception;

}
