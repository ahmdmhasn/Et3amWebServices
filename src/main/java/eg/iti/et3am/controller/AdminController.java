/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Admins;
import eg.iti.et3am.service.interfaces.AdminService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired(required = true)
    AdminService adminService;

    @RequestMapping(value = "/validate/login", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Map<String, Object> response = new HashMap<>();
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                Admins admin = adminService.login(email, password);
                if (admin != null) {
                    response.put("code", 1);
                    response.put("message", "Login Successfully");
                    response.put("admin", admin);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.put("code", 0);
                    response.put("message", "Admin doesn't exist");
                    response.put("admin", null);
                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } catch (Exception e) {
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

    @RequestMapping(value = "/add_restaurant_admin", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> addNewRestaurantAdmin(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("restaurant_id") int id) {
        Map<String, Object> response = new HashMap<>();
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                String idOfAdmin = adminService.addResturantAdmin(email, password, id);
                if (idOfAdmin != null) {
                    response.put("code", 1);
                    response.put("message", "admin added successfully");
                    response.put("id", idOfAdmin);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.put("code", 0);
                    response.put("message", "Admin didn't added successfully");

                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } catch (Exception e) {
                response.put("code", 0);
                response.put("message", "exception while adding  \n" + e.toString());
                e.printStackTrace();
                return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            response.put("code", 0);
            response.put("message", "Email , password or resuarant_id must not be empty.");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> summary() {

        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = adminService.getAdminSummary();
            response.put("code", 1);
            response.put("result", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("message", e.toString());
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
