package com.example.twitter.service;


import com.example.twitter.entity.User;
import com.example.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String login(String email, String password) {
        User userOptional = userRepository.findByEmail(email);
        if (userOptional!=null) {
            User user = userOptional;
            if (user.getPassword().equals(password)) {
                return "Login Successful";
            } else {
                return "Username/Password Incorrect";
            }
        } else {
            return "User does not exist";
        }
    }

    public String createUser(String email, String name, String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser!=null) {
            return "Forbidden, Account already exists";
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPassword(password);
            userRepository.save(newUser);
            return "Account Creation Successful";
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer id) {
        return userRepository.findByUserID(id);
    }



}
