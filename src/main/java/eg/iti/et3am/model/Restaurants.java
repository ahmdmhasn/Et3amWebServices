package eg.iti.et3am.model;
// Generated Jul 4, 2019 9:55:21 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Restaurants generated by hbm2java
 */
@Entity
@Table(name = "restaurants",
        catalog = "heroku_24f192cc0bbf6af"
)
public class Restaurants implements java.io.Serializable, Cloneable, Comparable<Restaurants> {

    private Integer restaurantId;
    private String restaurantName;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    private String restaurantImage;
    private Set<RestaurantAdmin> restaurantAdmins = new HashSet<RestaurantAdmin>(0);
    private Set<Meals> mealses = new HashSet<Meals>(0);
    private Set<UserUsedCoupon> userUsedCoupons = new HashSet<UserUsedCoupon>(0);

    public Restaurants() {
    }

    public Restaurants(String restaurantName, String city, String country, double latitude, double longitude) {
        this.restaurantName = restaurantName;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Restaurants(String restaurantName, String city, String country, double latitude, double longitude, String restaurantImage, Set<RestaurantAdmin> restaurantAdmins, Set<Meals> mealses, Set<UserUsedCoupon> userUsedCoupons) {
        this.restaurantName = restaurantName;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.restaurantImage = restaurantImage;
        this.restaurantAdmins = restaurantAdmins;
        this.mealses = mealses;
        this.userUsedCoupons = userUsedCoupons;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "restaurant_id", unique = true, nullable = false)
    public Integer getRestaurantId() {
        return this.restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Column(name = "restaurant_name", nullable = false, length = 50)
    public String getRestaurantName() {
        return this.restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Column(name = "city", nullable = false, length = 45)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "country", nullable = false, length = 45)
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "latitude", nullable = false, precision = 22, scale = 0)
    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude", nullable = false, precision = 22, scale = 0)
    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "restaurant_image", length = 45)
    public String getRestaurantImage() {
        return this.restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
    public Set<RestaurantAdmin> getRestaurantAdmins() {
        return this.restaurantAdmins;
    }

    public void setRestaurantAdmins(Set<RestaurantAdmin> restaurantAdmins) {
        this.restaurantAdmins = restaurantAdmins;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
    public Set<Meals> getMealses() {
        return this.mealses;
    }

    public void setMealses(Set<Meals> mealses) {
        this.mealses = mealses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
    public Set<UserUsedCoupon> getUserUsedCoupons() {
        return this.userUsedCoupons;
    }

    public void setUserUsedCoupons(Set<UserUsedCoupon> userUsedCoupons) {
        this.userUsedCoupons = userUsedCoupons;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Restaurants o) {
        return new Double(getLatitude()).compareTo(o.getLatitude());
    }
}
