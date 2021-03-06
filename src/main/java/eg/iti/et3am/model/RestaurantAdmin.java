package eg.iti.et3am.model;
// Generated Jul 4, 2019 9:55:21 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * RestaurantAdmin generated by hbm2java
 */
@Entity
@Table(name="restaurant_admin"
    ,catalog="heroku_24f192cc0bbf6af"
    , uniqueConstraints = @UniqueConstraint(columnNames="restaurant_admin_email") 
)
public class RestaurantAdmin  implements java.io.Serializable {


     private Integer restaurantAdminId;
     private Restaurants restaurants;
     private String restaurantAdminEmail;
     private String restaurantAdminPassword;

    public RestaurantAdmin() {
    }

    public RestaurantAdmin(Restaurants restaurants, String restaurantAdminEmail, String restaurantAdminPassword) {
       this.restaurants = restaurants;
       this.restaurantAdminEmail = restaurantAdminEmail;
       this.restaurantAdminPassword = restaurantAdminPassword;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="restaurant_admin_id", unique=true, nullable=false)
    public Integer getRestaurantAdminId() {
        return this.restaurantAdminId;
    }
    
    public void setRestaurantAdminId(Integer restaurantAdminId) {
        this.restaurantAdminId = restaurantAdminId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="restaurants_restaurant_id", nullable=false)
    public Restaurants getRestaurants() {
        return this.restaurants;
    }
    
    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    
    @Column(name="restaurant_admin_email", unique=true, nullable=false, length=45)
    public String getRestaurantAdminEmail() {
        return this.restaurantAdminEmail;
    }
    
    public void setRestaurantAdminEmail(String restaurantAdminEmail) {
        this.restaurantAdminEmail = restaurantAdminEmail;
    }

    
    @Column(name="restaurant_admin_password", nullable=false, length=45)
    public String getRestaurantAdminPassword() {
        return this.restaurantAdminPassword;
    }
    
    public void setRestaurantAdminPassword(String restaurantAdminPassword) {
        this.restaurantAdminPassword = restaurantAdminPassword;
    }




}


