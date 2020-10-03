package dependencies.UserAuthorization;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

@SuppressWarnings("FieldCanBeLocal")
public class User implements Serializable {
    private String username;
    private String password;
    private String hashedPassword;
    private String token;
    private boolean isLoggedIn = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.hashedPassword = DigestUtils.md5Hex(this.password);
        this.token = DigestUtils.md5Hex(String.format("%ssalt%s", this.username, this.password));
    }

    private User(String username, String hashedPassword, boolean isHashed) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean equals(User userObject) {
        return (this.username.equals(userObject.getUsername()) && this.hashedPassword.equals(userObject.getHashedPassword()));
    }

    public static User getDBUser(String username, String hashedPassword) {
        return new User(username, hashedPassword, true);
    }

    @Override
    public String toString() {
        return String.format("User <%s>", this.username);
    }

    public String getToken() {
        return this.token;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }
}
