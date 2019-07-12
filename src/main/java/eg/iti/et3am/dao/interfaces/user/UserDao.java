package eg.iti.et3am.dao.interfaces.user;

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
        
    /*---Get all user---*/
    List<Users> getEntityList() throws Exception;
    
    List<Users> getEntityListToBeVerified () throws Exception;
    
    /*---Update a user details by id---*/
    void updateDetailsEntity(UserDetails ud, String id) throws Exception;
    
    /*---Delete a user by id---*/
    boolean deleteEntity(long id) throws Exception;
    
    /*---Check if the same email exists---*/
    boolean isEmailValid(String email) throws Exception;
    
    /*---Check if the same username exists---*/
    boolean isUsernameValid(String username) throws Exception;
    
    /*---Login method using email & password---*/
    Users login(String email, String password) throws Exception;

    public void updateEntity(Users user);

    boolean verifyUser(String userID,int verifiedID) throws Exception;

    /* Set user verification to 2 */
    public int updateUserVerification(String id, int newValue) throws Exception;

    public boolean requestPasswordReset(String email);
}
