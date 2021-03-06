/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dto;

/**
 *
 * @author Wael M Elmahask
 */
public class RestaurantDTO implements Comparable<RestaurantDTO> {

    /**
     * @return the restaurantAdmin
     */
    public RestaurantAdminDTO getRestaurantAdmin() {
        return restaurantAdmin;
    }

    /**
     * @param restaurantAdmin the restaurantAdmin to set
     */
    public void setRestaurantAdmin(RestaurantAdminDTO restaurantAdmin) {
        this.restaurantAdmin = restaurantAdmin;
    }

    private Integer restaurantID;
    private String restaurantName;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    private double distance;
    private double travelTime;
    private String restaurantImage;
    private RestaurantAdminDTO restaurantAdmin;
    
    
    public RestaurantDTO() {
    }

    public RestaurantDTO(Integer restaurantID, String restaurantName, String city, String country, double latitude, double longitude, double distance, double travelTime, String restaurantImage) {
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.travelTime = travelTime;
        this.restaurantImage = restaurantImage;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    @Override
    public int compareTo(RestaurantDTO o) {
        return new Double(getLatitude()).compareTo(o.getLatitude());
    }
}
