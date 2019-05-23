/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.model;

/**
 *
 * @author A7med
 */
public class Status {

    private int code;
    private Integer id;
    private String message;
    private Users user;
    private Restaurants restaurant;
    private RestaurantAdmin restaurantAdmin;

    public Status(int code, Restaurants restaurant) {
        this.code = code;
        this.restaurant = restaurant;
    }

    public Status() {
    }

    public Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Status(int code, Users user) {
        this.code = code;
        this.user = user;
    }

    public Status(int code, Integer mealID, String message) {
        this.code = code;
        this.id = mealID;
        this.message = message;
    }

    public Status(int code, RestaurantAdmin restaurantAdmin) {
        this.code = code;
        this.restaurantAdmin = restaurantAdmin;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Restaurants getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurants restaurant) {
        this.restaurant = restaurant;
    }

    public RestaurantAdmin getRestaurantAdmin() {
        return restaurantAdmin;
    }

    public void setRestaurantAdmin(RestaurantAdmin restaurantAdmin) {
        this.restaurantAdmin = restaurantAdmin;
    }

}
