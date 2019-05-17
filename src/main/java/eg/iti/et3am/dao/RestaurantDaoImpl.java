package eg.iti.et3am.dao;

import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.Restaurants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wael M Elmahask
 */
@Transactional
@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    Session session = null;
    Transaction tx = null;

    @Override
    public Restaurants getRestaurantById(Integer id) throws Exception {
        session = sessionFactory.openSession();
        Restaurants restaurants = (Restaurants) session.load(Restaurants.class, id);

        Restaurants restaurants1 = (Restaurants) restaurants.clone();
        restaurants1.setMealses(getMealsById(restaurants.getRestaurantId()));
//        tx = session.getTransaction();
//        session.beginTransaction();
//        tx.commit();
        return restaurants1;
//        return restaurants;
    }

    @Override
    public Restaurants getRestaurantDetailsById(Integer id) throws Exception {
        session = sessionFactory.openSession();
        Restaurants restaurants = (Restaurants) session.load(Restaurants.class, id);
        Restaurants restaurants1 = (Restaurants) restaurants.clone();
        restaurants1.setMealses(getMealsById(restaurants.getRestaurantId()));
//        tx = session.getTransaction();
//        session.beginTransaction();
//        tx.commit();
        return restaurants1;
    }

    @Override
    public List<Restaurants> getRestaurantsList() throws Exception {
        session = sessionFactory.openSession();
//        tx = session.beginTransaction();
        List<Restaurants> restaurantses = session.createCriteria(Restaurants.class).list();

        // Create another array to be sent on response
        List<Restaurants> restaurantList = new ArrayList<>();
//          Restaurants restaurantsResponse = new Restaurants();
//          Restaurants restaurantsResponse1 = (Restaurants) restaurantsResponse.clone();

        for (Restaurants restaurants : restaurantses) {
            Restaurants restaurantsResponse = (Restaurants) restaurants.clone();
            restaurantsResponse.setMealses(getMealsById(restaurants.getRestaurantId()));
            restaurantList.add(restaurantsResponse);
        }
//        tx.commit();
//        session.close();
        return restaurantList;
    }

    @Override
    public Set<Meals> getMealsById(Integer id) throws Exception {
        session = sessionFactory.openSession();
//        tx = session.beginTransaction();
        List<Meals> mealses = session.createCriteria(Meals.class)
                .add(Restrictions.eq("restaurants.restaurantId", id)).list();

        // Create another array to be sent on response
        Set<Meals> mealList = new HashSet<>();
        for (Meals meals : mealses) {
            Meals mealsResponse = new Meals();
            mealsResponse.setMealId(meals.getMealId());
            mealsResponse.setMealName(meals.getMealName());
            mealsResponse.setMealValue(meals.getMealValue());
            //mealsResponse.setRestaurants(meals.getRestaurants());
            mealList.add(mealsResponse);
        }
//        tx.commit();
//        session.close();
        return mealList;
    }

    @Override
    public List<Meals> getMealById(Integer id) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<Meals> mealses = session.createCriteria(Meals.class)
                .add(Restrictions.eq("restaurants.restaurantId", id)).list();

        // Create another array to be sent on response
        List<Meals> mealList = new ArrayList<>();
        for (Meals meals : mealses) {
            Meals mealsResponse = new Meals();
            mealsResponse.setMealId(meals.getMealId());
            mealsResponse.setMealName(meals.getMealName());
            mealsResponse.setMealValue(meals.getMealValue());
            //mealsResponse.setRestaurants(meals.getRestaurants());
            mealList.add(mealsResponse);
        }
        tx.commit();
        session.close();
        return mealList;
    }

    @Override
    public String addRestaurant(Restaurants restaurant) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        System.out.println(session.save(restaurant) + "~~~~~~~~~~~~");
        tx.commit();
        String id = (String) session.getIdentifier(restaurant);
        System.out.println(restaurant.getRestaurantId() + "\t" + id + "\t" + "~~~~~~~~~~~~~~~~~~~~");
        session.close();
        return id;
    }

    @Override
    public Integer addMeal(Meals meal) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(meal);
        tx.commit();
        Integer id = (Integer) session.getIdentifier(meal);
        session.close();
        return id;
    }

}
