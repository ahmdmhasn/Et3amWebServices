package eg.iti.et3am.controller;

import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.model.Status;
import eg.iti.et3am.service.interfaces.RestaurantService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hibernate.jpa.internal.EntityManagerImpl.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // List of nearest restaurants
    @RequestMapping(value = "/rest_list", method = RequestMethod.GET, produces = "application/json")
    public List<Restaurants> RestaurantsList() {
        List<Restaurants> restaurantList = null;
        try {
            restaurantList = restaurantService.getRestaurantsList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return restaurantList;
    }

    // Restaurant deatails
    @RequestMapping(value = "/{rest_id}/meals", method = RequestMethod.GET)
    public List<Meals> getMealById(@PathVariable("rest_id") Integer id) {
        List<Meals> meals = null;
        try {
            meals = restaurantService.getMealById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return meals;
    }

    // Get Restaurant by id
    @RequestMapping(value = "/rest/{rest_id}", method = RequestMethod.GET)
    public Restaurants getRestaurantById(@PathVariable("rest_id") Integer id) {
        Restaurants restaurants = null;
        try {
            restaurants = restaurantService.getRestaurantById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return restaurants;
    }

    // Add new meal to restaurant
    @RequestMapping(value = "/rest/{rest_id}/addMeal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Status addMeal(@PathVariable("rest_id") Integer resturantId, @RequestBody Meals meal) {
        try {
            Integer id = restaurantService.addMeal(meal, resturantId);
            if (resturantId != null) {
                return new Status(1, "Meal Is Added");
            } else {
                return new Status(0, "Meal Not Added Becouse No Resturant by this ID " + id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Status(0, ex.getMessage());
        }
    }

    @RequestMapping(value = "/rest/{rest_id}/deleteMeal/{meal_id}", method = RequestMethod.DELETE)
    public Status removeMeal(@PathVariable("meal_id") Integer id, @PathVariable("rest_id") Integer resturantId) {
        try {
            boolean deleted = restaurantService.deleteMeal(resturantId, id);
            if (deleted == true) {
                return new Status(1, "Meal Is deleted");
            } else {
                return new Status(0, "Meal Not Deleted Becouse No Resturant or Meal by this ID " + id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Status(0, ex.getMessage());
        }
    }

    @RequestMapping(value = "/rest/{rest_id}/updateMeal/{meal_id}", method = RequestMethod.PUT)
    public ResponseEntity<Meals> updateMeal(@PathVariable("meal_id") Integer id, @RequestBody Meals meals) throws Exception {
        Meals meal = restaurantService.findMealById(id);
        if (meal == null) {
            LOG.info("Meal with id {} not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            restaurantService.updateMeal(id, meals);
        } catch (Exception ex) {
            Logger.getLogger(RestaurantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        restaurantService.updateMeal(id, meals);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }
}
