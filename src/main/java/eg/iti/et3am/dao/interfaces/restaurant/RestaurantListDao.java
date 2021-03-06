/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces.restaurant;

import eg.iti.et3am.dto.Results;

/**
 *
 * @author Wael M Elmahask
 */
public interface RestaurantListDao {

    Results getRestaurantsList(int page, double latitude, double longitude) throws Exception;
    Results getRestaurantListWithoutNeedForLocation(int page) throws Exception;
    Results searchInRestaurantsList(int pageNumber, double latitude, double longitude, String query) throws Exception;

}
