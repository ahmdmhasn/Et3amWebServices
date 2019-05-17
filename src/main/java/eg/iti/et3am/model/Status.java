/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.model;

/**
 *
 * @author A7med
 */
public class Status {

    private int code;
    private String message;
    private Users user;
    private Meals meal;

    public Status() {
    }

    public Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Status(int code, Users user) {
        this.code = code;
        this.user = user;
    }

    public Status(int code, Meals meal) {
        this.code = code;
        this.meal = meal;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
