/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.InquiryDao;
import eg.iti.et3am.model.Inquiries;
import eg.iti.et3am.service.interfaces.InquiryService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author A7med
 */
@Service
@Transactional
public class InquiryServiceImpl implements InquiryService {
    
    @Autowired
    private InquiryDao inquiryDao;

    @Override
    public int addEntity(Inquiries inquiry) throws Exception {
        inquiry.setCreationDate(new Date());
        inquiry.setStatus(0);
        return inquiryDao.addEntity(inquiry);
    }

    @Override
    public Inquiries getEntityById(int id) throws Exception {
        return inquiryDao.getEntityById(id);
    }

    @Override
    public List<Inquiries> getEntityList() throws Exception {
        return inquiryDao.getEntityList();
    }

    @Transactional
    @Override
    public Inquiries updateStatus(int id, int status) throws Exception {
        inquiryDao.updateStatus(id, status);
        return inquiryDao.getEntityById(id);
    }
    
}
