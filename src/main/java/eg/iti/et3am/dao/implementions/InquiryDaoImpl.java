/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dao.implementions;

import eg.iti.et3am.dao.interfaces.InquiryDao;
import eg.iti.et3am.model.Inquiries;
import eg.iti.et3am.utils.EntityCopier;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InquiryDaoImpl implements InquiryDao {
    
    @Autowired(required = true)
    private SessionFactory sessionFactory;

    Session session = null;

    @Override
    public int addEntity(Inquiries inquiry) throws Exception {
        session = sessionFactory.getCurrentSession();
        session.save(inquiry);
        return (int) session.getIdentifier(inquiry);
    }

    @Override
    public Inquiries getEntityById(int id) throws Exception {
        session = sessionFactory.getCurrentSession();
        Inquiries inquiry = (Inquiries) session.load(Inquiries.class, id);
        return (inquiry != null) ? EntityCopier.getInquiry(inquiry) : null;
    }

    @Override
    public List<Inquiries> getEntityList() throws Exception {
        session = sessionFactory.getCurrentSession();

        List<Inquiries> tempList = new ArrayList<>();
        List<Inquiries> inquiryList = session.createCriteria(Inquiries.class).list();
        for (Inquiries inquiry : inquiryList) {
            tempList.add(EntityCopier.getInquiry(inquiry));
        }
        return tempList;
    }

    @Override
    public int updateStatus(int id, int status) throws Exception {
        session = sessionFactory.getCurrentSession();
        
        String hql = "UPDATE Inquiries set status = :status "
                + "WHERE idInquiries = :id";
        Query query = session.createQuery(hql);
        query.setParameter("status", status);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    
}
