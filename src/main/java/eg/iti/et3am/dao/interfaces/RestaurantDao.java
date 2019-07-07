package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.dto.MealDTO;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.dto.Results;
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

    List<RestaurantDTO> getRestaurantsList(int page, double latitude, double longitude) throws Exception;
    
    Results getRestaurantsListTrial(int page, double latitude, double longitude) throws Exception;

    Results searchInRestaurantsList(int pageNumber, double latitude, double longitude, String query) throws Exception;

    List<Restaurants> getRestaurantsListWithMeals() throws Exception;

    // add meal to specific restaurant
    Integer addMeal(Meals meal, Integer restaurantId) throws Exception;

    // get list of meals by id
    public List<MealDTO> getMealsListById(Integer id, int page) throws Exception;

    //
    Set<Meals> getMealsSetById(Integer id) throws Exception;

    Meals findMealById(Integer id) throws Exception;

    boolean updateMeal(Integer mealId, Meals meals) throws Exception;

    boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception;

    RestaurantAdmin login(String email, String password) throws Exception;

    String addResturantAdmin(String email, String password, int restaurantId) throws Exception;
    
    public String getTopMeal(int restId) throws Exception ;
}
