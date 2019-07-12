package eg.iti.et3am.dto;

import java.io.Serializable;
import java.util.Date;

public class UserReserveCouponDTO implements Serializable {

    private String userId;
    private String couponId;
    private String couponBarcode;
    private String couponQrCode;
    private double couponValue;
    private Date reservationDate;
    private int reservationId;
    private int status;

    public UserReserveCouponDTO() {
    }

    public UserReserveCouponDTO(String userId, String couponId, String couponBarcode, String couponQrCode, double couponValue, Date reservationDate) {
        this.userId = userId;
        this.couponId = couponId;
        this.couponBarcode = couponBarcode;
        this.couponQrCode = couponQrCode;
        this.couponValue = couponValue;
        this.reservationDate = reservationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponBarcode() {
        return couponBarcode;
    }

    public void setCouponBarcode(String couponBarcode) {
        this.couponBarcode = couponBarcode;
    }

    public String getCouponQrCode() {
        return couponQrCode;
    }

    public void setCouponQrCode(String couponQrCode) {
        this.couponQrCode = couponQrCode;
    }

    public double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(double couponValue) {
        this.couponValue = couponValue;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     * @return the reservationId
     */
    public int getReservationId() {
        return reservationId;
    }

    /**
     * @param reservationId the reservationId to set
     */
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

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

}
