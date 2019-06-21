/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.dao.interfaces.AdminDao;
import eg.iti.et3am.model.Admins;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    
    
    @Autowired
    AdminDao adminDao;
    
    
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHome(Model model) {
        return "admin";
    }
    @RequestMapping(value = "/validate/login", method=RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Map<String, Object> response = new HashMap<>();
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                Admins admin = adminDao.login(email, password);
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
    
}
