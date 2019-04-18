package model;

public class User {

    private String name, username, password, address, email, user_type;

    public User(String uName, String pwrd) {
        this.username = uName;
        this.password = pwrd;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
