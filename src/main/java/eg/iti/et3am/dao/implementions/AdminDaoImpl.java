/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.AdminDao;
import eg.iti.et3am.model.Admins;
import eg.iti.et3am.model.Users;
import eg.iti.et3am.utils.EntityCopier;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nesma
 */
@Service
public class AdminDaoImpl implements AdminDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    Session session = null;
    Transaction tx = null;

    @Override
    public Admins login(String email, String password) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(Admins.class);
        criteria.add(Restrictions.eq("adminEmail", email));
        criteria.add(Restrictions.eq("adminPassword", password));

        Admins admin = (Admins) criteria.uniqueResult();
        return EntityCopier.getAdmin(admin);

    }

}
