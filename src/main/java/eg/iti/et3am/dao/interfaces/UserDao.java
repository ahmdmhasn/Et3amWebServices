package eg.iti.et3am.dao.interfaces;

import eg.iti.et3am.model.UserDetails;
import eg.iti.et3am.model.Users;
import java.util.List;

/**
 *
 * @author A7med
 */
public interface UserDao {
    
    /*---Add new user---*/
    String addEntity(Users user) throws Exception;
    
    /*---Add new user details object---*/
    public int addDetailsEntity(UserDetails userDetails) throws Exception;
    
    /*---Get user by id---*/
    Users getEntityById(String id) throws Exception;
    
    /*---Get user details by id---*/
    UserDetails getDetailsEntityById(String id) throws Exception;
    
    /*---Get all user---*/
    List<Users> getEntityList() throws Exception;
    
    List<Users> getEntityListToBeVerified () throws Exception;
    
    /*---Update a user by id---*/
    Users updateEntity(Users user) throws Exception;
    
    
    /*---Delete a user by id---*/
    boolean deleteEntity(long id) throws Exception;
    
    /*---Check if the same email exists---*/
    boolean isEmailValid(String email) throws Exception;
    
    /*---Check if the same username exists---*/
    boolean isUsernameValid(String username) throws Exception;
    
    /*---Login method using email & password---*/
    Users login(String email, String password) throws Exception;
    
    boolean verifyUser(String id) throws Exception;
}
