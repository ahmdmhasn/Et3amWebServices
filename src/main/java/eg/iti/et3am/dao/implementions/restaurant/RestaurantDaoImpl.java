package eg.iti.et3am.dao.implementions.restaurant;

import eg.iti.et3am.dao.interfaces.restaurant.RestaurantDao;
import eg.iti.et3am.dto.MealDTO;
import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.model.Meals;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.model.UserUsedCoupon;
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

    private Session session = null;

    private final int pageSize = 10;

  
   
    public Restaurants getRestaurantById(Integer id) throws Exception {

        session = sessionFactory.getCurrentSession();

        Restaurants restaurants = (Restaurants) session.load(Restaurants.class, id);
        //Copy Data from object To another
        Restaurants restaurants1 = (Restaurants) restaurants.clone();
        restaurants1.setMealses(getMealsSetById(restaurants.getRestaurantId()));
        return restaurants1;
    }

    @Override
    public List<RestaurantDTO> getAllRestaurantsByCity(String city) {

        session = sessionFactory.getCurrentSession();
        List<Restaurants> mySearchList = session.createCriteria(Restaurants.class).
                add(Restrictions.eq("city", city)).add(Restrictions.eq("status", 0)).list();
        if (mySearchList != null) {
            System.err.println("not null ");
            List<RestaurantDTO> selectedRestaurants = new ArrayList<>();
            for (Restaurants restaurants : mySearchList) {

                System.out.println("city:" + restaurants.getCity());
                RestaurantDTO restaurantDTO = new RestaurantDTO();

                restaurantDTO.setRestaurantID(restaurants.getRestaurantId());
                restaurantDTO.setRestaurantName(restaurants.getRestaurantName());
                restaurantDTO.setRestaurantImage(restaurants.getRestaurantImage());
                restaurantDTO.setCity(restaurants.getCity());
                restaurantDTO.setCountry(restaurants.getCountry());
                restaurantDTO.setLatitude(restaurants.getLatitude());
                restaurantDTO.setLongitude(restaurants.getLongitude());
                restaurantDTO.setDistance(0);
                selectedRestaurants.add(restaurantDTO);
            }

            return selectedRestaurants;
        } else {
            System.err.println("ERROR....");
            return null;
        }

    }

    // Not Used
    @Override
    public List<Restaurants> getRestaurantsListWithMeals() throws Exception {

        session = sessionFactory.getCurrentSession();

        List<Restaurants> restaurantses = session.createCriteria(Restaurants.class).add(Restrictions.eq("status", 0)).list();

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

    public List<MealDTO> getMealsListById(Integer id, int page) throws Exception {
        session = sessionFactory.getCurrentSession();

        List<Meals> mealses = session.createCriteria(Meals.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .add(Restrictions.eq("restaurants.restaurantId", id)).list();

        // Create another array to be sent on response
        List<MealDTO> mealList = new ArrayList<>();
        for (Meals meals : mealses) {
            MealDTO mealsResponse = new MealDTO();
            mealsResponse.setMealId(meals.getMealId());
            mealsResponse.setMealName(meals.getMealName());
            mealsResponse.setMealValue(meals.getMealValue());
            mealsResponse.setMealImage(meals.getMealImage());
            //mealsResponse.setRestaurants(meals.getRestaurants());
            mealList.add(mealsResponse);
        }

        return mealList;
    }

    @Override
    public MealDTO findMealById(Integer id) throws Exception {

        session = sessionFactory.getCurrentSession();
        Meals meals = (Meals) session.load(Meals.class, id);
        MealDTO mealDTO = new MealDTO();
        mealDTO.setMealId(meals.getMealId());
        mealDTO.setMealImage(meals.getMealImage());
        mealDTO.setMealValue(meals.getMealValue());
        mealDTO.setCount(0);
        return mealDTO;
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
    public MealDTO updateMeal(Integer mealId, Meals meals) throws Exception {
        session = sessionFactory.getCurrentSession();

        Meals meal = (Meals) session.createCriteria(Meals.class).add(Restrictions.eq("mealId", mealId)).uniqueResult();
        if (meal != null) {
            meal.setMealName(meals.getMealName());
            meal.setMealValue(meals.getMealValue());
            meal.setMealImage(meals.getMealImage());
            session.update(meal);
            MealDTO mealDTO = new MealDTO(mealId, meal.getMealName(), meal.getMealValue(), meal.getMealImage());
            return mealDTO;
        } else {
            return null;
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

    @Override

    public boolean updateRestaurant(int restaurantId, Restaurants restaurants) throws Exception {
        session = sessionFactory.getCurrentSession();
        try {
            Restaurants restaurant = (Restaurants) session.load(Restaurants.class, restaurantId);
            restaurant.setRestaurantName(restaurants.getRestaurantName());
            restaurant.setCity(restaurants.getCity());
            restaurant.setCountry(restaurants.getCountry());
            restaurant.setLongitude(restaurants.getLongitude());
            restaurant.setLatitude(restaurants.getLatitude());
            restaurant.setRestaurantImage(restaurants.getRestaurantImage());
            session.update(restaurant);
            return true;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRestaurant(int restaurantId) throws Exception {

        session = sessionFactory.getCurrentSession();

        try {
            Restaurants restaurants = (Restaurants) session.load(Restaurants.class, restaurantId);
            restaurants.setStatus(1);
            session.update(restaurants);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRestaurantAdmin(int adminId, RestaurantAdmin admin) throws Exception {
        session = sessionFactory.getCurrentSession();
        try {
            RestaurantAdmin restaurantAdmin = (RestaurantAdmin) session.load(RestaurantAdmin.class, adminId);
            restaurantAdmin.setRestaurantAdminEmail(admin.getRestaurantAdminEmail());
            restaurantAdmin.setRestaurantAdminPassword(admin.getRestaurantAdminPassword());
            session.update(restaurantAdmin);
            return true;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return false;
        }
    }
//TODO: get restaurants list without location

    public String getTopMeal(int restId) throws Exception {
        Set<Meals> mealList = getMealsSetById(restId);
        session = sessionFactory.getCurrentSession();
        List<MealDTO> mealsDTOList = new ArrayList<>();
        for (Meals meal : mealList) {
            long count = (long) session.createCriteria(UserUsedCoupon.class)
                    .createAlias("meals", "m")
                    .add(Restrictions.eq("m.mealId", meal.getMealId()))
                    .setProjection(Projections.rowCount()).uniqueResult();

            MealDTO mealDTO = new MealDTO(meal.getMealId(),
                    meal.getMealName(),
                    meal.getMealValue(),
                    meal.getMealImage(),
                    (int) count);
            mealsDTOList.add(mealDTO);
        }
        int bestMeal = 0;
        int bestCount = mealsDTOList.get(0).getCount();
        for (int i = 1; i < mealsDTOList.size(); i++) {
            if (mealsDTOList.get(i).getCount() > bestCount) {
                bestCount = mealsDTOList.get(i).getCount();
                bestMeal = i;

            }
        }
        if (mealsDTOList.get(bestMeal).getCount() == 0) {
            return "no top meal";
        }
        return mealsDTOList.get(bestMeal).getMealName();
    }
}
