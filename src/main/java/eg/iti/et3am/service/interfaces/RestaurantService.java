package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.dto.MealDTO;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
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

    List<RestaurantDTO> getRestaurantsList(int page, double latitude, double longitude) throws Exception;

    Results getRestaurantsListTrial(int page, double latitude, double longitude) throws Exception;

    Results searchInRestaurantsList(int pageNumber, double latitude, double longitude, String query) throws Exception;

    List<MealDTO> getMealById(Integer id, int page) throws Exception;

    Meals findMealById(Integer id) throws Exception;

    boolean updateMeal(Integer mealId, Meals meals) throws Exception;

    boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception;

    RestaurantAdmin login(String email, String password) throws Exception;
}
