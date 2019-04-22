package model;

// import javax.management.relation.Role;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    private String name, username, password, address, email, user_type;

    public User(String uName, String pwrd) {
        this.username = uName;
        this.password = pwrd;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public User(String username, String name, String email, String password, String user_type) {
        this.name = name;
        this.username = name;
        this.email = email;
        this.password = password;
        this.user_type = user_type;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", user_type='" + user_type + '\'' +
                '}';
    }
}
