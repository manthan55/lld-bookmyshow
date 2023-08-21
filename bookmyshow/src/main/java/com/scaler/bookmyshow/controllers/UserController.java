package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.dtos.UserSignUpRequestDto;
import com.scaler.bookmyshow.dtos.UserSignUpResponseDto;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    public UserSignUpResponseDto SignUp(UserSignUpRequestDto request){
        UserSignUpResponseDto response = new UserSignUpResponseDto();

        try{
            User user = userService.SignUp(request.getName(), request.getEmail(), request.getPassword());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setUserId(user.getId());
        }
        catch(Exception ex){
            System.out.println("User Already Exists");
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }
}
