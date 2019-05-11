/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Status;
import eg.iti.et3am.model.User;
import eg.iti.et3am.service.UserService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /*---Add new user---*/
    @RequestMapping(value = "/user_add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Status addEntity(@RequestBody User user) {
        try {
            long id = userService.addEntity(user);
            return new Status(1, "User " + id + " added Successfully!");
        } catch (Exception ex) {
            return new Status(0, ex.getMessage());
        }
    }

    /*---Get user by id---*/
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public @ResponseBody
    User getEntityById(@PathVariable("id") long id) {
        User user = null;
        try {
            user = userService.getEntityById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    /*---get all users---*/
    @RequestMapping(value = "/user_list", method = RequestMethod.GET)
    public @ResponseBody
    List<User> list() {
        List<User> userList = null;
        try {
            userList = userService.getEntityList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    /*---Update a user by id---*/
    @RequestMapping(value = "/user_update/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Status update(@PathVariable("id") long id, @RequestBody User user) {
        try {
            userService.updateEntity(id, user);
            return new Status(1, "User updated Successfully!");
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }

    /*---Delete a book by id---*/
    @RequestMapping(value = "/user_delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Status delete(@PathVariable("id") long id) {
        try {
            userService.deleteEntity(id);
            return new Status(1, "User deleted Successfully !");
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }
}
