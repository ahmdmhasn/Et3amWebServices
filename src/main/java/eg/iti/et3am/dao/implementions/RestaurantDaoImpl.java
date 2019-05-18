package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.RestaurantDao;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.Restaurants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
        //Restaurants restaurants1 = (Restaurants) restaurants.clone();
//        restaurants1.setMealses(getMealsById(restaurants.getRestaurantId()));
//        tx = session.getTransaction();
//        session.beginTransaction();
//        tx.commit();
        return restaurants;
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
//            restaurantsResponse.setMealses(getMealsById(restaurants.getRestaurantId()));
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
    public Integer addMeal(Meals meal, Integer restaurantId) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Restaurants r = (Restaurants) session.load(Restaurants.class, restaurantId);
//        r.getMealses().iterator().next().;
        meal.setRestaurants(r);
        session.save(meal);
        tx.commit();
        Integer id = (Integer) session.getIdentifier(meal);
        session.close();
        return id;
    }

    @Override
    public boolean deleteMeal(Integer restaurantId, Integer mealId) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Restaurants r = (Restaurants) session.load(Restaurants.class, restaurantId);
        for (Meals meal : r.getMealses()) {
            if (Objects.equals(mealId, meal.getMealId())) {
                r.getMealses().remove(meal);
                session.delete(meal);
                tx.commit();
                return true;
            }
        }
        tx.commit();
        session.close();
        return false;
    }

    @Override
    public boolean updateMeal(Integer mealId, Meals meals) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Meals meal = (Meals) session.load(Meals.class, mealId);
        meal.setMealName(meals.getMealName());
        meal.setMealValue(meals.getMealValue());
        meal.setMealImage(meals.getMealImage());
        session.update(meal);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public Meals findMealById(Integer id) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Meals meals = (Meals) session.load(Meals.class, id);
        tx.commit();
        return meals;
    }

}
