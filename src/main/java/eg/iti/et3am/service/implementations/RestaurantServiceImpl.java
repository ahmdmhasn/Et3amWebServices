package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.RestaurantDao;
import eg.iti.et3am.service.interfaces.RestaurantService;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.service.networkapi.CalculateRoute;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wael M Elmahask
 */
@Service
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
    public List<Restaurants> getRestaurantsList(double latitude, double longitude) throws Exception {
        System.out.println("lat ->" + latitude);
        List<Restaurants> list = restaurantDao.getRestaurantsList(latitude, longitude);
        return calculateRoute.calculateRoute(list, latitude, longitude);
    }

    @Override
    public List<Meals> getMealById(Integer id) throws Exception {
        return restaurantDao.getMealsListById(id);
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
}
