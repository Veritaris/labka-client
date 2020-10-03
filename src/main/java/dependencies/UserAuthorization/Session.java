package dependencies.UserAuthorization;

public class Session {
    private User user;

    public Session() {
        this.user = new User("", "");
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void removeUser() {
        this.user = null;
    }

    public User getUser() {
        return this.user;
    }
}
