package eg.iti.et3am.model;
// Generated May 16, 2019 11:54:55 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Admins generated by hbm2java
 */
@Entity
@Table(name="admins"
    ,catalog="heroku_24f192cc0bbf6af"
    , uniqueConstraints = @UniqueConstraint(columnNames="admin_email") 
)
public class Admins  implements java.io.Serializable {


     private Integer adminId;
     private String adminName;
     private String adminEmail;
     private String adminPassword;

    public Admins() {
    }

    public Admins(String adminName, String adminEmail, String adminPassword) {
       this.adminName = adminName;
       this.adminEmail = adminEmail;
       this.adminPassword = adminPassword;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="admin_id", unique=true, nullable=false)
    public Integer getAdminId() {
        return this.adminId;
    }
    
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    
    @Column(name="admin_name", nullable=false, length=45)
    public String getAdminName() {
        return this.adminName;
    }
    
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    
    @Column(name="admin_email", unique=true, nullable=false, length=45)
    public String getAdminEmail() {
        return this.adminEmail;
    }
    
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    
    @Column(name="admin_password", nullable=false, length=45)
    public String getAdminPassword() {
        return this.adminPassword;
    }
    
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }




}


