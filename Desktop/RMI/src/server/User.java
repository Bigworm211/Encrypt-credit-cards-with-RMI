package server;

/*
    This class contain all the user information that must be provide
    @param username - holds the username of the user
    @param password - keep the password of the user
    @param privilige - define the user type of priviliges
*/

public class User {
    //The path of directory, that contains the users information
    public static final String USER_DIR = "USERS/";

    private String username;
    private String password;
    private Priviliges privilige;
    
    //Enum type that contains the different type of priviliges
    public static enum Priviliges {
        ENCRYPT, DECRYPT, ENCRYPT_AND_DECRYPT;
    }

    public User(String username, String password, Priviliges privilige) {
        setPrivilige(privilige);
        setUsername(username);
        setPassword(password);
    }

    public User() {
        setPrivilige(null);
        setUsername("");
        setPassword("");
    }

    public Priviliges getPrivilige() {
        return privilige;
    }

    public void setPrivilige(Priviliges privilige) {
        this.privilige = privilige;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
