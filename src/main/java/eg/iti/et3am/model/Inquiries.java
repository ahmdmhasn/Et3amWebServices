package eg.iti.et3am.model;
// Generated Jul 4, 2019 9:55:21 PM by Hibernate Tools 4.3.1

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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Inquiries generated by hbm2java
 */
@Entity
@Table(name = "inquiries",
        catalog = "heroku_24f192cc0bbf6af"
)
public class Inquiries implements java.io.Serializable {

    public static final int PENDING = 0;
    public static final int RESPONDED = 1;

    private Integer idInquiries;
    private Users users;
    private Integer status;
    private Date creationDate;
    private String message;
    private String image;

    public Inquiries() {
    }

    public Inquiries(Users users, Date creationDate) {
        this.users = users;
        this.creationDate = creationDate;
    }

    public Inquiries(Users users, Integer status, Date creationDate, String image, String message) {
        this.users = users;
        this.status = status;
        this.creationDate = creationDate;
        this.image = image;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_inquiries", unique = true, nullable = false)
    public Integer getIdInquiries() {
        return this.idInquiries;
    }

    public void setIdInquiries(Integer idInquiries) {
        this.idInquiries = idInquiries;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Column(name = "status")
    @ColumnDefault("0")
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, length = 19)
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "image", length = 128)
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "message", length = 1024)
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
