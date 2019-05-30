/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.dao.implementions.PhotoDaoImpl;
import eg.iti.et3am.model.ImageFile;
import eg.iti.et3am.model.Status;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import eg.iti.et3am.dao.interfaces.PhotoDao;
import eg.iti.et3am.service.interfaces.PhotoService;

/**
 *
 * @author Nesma
 */
@RestController
@RequestMapping("/image")
public class PhotoController {

    @Autowired(required = true)
    PhotoService photoService;

    @RequestMapping(value = "/fileupload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    public Status upload(@RequestParam("file") MultipartFile inputFile,@RequestParam("id") int id) {

        if (!inputFile.isEmpty()) {

            ImageFile myImage = new ImageFile();
            myImage.setFile(inputFile);
            myImage.setImageName(inputFile.getOriginalFilename());
            // upload to cloud 
            Map<String, Object> result = photoService.uploadToCloudinary(myImage);
            System.out.println(result.get("secure_url"));
            // when image uploaded get the url 
            if (result.size() > 0) {
                myImage.setSecure_url((String) result.get("secure_url"));

                // Store At DB
              //  photoService.updateDB("Meals", id,myImage.getImageName());
                
                
                // Return Result
                return new Status(1, "Uploaded successfully");
            } else {
                return new Status(0, "Failed While Uploading");
            }
        }
        return new Status(0, "Failed While Uploading");

    }

}
