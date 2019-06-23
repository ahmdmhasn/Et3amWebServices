/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.cloudinary.validator;

import eg.iti.et3am.model.ImageFile;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Nesma
 */
public class ImageFileValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return ImageFile.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ImageFile file = (ImageFile) o;
        if (file.getFile().getSize() == 0) {
            errors.rejectValue("file", "required.fileUpload");
        }
    }

}
