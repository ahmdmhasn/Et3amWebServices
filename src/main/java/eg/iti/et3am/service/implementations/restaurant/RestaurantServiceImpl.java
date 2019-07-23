package eg.iti.et3am.service.implementations.restaurant;

import eg.iti.et3am.dao.interfaces.restaurant.RestaurantDao;
import eg.iti.et3am.dto.MealDTO;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.service.interfaces.restaurant.RestaurantService;
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
    public List<MealDTO> getMealById(Integer id, int page) throws Exception {
        return restaurantDao.getMealsListById(id, page);
    }

    @Override
    public boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception {
        return restaurantDao.deleteMeal(restaurantId, mealId);
    }

    @Override
    public MealDTO updateMeal(Integer mealId, Meals meals) throws Exception {
        return restaurantDao.updateMeal(mealId, meals);
    }

    @Override
    public MealDTO findMealById(Integer id) throws Exception {
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
    public List<RestaurantDTO> getAllRestaurantsByCity(String city) throws Exception {
        System.err.println("ana gwa el Service getAllRestaurantsByCity " + restaurantDao.getAllRestaurantsByCity(city).get(0).getCity());
        return restaurantDao.getAllRestaurantsByCity(city);
    }

    @Override
    public boolean updateRestaurant(int restaurantId, Restaurants restaurants) throws Exception {

        return restaurantDao.updateRestaurant(restaurantId, restaurants);
    }

    @Override
    public boolean deleterestaurant(int restaurantId) throws Exception {
        return restaurantDao.deleteRestaurant(restaurantId);
    }

    @Override
    public boolean updateAdmin(int adminId, RestaurantAdmin admin) throws Exception {
        return restaurantDao.updateRestaurantAdmin(adminId, admin);
    }
}
