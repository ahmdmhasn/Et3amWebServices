package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wael M Elmahask
 */
public interface RestaurantDao {

    String addRestaurant(Restaurants restaurant) throws Exception;

    
    // get restaurant by id
    Restaurants getRestaurantById(Integer id) throws Exception;

    List<Restaurants> getRestaurantsList(double latitude, double longitude) throws Exception;

    List<Restaurants> getRestaurantsListWithMeals() throws Exception;

    // add meal to specific restaurant
    Integer addMeal(Meals meal, Integer restaurantId) throws Exception;

    // get list of meals by id
    List<Meals> getMealsListById(Integer id) throws Exception;

    //
    Set<Meals> getMealsSetById(Integer id) throws Exception;

    Meals findMealById(Integer id) throws Exception;

    boolean updateMeal(Integer mealId, Meals meals) throws Exception;

    boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception;

    RestaurantAdmin login(String email, String password) throws Exception;
    
    String addResturantAdmin(String email,String password,int restaurantId) throws Exception;
}
