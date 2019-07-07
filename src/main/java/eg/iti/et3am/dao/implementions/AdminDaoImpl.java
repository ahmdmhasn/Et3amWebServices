
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.AdminDao;
import eg.iti.et3am.model.Admins;
import eg.iti.et3am.utils.EntityCopier;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nesma
 */
@Repository
public class AdminDaoImpl implements AdminDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    Session session = null;
    Transaction tx = null;

    @Override
    public Admins login(String email, String password) throws Exception {
        session = sessionFactory.getCurrentSession();
//        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(Admins.class);
        criteria.add(Restrictions.eq("adminEmail", email));
        criteria.add(Restrictions.eq("adminPassword", password));

        Admins admin = (Admins) criteria.uniqueResult();
        return EntityCopier.getAdmin(admin);

    }

    // Summary methods
    @Override
    public long getTodayCount(String table, String date) throws Exception {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from " + table + " where " + date + " > CURDATE()");
//        query.setString("table", table);
//        query.setString("date", date);
        return (long) query.uniqueResult();
    }

    @Override
    public long getCountByDays(String table, String date, int days) throws Exception {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from " + table + " where " + date + " > :date");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24 * days);
        Date mDate = cal.getTime();
        query.setParameter("date", mDate);
        try {
            return (long) query.uniqueResult();
        } catch (NullPointerException | NoResultException e) {
            return 0;
        }
    }

    @Override
    public double getValueByDays(String table, String date, String value, int days) throws Exception {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select SUM(" + value + ") from " + table + " where " + date + " > :date");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24 * days);
        Date mDate = cal.getTime();
        query.setParameter("date", mDate);
        
        try {
            return (double) query.uniqueResult();
        } catch (NullPointerException | NoResultException e) {
            return 0;
        }
    }

    @Override
    public List getDatailedCountInDays(String table, String date, int days) throws Exception {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT Date(" + date + "), COUNT(*)"
                + " FROM " + table + " WHERE " + date + " > :date"
                + " GROUP BY DAY(" + date + ")"
                + " ORDER BY " + date + " DESC");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24 * days);
        Date mDate = cal.getTime();
        query.setParameter("date", mDate);
        return query.list();
    }

}
