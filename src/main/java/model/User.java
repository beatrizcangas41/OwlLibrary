package model;

// import javax.management.relation.Role;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))

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

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
}
