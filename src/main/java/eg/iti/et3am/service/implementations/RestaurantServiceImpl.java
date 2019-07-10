package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.RestaurantDao;
import eg.iti.et3am.dto.MealDTO;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.service.interfaces.RestaurantService;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.service.networkapi.CalculateRoute;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wael M Elmahask
 */
@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CalculateRoute calculateRoute;

    @Override
    public String addRestaurant(Restaurants restaurant) throws Exception {
        return restaurantDao.addRestaurant(restaurant);
    }

    @Override
    public Integer addMeal(Meals meal, Integer restaurantId) throws Exception {
        return restaurantDao.addMeal(meal, restaurantId);
    }

    @Override
    public Restaurants getRestaurantById(Integer id) throws Exception {
        return restaurantDao.getRestaurantById(id);
    }

    @Override
    public List<RestaurantDTO> getRestaurantsList(int page, double latitude, double longitude) throws Exception {
        List<RestaurantDTO> list = restaurantDao.getRestaurantsList(page, latitude, longitude);
        return calculateRoute.calculateRoute(list, latitude, longitude);
    }

    @Override
    public Results getRestaurantsListTrial(int page, double latitude, double longitude) throws Exception {
        Results list = restaurantDao.getRestaurantsListTrial(page, latitude, longitude);
        return calculateRoute.calculateRoute(list, latitude, longitude);
    }

    @Override
    public Results searchInRestaurantsList(int page, double latitude, double longitude, String query) throws Exception {
        Results list = restaurantDao.searchInRestaurantsList(page, latitude, longitude, query);
        return calculateRoute.calculateRoute(list, latitude, longitude);
    }

    @Override
    public List<MealDTO> getMealById(Integer id, int page) throws Exception {
        return restaurantDao.getMealsListById(id, page);
    }

    @Override
    public boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception {
        return restaurantDao.deleteMeal(restaurantId, mealId);
    }

    @Override
    public boolean updateMeal(Integer mealId, Meals meals) throws Exception {
        return restaurantDao.updateMeal(mealId, meals);
    }

    @Override
    public Meals findMealById(Integer id) throws Exception {
        return restaurantDao.findMealById(id);
    }

    @Override
    public RestaurantAdmin login(String email, String password) throws Exception {
        return restaurantDao.login(email, password);
    }

    @Override
    public String getTopMeal(int restId) throws Exception {
        return restaurantDao.getTopMeal(restId);
    }

    @Override
    public List <Restaurants> getAllRestaurantsByCity(String city) throws Exception{
        System.err.println("ana gwa el Service getAllRestaurantsByCity " + restaurantDao.getAllRestaurantsByCity(city).get(0).getCity());
return restaurantDao.getAllRestaurantsByCity(city);
    }
}
