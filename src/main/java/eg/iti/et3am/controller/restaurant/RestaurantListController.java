/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller.restaurant;

import eg.iti.et3am.dto.Results;
import eg.iti.et3am.service.interfaces.restaurant.RestaurantListService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wael M Elmahask
 */
@RestController
@RequestMapping("/restaurant")
public class RestaurantListController {

    @Autowired
    private RestaurantListService restaurantService;

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
                Results results = restaurantService.getRestaurantsList(page, latitude, longitude);
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
            result.put("message", ex.getMessage());
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
    
      //  list of restaurants for admin app
    @RequestMapping(value = "/list_without_location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> RestaurantsListWithoutLocation(@RequestParam("page") int page) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (page <= 0) {
                result.put("code", 0);
                result.put("message", "page must be greater than 0");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                Results results = restaurantService.getRestaurantListWithoutNeedForLocation(page);
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
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

}
