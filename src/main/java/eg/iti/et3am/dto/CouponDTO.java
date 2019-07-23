/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dto;

import java.util.Date;

/**
 *
 * @author Wael M Elmahask
 */
public class CouponDTO {

    private String couponId;
    private double couponValue;
    private String couponBarcode;
    private Date creationDate;

    public CouponDTO() {
    }

    public CouponDTO(String couponId, double couponValue, String couponBarcode, Date creationDate) {
        this.couponId = couponId;
        this.couponValue = couponValue;
        this.couponBarcode = couponBarcode;
        this.creationDate = creationDate;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(double couponValue) {
        this.couponValue = couponValue;
    }

    public String getCouponBarcode() {
        return couponBarcode;
    }

    public void setCouponBarcode(String couponBarcode) {
        this.couponBarcode = couponBarcode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
