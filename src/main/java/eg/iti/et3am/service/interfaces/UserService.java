package eg.iti.et3am.service.interfaces;

import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface UserService {
    
    /*---Add new user---*/
    Users addEntity(Users user) throws Exception;
    
    /*---Get user by id---*/
    Users getEntityById(String id) throws Exception;
    
    /*---Get all user---*/
    List<Users> getEntityList() throws Exception;
    
    /*---Update a user by id---*/
    Users updateEntity(UserDetails userDetails, String id) throws Exception;
    
    /*---Delete a user by id---*/
    boolean deleteEntity(long id) throws Exception;
    
    /*---Check if the same email exists---*/
    boolean isEmailValid(String email) throws Exception;
    
    /*---Check if the same username exists---*/
    boolean isUsernameValid(String username) throws Exception;
    
    /*---Login method using email & password---*/
    Users login(String email, String password) throws Exception;

    public void updateEntity(Users user);
}
