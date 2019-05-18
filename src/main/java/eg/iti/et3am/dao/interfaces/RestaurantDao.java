/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.Restaurants;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wael M Elmahask
 */
public interface RestaurantDao {

    String addRestaurant(Restaurants restaurant) throws Exception;

    Integer addMeal(Meals meal, Integer restaurantId) throws Exception;

    Restaurants getRestaurantById(Integer id) throws Exception;

    Restaurants getRestaurantDetailsById(Integer id) throws Exception;

    List<Meals> getMealById(Integer id) throws Exception;

    Set<Meals> getMealsById(Integer id) throws Exception;

    Meals findMealById(Integer id) throws Exception;

    List<Restaurants> getRestaurantsList() throws Exception;

    boolean updateMeal(Integer mealId, Meals meals) throws Exception;
//
//    boolean deleteRestaurant(long id) throws Exception;

    boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception;
}
