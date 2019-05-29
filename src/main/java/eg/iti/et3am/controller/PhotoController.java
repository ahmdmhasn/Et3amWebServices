/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Status;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nesma
 */
@RestController
@RequestMapping("/image")
public class PhotoController {
    
    
//    @Autowired
//	ServletContext context;
    
    @RequestMapping(value = "/fileupload", headers=("content-type=multipart/*"), method = RequestMethod.POST)
 public Status upload(@RequestParam("file") MultipartFile inputFile) {
//  FileInfo fileInfo = new FileInfo();
//  HttpHeaders headers = new HttpHeaders();
  if (!inputFile.isEmpty()) {
//   try {
    String originalFilename = inputFile.getOriginalFilename();
  //  File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded")+  File.separator + originalFilename);
//      try {
//          inputFile.transferTo(destinationFile);
//      } catch (IOException ex) {
//          Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
//      } catch (IllegalStateException ex) {
//          Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
      //}
//    fileInfo.setFileName(destinationFile.getPath());
//    fileInfo.setFileSize(inputFile.getSize());
       System.out.println("File Uploaded Successfully - " + originalFilename);
       
       return new Status(1,"Uploaded successfully");
       
  }
  return new Status(0,"Failed While Uploading");
       
//    return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
//   } catch (Exception e) {    
//    return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
//   }
//  }else{
//   return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
//  }
 }
    
    
}
