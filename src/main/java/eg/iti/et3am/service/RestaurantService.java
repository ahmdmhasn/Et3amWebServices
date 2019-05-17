/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service;

import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.Restaurants;
import java.util.List;

/**
 *
 * @author Wael M Elmahask
 */
public interface RestaurantService {

    String addRestaurant(Restaurants restaurant) throws Exception;

    Integer addMeal(Meals meal) throws Exception;

    Restaurants getRestaurantById(Integer id) throws Exception;
//
    List<Restaurants> getRestaurantsList() throws Exception;

    List<Meals> getMealById(Integer id) throws Exception;
//
//    boolean updateRestaurant(long id, Restaurants restaurant) throws Exception;
//
//    boolean deleteRestaurant(long id) throws Exception;
}
