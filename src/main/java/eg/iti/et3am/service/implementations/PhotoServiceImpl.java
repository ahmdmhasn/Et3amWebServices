/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import eg.iti.et3am.dao.interfaces.PhotoDao;
import eg.iti.et3am.model.ImageFile;
import eg.iti.et3am.service.interfaces.PhotoService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nesma
 */
@Service
public class PhotoServiceImpl implements PhotoService{
     @Autowired
     private PhotoDao photoDao;

    @Override
    public Map<String, Object> uploadToCloudinary(ImageFile imageSent) {

return photoDao.uploadToCloudinary(imageSent);
    }
//
//    @Override
//    public boolean updateDB(String tableName, int id, String imageUrl) {
//        return photoDao.updateDB(tableName, id, imageUrl);
//    }

   
     
     
     
}
