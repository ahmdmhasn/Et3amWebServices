/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.service.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import eg.iti.et3am.utils.CloudinaryConstants;
import eg.iti.et3am.model.ImageFile;
import eg.iti.et3am.service.interfaces.PhotoService;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Nesma
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    private Cloudinary getCloudinaryClient() {
        return new Cloudinary(ObjectUtils.asMap("cloud_name", CloudinaryConstants.CLOUD_NAME,
                "api_key", CloudinaryConstants.API_KEY,
                "api_secret", CloudinaryConstants.API_SECRET,
                "secure", true));
    }

    @Override
    public Map<String, Object> uploadToCloudinary(ImageFile imageSent) {

        Cloudinary cloudinary = getCloudinaryClient();
        Map<String, Object> cloudinaryUrl = null;
        Map params = ObjectUtils.asMap("public_id", "images/" + imageSent.getImageName());
        File convFile;
        try {
            // convert from multipart file to File 
            convFile = multipartToFile(imageSent.getFile());
            @SuppressWarnings("unchecked")

            Map<String, Object> result = (Map<String, Object>) cloudinary.uploader().upload(convFile, params);
            System.out.println(result.get("secure_url"));
            cloudinaryUrl = result;

        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {

            System.out.println("Could not upload file to Cloundinary from MultipartFile " + imageSent.getImageName());
            ex.printStackTrace();

        }

        return cloudinaryUrl;
    }

    private File multipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {

        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartFile;
        FileItem fileItem = commonsMultipartFile.getFileItem();
        DiskFileItem diskFileItem = (DiskFileItem) fileItem;
        String absPath = diskFileItem.getStoreLocation().getAbsolutePath();
        File file = new File(absPath);
        if (!file.exists()) {
            file.createNewFile();
            multipartFile.transferTo(file);
        }

        return file;
    }

}
