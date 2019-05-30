/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.model;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nesma
 */
public class ImageFile implements Serializable {

    private String imageName;
    private String secure_url;
    private MultipartFile imageFile;

    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the secure_url
     */
    public String getSecure_url() {
        return secure_url;
    }

    /**
     * @param secure_url the secure_url to set
     */
    public void setSecure_url(String secure_url) {
        this.secure_url = secure_url;
    }

    public MultipartFile getFile() {
        return imageFile;
    }

    public void setFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
