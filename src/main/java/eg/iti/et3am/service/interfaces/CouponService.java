package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.model.Coupons;

/**
 *
 * @author A7med
 */
public interface CouponService {

    public Coupons findByCode(String code);
    
}
