/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HP
 */
public class RestaurantCoupons implements Serializable {

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public RestaurantCoupons(String barCode, Date usedDate, float price, int status) {
        this.barCode = barCode;
        this.usedDate = usedDate;
        this.price = price;
        this.status = status;
    }
    private String barCode;
    private Date usedDate;
    private float price;
    private int status;

    /**
     * @return the barCode
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * @param barCode the barCode to set
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * @return the usedDate
     */
    public Date getUsedDate() {
        return usedDate;
    }

    /**
     * @param usedDate the usedDate to set
     */
    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

}
