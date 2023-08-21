package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User SignUp(String name, String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        User userSaved = userRepository.save(user);

        return userSaved;
    }
}
