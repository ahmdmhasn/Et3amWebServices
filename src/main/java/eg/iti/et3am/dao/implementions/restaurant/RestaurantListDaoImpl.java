/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.implementions.restaurant;

import eg.iti.et3am.dao.interfaces.restaurant.RestaurantListDao;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wael M Elmahask
 */
@Repository
public class RestaurantListDaoImpl implements RestaurantListDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    private Session session = null;

    private final int pageSize = 10;

    @Override
    public Results getRestaurantsList(int pageNumber, double latitude, double longitude) throws Exception {

        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Restaurants.class);
        criteria.addOrder(Order.asc("latitude"));
        criteria.addOrder(Order.asc("longitude"));
        long count = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        criteria.setFetchSize(10);

        List<Restaurants> restaurantses = criteria.list();

        List<RestaurantDTO> listRDtos = new ArrayList<>();
        for (Restaurants restaurants : restaurantses) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();

            restaurantDTO.setRestaurantID(restaurants.getRestaurantId());
            restaurantDTO.setRestaurantName(restaurants.getRestaurantName());
            restaurantDTO.setRestaurantImage(restaurants.getRestaurantImage());
            restaurantDTO.setCity(restaurants.getCity());
            restaurantDTO.setCountry(restaurants.getCountry());
            restaurantDTO.setLatitude(restaurants.getLatitude());
            restaurantDTO.setLongitude(restaurants.getLongitude());
            double distance = Utils.distance(restaurants.getLatitude(), latitude, restaurants.getLongitude(), longitude, 0.0, 0.0);
            restaurantDTO.setDistance(distance);

            listRDtos.add(restaurantDTO);
        }

        Results results = new Results();
        results.setPage(pageNumber);
        results.setTotalPages(count);
        results.setTotalResults(count);
        results.setResults(listRDtos);
        return results;

    }

    @Override
    public Results searchInRestaurantsList(int pageNumber, double latitude, double longitude, String query) throws Exception {

        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Restaurants.class);
        Criterion restaurantName = Restrictions.ilike("restaurantName", "" + query + "", MatchMode.ANYWHERE);
        Criterion city = Restrictions.ilike("city", query, MatchMode.ANYWHERE);
        LogicalExpression orExp = Restrictions.or(restaurantName, city);
        criteria.add(orExp);
        criteria.addOrder(Order.asc("restaurantName"));
        long count = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        List<Restaurants> restaurantses = criteria.list();

        List<RestaurantDTO> listRDtos = new ArrayList<>();

        for (Restaurants restaurants : restaurantses) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();

            restaurantDTO.setRestaurantID(restaurants.getRestaurantId());
            restaurantDTO.setRestaurantName(restaurants.getRestaurantName());
            restaurantDTO.setRestaurantImage(restaurants.getRestaurantImage());
            restaurantDTO.setCity(restaurants.getCity());
            restaurantDTO.setCountry(restaurants.getCountry());
            restaurantDTO.setLatitude(restaurants.getLatitude());
            restaurantDTO.setLongitude(restaurants.getLongitude());
            double distance = Utils.distance(restaurants.getLatitude(), latitude, restaurants.getLongitude(), longitude, 0.0, 0.0);
            restaurantDTO.setDistance(distance);
            listRDtos.add(restaurantDTO);
        }
        Results results = new Results();
        results.setPage(pageNumber);
        results.setTotalPages(count);
        results.setTotalResults(count);
        results.setResults(listRDtos);
        return results;
    }

}
