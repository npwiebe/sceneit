package comp3350.sceneit.data;

import comp3350.sceneit.data.exceptions.InvalidUserCredentials;
import comp3350.sceneit.data.exceptions.UserExistsException;

public interface UserManager {
    /**
     * Register a new user
     *
     * @param username The username to use
     * @param password The password to use
     * @param name The users name, Ex. Joe Smoe
     * @return A JWT of the logged in user info.
     * @throws UserExistsException if the user already exists
     */
    public String register(String username, String password, String name) throws UserExistsException;

    /**
     * Login as a user
     *
     * @param username The username to use
     * @param password The password to use
     * @return A JWT of the logged in user info.
     * @throws InvalidUserCredentials if the user could not be logged in due to invalid credentials
     */
    public String login(String username, String password) throws InvalidUserCredentials;
}
