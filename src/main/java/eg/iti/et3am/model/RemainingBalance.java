package eg.iti.et3am.model;
// Generated May 15, 2019 11:40:21 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RemainingBalance generated by hbm2java
 */
@Entity
@Table(name="remaining_balance"
    ,catalog="heroku_24f192cc0bbf6af"
)
public class RemainingBalance  implements java.io.Serializable {


     private Integer changeId;
     private UserUseCoupon userUseCoupon;
     private float changeValue;

    public RemainingBalance() {
    }

    public RemainingBalance(UserUseCoupon userUseCoupon, float changeValue) {
       this.userUseCoupon = userUseCoupon;
       this.changeValue = changeValue;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="change_id", unique=true, nullable=false)
    public Integer getChangeId() {
        return this.changeId;
    }
    
    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_use_coupon_used_coupon_id", nullable=false)
    public UserUseCoupon getUserUseCoupon() {
        return this.userUseCoupon;
    }
    
    public void setUserUseCoupon(UserUseCoupon userUseCoupon) {
        this.userUseCoupon = userUseCoupon;
    }

    
    @Column(name="change_value", nullable=false, precision=12, scale=0)
    public float getChangeValue() {
        return this.changeValue;
    }
    
    public void setChangeValue(float changeValue) {
        this.changeValue = changeValue;
    }




}


