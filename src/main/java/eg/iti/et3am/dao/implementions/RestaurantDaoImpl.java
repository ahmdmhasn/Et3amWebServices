package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.RestaurantDao;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import eg.iti.et3am.utils.Utils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Wael M Elmahask
 */
@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    Session session = null;

    @Override
    public Restaurants getRestaurantById(Integer id) throws Exception {
        session = sessionFactory.getCurrentSession();
        Restaurants restaurants = (Restaurants) session.load(Restaurants.class, id);
        //Copy Data from object To another
        Restaurants restaurants1 = (Restaurants) restaurants.clone();
        restaurants1.setMealses(getMealsSetById(restaurants.getRestaurantId()));
        return restaurants1;
    }

    @Override
    public List<RestaurantDTO> getRestaurantsList(int pageNumber, double latitude, double longitude) throws Exception {

        int pageSize = 10;

        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Restaurants.class);
        criteria.addOrder(Order.asc("latitude"));
        criteria.addOrder(Order.asc("longitude"));
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
        return listRDtos;
    }

    @Override
    public List<RestaurantDTO> searchInRestaurantsList(int pageNumber, double latitude, double longitude, String query) throws Exception {

        int pageSize = 10;

        session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Restaurants.class);
//        criteria.add(Restrictions.ilike("restaurantName", query, MatchMode.ANYWHERE));
//        criteria.add(Restrictions.ilike("city", query, MatchMode.ANYWHERE));
        Criterion restaurantName = Restrictions.ilike("restaurantName", ""+query+"", MatchMode.ANYWHERE);
        Criterion city = Restrictions.ilike("city", query, MatchMode.ANYWHERE);
        System.out.println("query+++++++++ "+query.getBytes("UTF-8"));
        LogicalExpression orExp = Restrictions.or(restaurantName, city);
        criteria.add(orExp);
        criteria.addOrder(Order.asc("restaurantName"));
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
        return listRDtos;
    }

    @Override
    public List<Restaurants> getRestaurantsListWithMeals() throws Exception {
        session = sessionFactory.getCurrentSession();

        List<Restaurants> restaurantses = session.createCriteria(Restaurants.class).list();

        // Create another array to be sent on response
        List<Restaurants> restaurantList = new ArrayList<>();
        for (Restaurants restaurants : restaurantses) {
            Restaurants restaurantsResponse = (Restaurants) restaurants.clone();
            restaurantsResponse.setMealses(getMealsSetById(restaurants.getRestaurantId()));
            restaurantList.add(restaurantsResponse);
        }
        Collections.sort(restaurantList);
        return restaurantList;
    }

    @Override
    public Set<Meals> getMealsSetById(Integer id) throws Exception {
        session = sessionFactory.getCurrentSession();

        List<Meals> mealses = session.createCriteria(Meals.class)
                .add(Restrictions.eq("restaurants.restaurantId", id)).list();

        // Create another array to be sent on response
        Set<Meals> mealList = new HashSet<>();
        for (Meals meals : mealses) {
            Meals mealsResponse = new Meals();
            mealsResponse.setMealId(meals.getMealId());
            mealsResponse.setMealName(meals.getMealName());
            mealsResponse.setMealValue(meals.getMealValue());
            mealList.add(mealsResponse);
        }
        return mealList;
    }

    @Override
    public List<Meals> getMealsListById(Integer id) throws Exception {
        session = sessionFactory.getCurrentSession();

        List<Meals> mealses = session.createCriteria(Meals.class)
                .add(Restrictions.eq("restaurants.restaurantId", id)).list();

        // Create another array to be sent on response
        List<Meals> mealList = new ArrayList<>();
        for (Meals meals : mealses) {
            Meals mealsResponse = new Meals();
            mealsResponse.setMealId(meals.getMealId());
            mealsResponse.setMealName(meals.getMealName());
            mealsResponse.setMealValue(meals.getMealValue());
            mealsResponse.setMealImage(meals.getMealImage());
            mealsResponse.setRestaurants(meals.getRestaurants());
            mealList.add(mealsResponse);
        }

        return mealList;
    }

    @Override
    public Meals findMealById(Integer id) throws Exception {
        session = sessionFactory.getCurrentSession();

        Meals meals = (Meals) session.load(Meals.class, id);
        Meals meal2 = new Meals(meals.getRestaurants(), meals.getMealName(), meals.getMealValue(), meals.getMealImage());
        return meal2;
    }

    @Override
    public String addRestaurant(Restaurants restaurant) throws Exception {
        session = sessionFactory.getCurrentSession();

        session.save(restaurant);

        int id = (int) session.getIdentifier(restaurant);

        return String.valueOf(id);
    }

    @Override
    public Integer addMeal(Meals meal, Integer restaurantId) throws Exception {
        session = sessionFactory.getCurrentSession();

        Restaurants r = (Restaurants) session.load(Restaurants.class, restaurantId);
        meal.setRestaurants(r);
        session.save(meal);

        int id = (Integer) session.getIdentifier(meal);
        return id;
    }

    @Override
    public RestaurantAdmin login(String email, String password) throws Exception {
        session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(RestaurantAdmin.class);
        criteria.add(Restrictions.eq("restaurantAdminEmail", email));
        criteria.add(Restrictions.eq("restaurantAdminPassword", password));

        RestaurantAdmin admin = (RestaurantAdmin) criteria.uniqueResult();

        Restaurants restaurant = admin.getRestaurants();
        System.out.println(restaurant.getCity() + "city");
        RestaurantAdmin admin2 = new RestaurantAdmin();
        Restaurants restaurant2 = new Restaurants(restaurant.getRestaurantName(),
                restaurant.getCity(), restaurant.getCountry(), restaurant.getLatitude(),
                restaurant.getLongitude(), restaurant.getRestaurantImage(), null, null, null);
        restaurant2.setRestaurantId(restaurant.getRestaurantId());

        admin2.setRestaurantAdminId(admin.getRestaurantAdminId());
        admin2.setRestaurantAdminEmail(admin.getRestaurantAdminEmail());
        admin2.setRestaurants(restaurant2);

        return admin2;
    }

    @Override
    public boolean updateMeal(Integer mealId, Meals meals) throws Exception {
        session = sessionFactory.getCurrentSession();
        try {
            Meals meal = (Meals) session.load(Meals.class, mealId);
            meal.setMealName(meals.getMealName());
            meal.setMealValue(meals.getMealValue());
            meal.setMealImage(meals.getMealImage());
            session.update(meal);
            return true;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception {
        session = sessionFactory.getCurrentSession();

        try {
            Restaurants r = (Restaurants) session.load(Restaurants.class, restaurantId);
            for (Meals meal : r.getMealses()) {
                if (Objects.equals(mealId, meal.getMealId())) {
                    r.getMealses().remove(meal);
                    session.delete(meal);
                    return true;
                }
            }
            return false;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public String addResturantAdmin(String email, String password, int restaurantId) throws Exception {
        Restaurants restaurants = getRestaurantById(restaurantId);
        System.out.println(restaurants.getCountry() + "country nnnnnnn");
        session = sessionFactory.getCurrentSession();
        RestaurantAdmin restaurantAdmin = new RestaurantAdmin();
        restaurantAdmin.setRestaurantAdminEmail(email);
        restaurantAdmin.setRestaurantAdminPassword(password);
        restaurantAdmin.setRestaurants(restaurants);
        session.save(restaurantAdmin);
        int id = (int) session.getIdentifier(restaurantAdmin);

        return String.valueOf(id);

    }
}
/*
@Override
    public List<Restaurants> getRestaurantsList(int pageNumber, double latitude, double longitude) throws Exception {

        int pageSize = 10;

        session = sessionFactory.getCurrentSession();
        List<Restaurants> restaurantses = session.createCriteria(Restaurants.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize).list(); //.addOrder(Order.asc("distance")) //sort
        // Create another array to be sent on response
        List<Restaurants> restaurantListSort = new ArrayList<>();
        for (Restaurants restaurants : restaurantses) {
            Restaurants restaurantsResponse = (Restaurants) restaurants.clone();
            double distance = Utils.distance(restaurantsResponse.getLatitude(), latitude, restaurantsResponse.getLongitude(), longitude, 0.0, 0.0);
            restaurantsResponse.setDistance(distance);
            restaurantListSort.add(restaurantsResponse);
            Collections.sort(restaurantListSort, new Comparator<Restaurants>() {
                @Override
                public int compare(Restaurants u1, Restaurants u2) {
                    return new Double(u1.getDistance()).compareTo(u2.getDistance());
                }
            });
        }
        restaurantListSort.subList(0, min(restaurantListSort.size(), pageSize));
        for (Restaurants number : restaurantListSort) {
            System.out.println("Number = " + number.getDistance());
        }
        return restaurantListSort;
    }
 */
