/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.AdminDao;
import eg.iti.et3am.dao.interfaces.RestaurantDao;
import eg.iti.et3am.model.Admins;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nesma
 */
@Transactional
@Service
public class AdminServiceImpl implements AdminService{
    
@Autowired (required = true)
 AdminDao adminDao;
@Autowired (required = true)
RestaurantDao restaurantDao;


    @Override
    @Transactional
    public String addResturantAdmin(String email,String password,int restaurantId) throws Exception {
  return  restaurantDao.addResturantAdmin(email,password,restaurantId);

    }

    @Override
    @Transactional
    public Admins login(String email, String password) throws Exception {
return adminDao.login(email, password);
    }
}
