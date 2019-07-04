/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.controller;

import eg.iti.et3am.model.Inquiries;
import eg.iti.et3am.service.interfaces.InquiryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    
    @Autowired
    private InquiryService inquiryService;
    
    /*---Add new user---*/
    @RequestMapping(value = "/submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> addEntity(@RequestBody Inquiries inquiry) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            result.put("status", 1);
            result.put("id", inquiryService.addEntity(inquiry));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("status", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    /*---Get inquiry by id---*/
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> getEntityById(@RequestParam("id") int id) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            Inquiries inquiry = inquiryService.getEntityById(id);
            result.put("status", 1);
            result.put("inquiry", inquiry);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("status", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }
    
    /*--- Get all inquiries---*/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Inquiries> list() {
        List<Inquiries> inquiriesList = null;
        try {
            inquiriesList = inquiryService.getEntityList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inquiriesList;
    }
    
    /* Update inquiry state */
    @RequestMapping(value = "/update/status", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> updateInquiryStatus(@RequestParam("id") int id, 
            @RequestParam("is_read") int isRead) {

        Map<String, Object> result = new HashMap<>();
        int status = (isRead == 1) ? Inquiries.RESPONDED : Inquiries.PENDING;
        
        try {
            result.put("status", 1);
            result.put("inquiry", inquiryService.updateStatus(id, status));
            result.put("message", "Successfully updated");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("status", 0);
            result.put("message", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }
    
}
