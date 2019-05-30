/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

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
import eg.iti.et3am.service.interfaces.PhotoService;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile inputFile,@RequestParam("id") int id) {
                  Map<String, Object> response = new HashMap<>();

        if (!inputFile.isEmpty()) {

            ImageFile myImage = new ImageFile();
            myImage.setFile(inputFile);
            myImage.setImageName(inputFile.getOriginalFilename());
            // upload to cloud 
            Map<String, Object> resultFromCloud = photoService.uploadToCloudinary(myImage);
            System.out.println(resultFromCloud.get("secure_url"));
            // when image uploaded get the url 
            if (resultFromCloud.size() > 0) {
                myImage.setSecure_url((String) resultFromCloud.get("secure_url"));

                response.put("code", 1);
                response.put("message", "Uploaded Seccessfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
                // Store At DB
              //  photoService.updateDB("Meals", id,myImage.getImageName());
                
            } else {
               // return new Status(0, "Failed While Uploading");
               response.put("code",0);
               response.put("message", "Error While Uploading Phototo cloudinary");
                               return new ResponseEntity<>(response, HttpStatus.CONFLICT);

            }
        }
         response.put("code",0);
               response.put("message", "There is no file");
                               return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        

    }

}
