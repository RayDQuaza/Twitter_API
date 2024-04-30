package com.example.twitter.controller;

import com.example.twitter.entity.MyError;
import com.example.twitter.entity.User;
import com.example.twitter.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {



    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String response = userService.login(email, password);
        MyError m=new MyError(response);
        if(response.equals("Login Successful"))
        {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(m);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {
        String email = request.getEmail();
        String name = request.getName();
        String password = request.getPassword();
        String response = userService.createUser(email, name, password);
        MyError m=new MyError(response);
        if(response.equals("Account Creation Successful"))
        {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
//        Gson gson = new Gson();
//        Map<String,String> map = new HashMap<>();
//        map.put("Error",m.getError());
//        String ans=gson.toJson(map);
        return ResponseEntity.status(HttpStatus.OK).body(m);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam Integer userID) {
        User CUser=userService.findUserById(userID);

        if(CUser!=null) {
            UserDisplay UD = new UserDisplay(CUser);
            return ResponseEntity.status(HttpStatus.OK).body(UD);
        }
        else{
            MyError m=new MyError("User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> LU=userService.getAllUsers();
        List<UserDisplay> LUD=new ArrayList<>();
        for(int i=0;i<LU.size();i++)
        {
            LUD.add(new UserDisplay(LU.get(i)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(LUD);
    }



    // Add other endpoints for user-related operations

    private static class UserLoginRequest {
        private String email;
        private String password;

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

    private static class UserSignupRequest {
        private String email;
        private String name;
        private String password;

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
    }

    class UserDisplay{
        private String name;
        private Integer userID;
        private String email;

        UserDisplay(User user) {
            this.name = user.getName();
            this.userID = user.getUserId();
            this.email = user.getEmail();
        }


        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
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
    }
}



