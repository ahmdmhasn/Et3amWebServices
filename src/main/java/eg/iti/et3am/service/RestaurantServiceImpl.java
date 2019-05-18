/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service;

import eg.iti.et3am.dao.*;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wael M Elmahask
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public String addRestaurant(Restaurants restaurant) throws Exception {
        return restaurantDao.addRestaurant(restaurant);
    }

    @Override
    public String addMeal(Meals meal) throws Exception {
        return restaurantDao.addMeal(meal);
    }

    @Override
    public Restaurants getRestaurantById(Integer id) throws Exception {
        return restaurantDao.getRestaurantById(id);
    }

    @Override
    public List<Restaurants> getRestaurantsList() throws Exception {
        return restaurantDao.getRestaurantsList();
    }

    @Override
    public List<Meals> getMealById(Integer id) throws Exception {
        return restaurantDao.getMealById(id);
    }

    @Override
    public RestaurantAdmin login(String email, String password) throws Exception {
        return restaurantDao.login(email, password);
    }

}
