package eg.iti.et3am.model;
// Generated May 15, 2019 11:40:21 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Coupons generated by hbm2java
 */
@Entity
@Table(name="coupons"
    ,catalog="heroku_24f192cc0bbf6af"
    , uniqueConstraints = @UniqueConstraint(columnNames="coupon_barcode") 
)
public class Coupons  implements java.io.Serializable {


     private String couponId;
     private Users users;
     private String couponValue;
     private String couponBarcode;
     private Date creationDate;
     private byte[] couponQrcode;
     private Set<AvailableCoupons> availableCouponses = new HashSet<AvailableCoupons>(0);
     private Set<UserBalanceCoupon> userBalanceCoupons = new HashSet<UserBalanceCoupon>(0);
     private Set<UserReserveCoupon> userReserveCoupons = new HashSet<UserReserveCoupon>(0);

    public Coupons() {
    }

	
    public Coupons(String couponId, Users users, String couponValue, String couponBarcode) {
        this.couponId = couponId;
        this.users = users;
        this.couponValue = couponValue;
        this.couponBarcode = couponBarcode;
    }
    public Coupons(String couponId, Users users, String couponValue, String couponBarcode, Date creationDate, byte[] couponQrcode, Set<AvailableCoupons> availableCouponses, Set<UserBalanceCoupon> userBalanceCoupons, Set<UserReserveCoupon> userReserveCoupons) {
       this.couponId = couponId;
       this.users = users;
       this.couponValue = couponValue;
       this.couponBarcode = couponBarcode;
       this.creationDate = creationDate;
       this.couponQrcode = couponQrcode;
       this.availableCouponses = availableCouponses;
       this.userBalanceCoupons = userBalanceCoupons;
       this.userReserveCoupons = userReserveCoupons;
    }
   
     @Id 

    
    @Column(name="coupon_id", unique=true, nullable=false, length=128)
    public String getCouponId() {
        return this.couponId;
    }
    
    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="donator_id", nullable=false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }

    
    @Column(name="coupon_value", nullable=false, length=45)
    public String getCouponValue() {
        return this.couponValue;
    }
    
    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
    }

    
    @Column(name="coupon_barcode", unique=true, nullable=false, length=45)
    public String getCouponBarcode() {
        return this.couponBarcode;
    }
    
    public void setCouponBarcode(String couponBarcode) {
        this.couponBarcode = couponBarcode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_date", length=26)
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    @Column(name="coupon_qrcode")
    public byte[] getCouponQrcode() {
        return this.couponQrcode;
    }
    
    public void setCouponQrcode(byte[] couponQrcode) {
        this.couponQrcode = couponQrcode;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="coupons")
    public Set<AvailableCoupons> getAvailableCouponses() {
        return this.availableCouponses;
    }
    
    public void setAvailableCouponses(Set<AvailableCoupons> availableCouponses) {
        this.availableCouponses = availableCouponses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="coupons")
    public Set<UserBalanceCoupon> getUserBalanceCoupons() {
        return this.userBalanceCoupons;
    }
    
    public void setUserBalanceCoupons(Set<UserBalanceCoupon> userBalanceCoupons) {
        this.userBalanceCoupons = userBalanceCoupons;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="coupons")
    public Set<UserReserveCoupon> getUserReserveCoupons() {
        return this.userReserveCoupons;
    }
    
    public void setUserReserveCoupons(Set<UserReserveCoupon> userReserveCoupons) {
        this.userReserveCoupons = userReserveCoupons;
    }




}

