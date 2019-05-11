/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.model;

import javax.persistence.Column;    
import javax.persistence.Entity;    
import javax.persistence.Id;    
import javax.persistence.Table;    
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity    
@Table(name = "users")    
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})    
public class User {
    
 //    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")    
    private Long id;
    
    @Column(name = "username") 
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "isReceiver")
    private int isReceiver;

    public User() {
    }

    public User(Long id, String username, String password, String email, int isReceiver) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isReceiver = isReceiver;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsReceiver() {
        return isReceiver;
    }

    public void setIsReceiver(int isReceiver) {
        this.isReceiver = isReceiver;
    }
}
