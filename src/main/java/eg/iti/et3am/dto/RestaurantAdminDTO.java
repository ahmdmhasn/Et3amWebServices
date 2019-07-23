/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dto;

/**
 *
 * @author Nesma
 */
public class RestaurantAdminDTO {

    /**
     * @return the restaurantAdminId
     */
    public Integer getRestaurantAdminId() {
        return restaurantAdminId;
    }

    /**
     * @param restaurantAdminId the restaurantAdminId to set
     */
    public void setRestaurantAdminId(Integer restaurantAdminId) {
        this.restaurantAdminId = restaurantAdminId;
    }

    /**
     * @return the restaurantAdminEmail
     */
    public String getRestaurantAdminEmail() {
        return restaurantAdminEmail;
    }

    /**
     * @param restaurantAdminEmail the restaurantAdminEmail to set
     */
    public void setRestaurantAdminEmail(String restaurantAdminEmail) {
        this.restaurantAdminEmail = restaurantAdminEmail;
    }

    /**
     * @return the restaurantAdminPassword
     */
    public String getRestaurantAdminPassword() {
        return restaurantAdminPassword;
    }

    /**
     * @param restaurantAdminPassword the restaurantAdminPassword to set
     */
    public void setRestaurantAdminPassword(String restaurantAdminPassword) {
        this.restaurantAdminPassword = restaurantAdminPassword;
    }
    private Integer restaurantAdminId;
     private String restaurantAdminEmail;
     private String restaurantAdminPassword;
}
