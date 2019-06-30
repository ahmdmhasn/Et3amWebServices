package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.dto.RestaurantDTO;
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

    public List<RestaurantDTO> searchInRestaurantsList(int pageNumber, double latitude, double longitude, String query) throws Exception;

    List<Meals> getMealById(Integer id) throws Exception;

    Meals findMealById(Integer id) throws Exception;

    boolean updateMeal(Integer mealId, Meals meals) throws Exception;

    public boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception;

    public RestaurantAdmin login(String email, String password) throws Exception;
}
