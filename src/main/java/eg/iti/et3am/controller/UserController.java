package eg.iti.et3am.controller;

import eg.iti.et3am.model.Status;
import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.service.interfaces.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*---Add new user---*/
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> addEntity(@RequestBody Users user) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", 1);
            result.put("user", userService.addEntity(user));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("status", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    /*---Get user by id---*/
    @RequestMapping(value = "/u/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> getEntityById(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Users user = userService.getEntityById(id);
            result.put("status", 1);
            result.put("user", user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
            result.put("status", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
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

    @RequestMapping(value = "/list_verifed", method = RequestMethod.GET)
    public @ResponseBody
    List<Users> listOfUserToBeVerified() {
        List<Users> userList = null;
        try {
            System.out.println("ffsfsdtgdgdg");
            userList = userService.getEntityListToBeVerified();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }


    /*---Update a user by id---*/
    @RequestMapping(value = "/u/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> updateUserDetails(@PathVariable("id") String id,
            @RequestBody UserDetails userDetails) {

        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", 1);
            result.put("users", userService.updateEntity(userDetails, id));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("status", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
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
    @RequestMapping(value = "/validate/userEmail", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> isEmailValid(@RequestParam("string") String email) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userService.isEmailValid(email)) {
                response.put("code", 1);
                response.put("message", "Email is valid");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("code", 0);
                response.put("message", "Email is not valid");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("message", e.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /*---Check if the same username exists---*/
    @RequestMapping(value = "/validate/userName", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> isUsernameValid(@RequestParam("string") String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userService.isUsernameValid(username)) {
                response.put("code", 1);
                response.put("message", "Username is valid");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("code", 0);
                response.put("message", "Username is not valid");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("message", e.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /*---Check if the same username exists---*/
    @RequestMapping(value = "/validate/login", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Map<String, Object> response = new HashMap<>();
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                Users user = userService.login(email, password);
                if (user != null) {
                    response.put("code", 1);
                    response.put("message", "");
                    response.put("user", user);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.put("code", 0);
                    response.put("message", "User doesn't exist!");
                    response.put("user", null);
                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.put("code", 0);
                response.put("message", "Email/ password doesn't match  \n" + e.toString());
                return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            response.put("code", 0);
            response.put("message", "Email or password must not be empty.");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> verifyUser(@RequestParam("id") String id) {
        Map<String, Object> response = new HashMap<>();

        if (!id.isEmpty()) {

            try {
                if (userService.verifyUser(id)) {
                    response.put("code", 1);
                    response.put("message", "User Verified Successfully");
                    return new ResponseEntity<>(response, HttpStatus.OK);

                } else {
                    response.put("code", 0);
                    response.put("message", "Verifing isn't completed");
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);

                }

            } catch (Exception ex) {
                ex.printStackTrace();
                response.put("code", 0);
                response.put("message", "Error While Verifing");

                return new ResponseEntity<>(response, HttpStatus.CONFLICT);

            }

        }

        return null;
    }

}
