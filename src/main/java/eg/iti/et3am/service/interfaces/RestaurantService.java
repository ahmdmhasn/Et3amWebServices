/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.Restaurants;
import java.util.List;

/**
 *
 * @author Wael M Elmahask
 */
public interface RestaurantService {

    String addRestaurant(Restaurants restaurant) throws Exception;

    Integer addMeal(Meals meal, Integer resturantId) throws Exception;

    Restaurants getRestaurantById(Integer id) throws Exception;
//

    List<Restaurants> getRestaurantsList() throws Exception;

    List<Meals> getMealById(Integer id) throws Exception;
//
//    boolean updateRestaurant(long id, Restaurants restaurant) throws Exception;

    Meals findMealById(Integer id) throws Exception;

    boolean updateMeal(Integer mealId, Meals meals) throws Exception;

    //    boolean deleteRestaurant(long id) throws Exception;
    public boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception;
}
