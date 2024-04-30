package com.example.twitter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;
    @JsonIgnore
    private String email;
    private String name;
    @JsonIgnore
    private String password;

    public Integer getUserId() {
        return userID;
    }

    public void setUserId(Integer userId) {
        this.userID = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public class UserDisplayFP {
        private Integer userID;
        private String name;

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Getters and setters
    }

    public UserDisplayFP toUserDisplay() {
        UserDisplayFP userDisplay = new UserDisplayFP();
        userDisplay.setUserID(this.userID);
        userDisplay.setName(this.name);
        return userDisplay;
    }

}
