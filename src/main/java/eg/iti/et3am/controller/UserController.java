/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Status;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /*---Add new user---*/
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Status addEntity(@RequestBody Users user) {
        try {
            String id = userService.addEntity(user);
            return new Status(1, user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Status(0, ex.getMessage());
        }
    }

    /*---Get user by id---*/
    @RequestMapping(value = "/u/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Users getEntityById(@PathVariable("id") long id) {
        Users user = null;
        try {
            user = userService.getEntityById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    /*---get all user---*/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Users> list() {
        List<Users> userList = null;
        try {
            userList = userService.getEntityList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    // Not supported
    /*---Update a user by id---*/
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Status update(@PathVariable("id") long id, @RequestBody Users user) {
        try {
            userService.updateEntity(id, user);
            return new Status(1, "User updated Successfully!");
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }

    // Not supported
    /*---Delete a user by id---*/
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Status delete(@PathVariable("id") long id) {
        try {
            userService.deleteEntity(id);
            return new Status(1, "User deleted Successfully !");
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }

    /*---Check if the same email exists---*/
    @RequestMapping(value = "/validate/email/{email}", method = RequestMethod.GET)
    public @ResponseBody
    Status isEmailValid(@PathVariable("email") String email) {
        try {
            if (userService.isEmailValid(email)) {
                return new Status(1, "Email is valid!");
            } else {
                return new Status(0, "Email is not valid!");
            }
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }

    /*---Check if the same username exists---*/
    @RequestMapping(value = "/validate/username/{username}", method = RequestMethod.GET)
    public @ResponseBody
    Status isUsernameValid(@PathVariable("username") String username) {
        try {
            if (userService.isUsernameValid(username)) {
                return new Status(1, "Username is valid!");
            } else {
                return new Status(0, "Username is not valid!");
            }
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }

    /*---Check if the same username exists---*/
    @RequestMapping(value = "/validate/login", method = RequestMethod.GET)
    public @ResponseBody
    Status login(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                Users user = userService.login(email, password);
                if (user != null) {
                    return new Status(1, user);
                } else {
                    return new Status(0, "User doesn't exist!");
                }
            } catch (Exception e) {
                return new Status(0, e.toString());
            }
        } else {
            return new Status(0, "Email or password must not be empty");
        }
    }
}
