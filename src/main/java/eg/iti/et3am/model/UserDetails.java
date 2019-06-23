package eg.iti.et3am.model;
// Generated May 16, 2019 11:54:55 PM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * UserDetails generated by hbm2java
 */
@Entity
@Table(name = "user_details",
         catalog = "heroku_24f192cc0bbf6af",
         uniqueConstraints = {
            @UniqueConstraint(columnNames = "mobile_number"),
            @UniqueConstraint(columnNames = "national_id")}
)
public class UserDetails implements java.io.Serializable {

    private Integer userDetailId;
    private Users users;
    private String mobileNumber;
    private String nationalId;
    private String job;
    private String nationalIdFront;
    private String nationalIdBack;
    private String profileImage;
    private Date birthdate;

    public UserDetails() {
    }

    public UserDetails(Users users, String nationalIdFront, String nationalIdBack, Date barthdate) {
        this.users = users;
        this.nationalIdFront = nationalIdFront;
        this.nationalIdBack = nationalIdBack;
        this.birthdate = barthdate;
    }

    public UserDetails(Users users, String mobileNumber, String nationalId, String job, String nationalIdFront, String nationalIdBack, String profileImage, Date barthdate) {
        this.users = users;
        this.mobileNumber = mobileNumber;
        this.nationalId = nationalId;
        this.job = job;
        this.nationalIdFront = nationalIdFront;
        this.nationalIdBack = nationalIdBack;
        this.profileImage = profileImage;
        this.birthdate = barthdate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_detail_id", unique = true, nullable = false)
    public Integer getUserDetailId() {
        return this.userDetailId;
    }

    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_user_id", nullable = false)
    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Column(name = "mobile_number", unique = true, length = 45)
    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Column(name = "national_id", unique = true, length = 45)
    public String getNationalId() {
        return this.nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @Column(name = "job", length = 45)
    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Column(name = "national_id_front", length = 45)
    public String getNationalIdFront() {
        return this.nationalIdFront;
    }

    public void setNationalIdFront(String nationalIdFront) {
        this.nationalIdFront = nationalIdFront;
    }

    @Column(name = "national_id_back", length = 45)
    public String getNationalIdBack() {
        return this.nationalIdBack;
    }

    public void setNationalIdBack(String nationalIdBack) {
        this.nationalIdBack = nationalIdBack;
    }

    @Column(name = "profile_image", length = 45)
    public String getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", length = 10)
    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date barthdate) {
        this.birthdate = barthdate;
    }

}
