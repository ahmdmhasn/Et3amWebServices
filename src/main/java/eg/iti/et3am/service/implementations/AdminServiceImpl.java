/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.AdminDao;
import eg.iti.et3am.dao.interfaces.CouponDao;
import eg.iti.et3am.dao.interfaces.RestaurantDao;
import eg.iti.et3am.model.Admins;
import eg.iti.et3am.model.Coupons;
import eg.iti.et3am.model.RestaurantAdmin;
import eg.iti.et3am.service.interfaces.AdminService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nesma
 */
@Transactional
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired(required = true)
    AdminDao adminDao;

    @Autowired(required = true)
    RestaurantDao restaurantDao;

    @Autowired(required = true)
    CouponDao couponDao;

    @Override
    @Transactional
    public String addResturantAdmin(String email, String password, int restaurantId) throws Exception {
        return restaurantDao.addResturantAdmin(email, password, restaurantId);

    }

    @Override
    @Transactional
    public Admins login(String email, String password) throws Exception {
        return adminDao.login(email, password);
    }

    @Override
    @Transactional
    public Map<String, Object> getAdminSummary() throws Exception {

        Map<String, Object> result = new HashMap<>();
        
        Map<String, Object> today = new HashMap<>();
        Map<String, Object> month = new HashMap<>();
        Map<String, Object> details = new HashMap<>();

        today.put("created_coupons", adminDao.getTodayCount("Coupons", "creationDate"));
        today.put("reserved_coupons", adminDao.getTodayCount("UserReserveCoupon","reservationDate"));
        today.put("used_coupons", adminDao.getTodayCount("UserUsedCoupon","useDate"));
        today.put("registered_users", adminDao.getTodayCount("Users","creationDate"));
        today.put("created_coupons_value", adminDao.getValueByDays("Coupons", "creationDate", "couponValue", 1));
        today.put("used_coupons_value", adminDao.getValueByDays("UserUsedCoupon","useDate", "price", 1));
        
        month.put("created_coupons", adminDao.getCountByDays("Coupons", "creationDate", 30));
        month.put("reserved_coupons", adminDao.getCountByDays("UserReserveCoupon","reservationDate", 30));
        month.put("used_coupons", adminDao.getCountByDays("UserUsedCoupon","useDate", 30));
        month.put("registered_users", adminDao.getCountByDays("Users","creationDate", 30));
        month.put("created_coupons_value", adminDao.getValueByDays("Coupons", "creationDate", "couponValue", 30));
        month.put("used_coupons_value", adminDao.getValueByDays("UserUsedCoupon","useDate", "price", 30));

        details.put("Coupons", getCountInDays("Coupons", "creationDate", 30));
        details.put("UserReserveCoupon", getCountInDays("UserReserveCoupon", "reservationDate", 30));
        details.put("UserUsedCoupon", getCountInDays("UserUsedCoupon", "useDate", 30));
        details.put("Users", getCountInDays("Users", "creationDate", 30));
        
        result.put("today", today);
        result.put("month", month);
        result.put("details", details);
        return result;
    }

    private TreeMap<String, Long> getCountInDays(String table, String date, int period) throws Exception {

        List<Object[]> databaseList = adminDao.getDatailedCountInDays(table, date, period);

        HashMap<String, Long> hashMap = new HashMap<>();
        
        // Create hashmap of the past period
        for (int i = 0; i < period; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -24 * i);
            Date mDate = cal.getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            hashMap.put(fmt.format(mDate), 0L);
        }

        // Fill the existing data
        for (int i = 0; i < databaseList.size(); i++) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date mDate = (Date) databaseList.get(i)[0];
            long count = (long) databaseList.get(i)[1];
            hashMap.put(fmt.format(mDate), count);
        }

        return new TreeMap<>(hashMap);
    }
}
