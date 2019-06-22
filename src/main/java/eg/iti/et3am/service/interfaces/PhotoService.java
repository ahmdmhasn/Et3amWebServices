/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.model.ImageFile;
import java.util.Map;

/**
 *
 * @author Nesma
 */

public interface PhotoService {
        // to upload image to cloudinary 
  public Map<String,Object>  uploadToCloudinary(ImageFile imageSent);
  
  // to update the table with the sting url of the image
  //public boolean updateDB(String tableName,int id, String imageUrl);
  
}
