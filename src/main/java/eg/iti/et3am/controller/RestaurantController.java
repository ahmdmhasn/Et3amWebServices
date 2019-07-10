
package eg.iti.et3am.controller;

import eg.iti.et3am.dao.implementions.RestaurantDaoImpl;
import eg.iti.et3am.dto.MealDTO;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.model.Status;
import eg.iti.et3am.service.interfaces.RestaurantService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.Charsets;
import static org.hibernate.jpa.internal.EntityManagerImpl.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // List of nearest restaurants
    
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> RestaurantsList(@RequestParam("page") int page, @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (page <= 0) {
                result.put("code", 0);
                result.put("message", "page must be greater than 0");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                Results results = restaurantService.getRestaurantsListTrial(page, latitude, longitude);
                if (results != null) {
                    result.put("code", 1);
                    result.put("page", results.getPage());
                    result.put("total_page", results.getTotalPages());
                    result.put("total_results", results.getTotalResults());
                    result.put("results", results.getResults());
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("code", 0);
                    result.put("message", "there are not Coupons");
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(RestaurantController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getMessage() );
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    
    
    
    
    
    
    @RequestMapping(value = "/listByCity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> SearchRestaurantsList(@RequestParam("city") String city) {
        Map<String, Object> result = new HashMap<>();
        try {
            
                
                
                List <Restaurants> list  = restaurantService.getAllRestaurantsByCity(city);
                if (list != null) {
                    result.put("code", 1);
                    result.put("results", list);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("code", 0);
                    result.put("message", "there are not Coupons");
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(RestaurantController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getCause() + " ss");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    
    
    
    
    
    
    
    
    
    

    // search in List of nearest restaurants
    @RequestMapping(value = "/searchList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> searchInRestaurantsList(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude, @RequestParam("query") String query, @RequestParam("page") int page) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (page <= 0 || query.isEmpty()) {
                result.put("code", 0);
                result.put("message", "page must be greater than 0");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                Results restaurantList = restaurantService.searchInRestaurantsList(page, latitude, longitude, query);
                if (restaurantList != null) {
                    result.put("code", 1);
                    result.put("page", restaurantList.getPage());
                    result.put("total_results", restaurantList.getTotalResults());
                    result.put("total_pages", restaurantList.getTotalPages());
                    result.put("results", restaurantList.getResults());
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("code", 0);
                    result.put("message", "there are not restaurants");
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(RestaurantController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    // Restaurant deatails
    @RequestMapping(value = "/{rest_id}/meals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> get(@PathVariable("rest_id") Integer id, @RequestParam("page") int page) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            if (page <= 0) {
                result.put("code", 0);
                result.put("message", "page must be greater than 0");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                List<MealDTO> mealsList = restaurantService.getMealById(id, page);
                if (mealsList != null && !mealsList.isEmpty()) {
                    result.put("code", 1);
                    result.put("page", page);
                    result.put("total_results", mealsList.size());
                    result.put("results", mealsList);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("code", 0);
                    result.put("message", "there are not Coupons");
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(RestaurantController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
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
                return new Status(1, id, "Meal is added");
            } else {
                return new Status(0, "Meal Not Added Becouse No Resturant by this ID " + resturantId);
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return new Status(0, ex.getMessage());
        }
    }

    // Update meal to restaurant
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

    // Remove meal to restaurant
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

    /*---Check if the same username exists---*/
    @RequestMapping(value = "/validate/login", method = RequestMethod.GET)
    public @ResponseBody
    Status login(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return new Status(0, "Email or password must not be empty");
        }

        try {
            RestaurantAdmin admin = restaurantService.login(email, password);
            if (admin != null) {
                return new Status(1, admin);
            } else {
                return new Status(0, "Restaurant doesn't exist!");
            }
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }

    @RequestMapping(value = "/add_restuarant", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> addResturant(@RequestParam("restuarant_name") String restaurantName, @RequestParam("city") String city, @RequestParam("country") String country, @RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude, @RequestParam("restaurant_image") String restaurantImage) {
        Map<String, Object> response = new HashMap<>();

        if (!restaurantName.isEmpty() && !city.isEmpty() && !country.isEmpty() && longitude != 0 && latitude != 0) {
            try {
                Restaurants restaurants = new Restaurants();
                restaurants.setRestaurantName(restaurantName);
                restaurants.setCity(city);
                restaurants.setCountry(country);
                restaurants.setLongitude(longitude);
                restaurants.setLatitude(latitude);
                restaurants.setRestaurantImage(restaurantImage);
                int idOfRestuarant = Integer.parseInt(restaurantService.addRestaurant(restaurants));
                response.put("code", 1);
                response.put("message", "restuarant added succesfully");
                response.put("id", idOfRestuarant);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } catch (Exception e) {
                response.put("code", 0);
                response.put("message", " exception while adding resturant \n" + e.toString());
                e.printStackTrace();
                return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            response.put("code", 0);
            response.put("message", "restuarant data must not be empty.");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
    
    
    @RequestMapping(value = "/{rest_id}/top_meal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getTopMeal(@PathVariable("rest_id") Integer id) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            String topMeal = restaurantService.getTopMeal(id);
          
                result.put("code", 1);
                result.put("topMeal", topMeal);
                return new ResponseEntity<>(result, HttpStatus.OK);
           
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(RestaurantController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("code", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

