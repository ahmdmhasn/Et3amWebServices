/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.ImageFile;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import eg.iti.et3am.service.interfaces.PhotoService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
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
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile inputFile) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!inputFile.isEmpty()) {

                ImageFile myImage = new ImageFile();
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                myImage.setFile(inputFile);
                myImage.setImageName(FilenameUtils.removeExtension(inputFile.getOriginalFilename()) + "_" + timeStamp);
                
                // upload to cloud 
                Map<String, Object> resultFromCloud = photoService.uploadToCloudinary(myImage);

                // when image uploaded get the url 
                if (resultFromCloud.size() > 0) {
                    myImage.setSecure_url((String) resultFromCloud.get("secure_url"));
                    response.put("code", 1);
                    response.put("message", "Uploaded Successfully");
                    response.put("image", resultFromCloud);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // return new Status(0, "Failed While Uploading");
                    response.put("code", 0);
                    response.put("message", "Error While Uploading Phototo cloudinary");
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
            }
            response.put("code", 0);
            response.put("message", "There is no file");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.put("code", 0);
            response.put("message", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }
}