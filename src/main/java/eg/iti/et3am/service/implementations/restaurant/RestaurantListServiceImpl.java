/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations.restaurant;

import eg.iti.et3am.dao.interfaces.restaurant.RestaurantListDao;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.service.interfaces.restaurant.RestaurantListService;
import eg.iti.et3am.service.networkapi.CalculateRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wael M Elmahask
 */
@Service
@Transactional
public class RestaurantListServiceImpl implements RestaurantListService {

    @Autowired
    private RestaurantListDao restaurantDao;

    @Autowired
    private CalculateRoute calculateRoute;

    @Override
    public Results getRestaurantsList(int page, double latitude, double longitude) throws Exception {
        Results list = restaurantDao.getRestaurantsList(page, latitude, longitude);
        return calculateRoute.calculateRoute(list, latitude, longitude);
    }

    @Override
    public Results searchInRestaurantsList(int page, double latitude, double longitude, String query) throws Exception {
        Results list = restaurantDao.searchInRestaurantsList(page, latitude, longitude, query);
        return calculateRoute.calculateRoute(list, latitude, longitude);
    }
}
