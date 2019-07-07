/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.model.Admins;
import java.util.List;

/**
 *
 * @author Nesma
 */
public interface AdminDao {

    /*---Login method using email & password---*/
    Admins login(String email, String password) throws Exception;

    /* Summary methods */
    public long getTodayCount(String table, String date) throws Exception;
    
    public long getCountByDays(String table, String date, int days) throws Exception;
    
    public double getValueByDays(String table, String date, String value, int days) throws Exception;
    
    public List getDatailedCountInDays(String table, String date, int days) throws Exception;
        
}
