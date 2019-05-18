/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.model.Status;
import eg.iti.et3am.service.RestaurantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // List of nearest restaurants
    @RequestMapping(value = "/rest_list", method = RequestMethod.GET, produces = "application/json")
    public List<Restaurants> RestaurantsList() {
        List<Restaurants> restaurantList = null;
        try {
            restaurantList = restaurantService.getRestaurantsList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return restaurantList;
    }

    // Restaurant deatails
    @RequestMapping(value = "/meals/{r_id}", method = RequestMethod.GET)
    public List<Meals> getMealById(@PathVariable("r_id") Integer id) {
        List<Meals> meals = null;
        try {
            meals = restaurantService.getMealById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return meals;
    }

    // Get Restaurant by id
    @RequestMapping(value = "/rest/{r_id}", method = RequestMethod.GET)
    public Restaurants getRestaurantById(@PathVariable("r_id") Integer id) {
        Restaurants restaurants = null;
        try {
            restaurants = restaurantService.getRestaurantById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return restaurants;
    }

    // Add new meal to restaurant
    @RequestMapping(value = "/r/addMeal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Status addMeal(@RequestBody Meals meal) {
        try {
            String id = restaurantService.addMeal(meal);
            return new Status(1, meal);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Status(0, ex.getMessage());
        }
    }

    /*---Check if the same username exists---*/
    @RequestMapping(value = "/validate/login", method = RequestMethod.GET)
    public @ResponseBody
    Status login(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return new Status(0, "Email or password must not be empty");
        }

        try {
            RestaurantAdmin admin = restaurantService.login(email, password);
            if (admin != null) {
                return new Status(1, admin);
            } else {
                return new Status(0, "Restaurant doesn't exist!");
            }
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }
}
