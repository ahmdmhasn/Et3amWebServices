package eg.iti.et3am.model;
// Generated May 16, 2019 11:54:55 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * UserReserveCoupon generated by hbm2java
 */
@Entity
@Table(name = "user_reserve_coupon", catalog = "heroku_24f192cc0bbf6af"
)
public class UserReserveCoupon implements java.io.Serializable {

    private Integer reservedCouponId;
    private Coupons coupons;
    private Users users;
    private Date reservationDate;
    private int status;
    private Set<UserUsedCoupon> userUsedCoupons = new HashSet<>(0);

    public UserReserveCoupon() {
    }

    public UserReserveCoupon(Coupons coupons, Users users, Date reservationDate, int status) {
        this.coupons = coupons;
        this.users = users;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public UserReserveCoupon(Coupons coupons, Users users, Date reservationDate, int status, Set<UserUsedCoupon> userUsedCoupons) {
        this.coupons = coupons;
        this.users = users;
        this.reservationDate = reservationDate;
        this.status = status;
        this.userUsedCoupons = userUsedCoupons;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "reserved_coupon_id", unique = true, nullable = false)
    public Integer getReservedCouponId() {
        return this.reservedCouponId;
    }

    public void setReservedCouponId(Integer reservedCouponId) {
        this.reservedCouponId = reservedCouponId;
    }

    //    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupons_coupon_id", nullable = false)
    public Coupons getCoupons() {
        return this.coupons;
    }

    public void setCoupons(Coupons coupons) {
        this.coupons = coupons;
    }

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserver_id", nullable = false)
    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reservation_date", nullable = false, length = 26)
    public Date getReservationDate() {
        return this.reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userReserveCoupon")
    public Set<UserUsedCoupon> getUserUsedCoupons() {
        return this.userUsedCoupons;
    }

    public void setUserUsedCoupons(Set<UserUsedCoupon> userUsedCoupons) {
        this.userUsedCoupons = userUsedCoupons;
    }

}
