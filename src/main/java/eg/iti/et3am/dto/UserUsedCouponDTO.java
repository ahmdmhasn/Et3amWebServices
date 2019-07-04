package eg.iti.et3am.dto;

import java.io.Serializable;
import java.util.Date;

public class UserUsedCouponDTO implements Serializable {

    private String couponId;
    private String userId;
    private String barCode;
    private String userName;
    private String restaurantName;
    private String restaurantAddress;
    private Date useDate;
    private float price;

    public UserUsedCouponDTO() {
    }

    public UserUsedCouponDTO(String couponId, String userId, String barCode, String userName, String restaurantName, String restaurantAddress, Date useDate, float price) {
        this.couponId = couponId;
        this.userId = userId;
        this.barCode = barCode;
        this.userName = userName;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.useDate = useDate;
        this.price = price;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
