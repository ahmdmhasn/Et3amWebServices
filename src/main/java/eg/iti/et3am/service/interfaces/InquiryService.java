/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.model.Inquiries;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface InquiryService {
    
    /*---Add new inquiry---*/
    int addEntity(Inquiries inquiry) throws Exception;
    
    /*---Get inquiry by id---*/
    Inquiries getEntityById(int id) throws Exception;
    
    /*---Get all inquiries---*/
    List<Inquiries> getEntityList() throws Exception;
    
    /*---Update inquiry status---*/
    Inquiries updateStatus(int id, int status) throws Exception;
    
}
