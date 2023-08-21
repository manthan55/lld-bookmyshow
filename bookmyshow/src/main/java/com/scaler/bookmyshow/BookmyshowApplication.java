package com.scaler.bookmyshow;

import com.scaler.bookmyshow.controllers.UserController;
import com.scaler.bookmyshow.dtos.UserSignUpRequestDto;
import com.scaler.bookmyshow.dtos.UserSignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookmyshowApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(BookmyshowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserSignUpRequestDto request = new UserSignUpRequestDto();
		request.setEmail("manthan@mail.com");
		request.setName("Manthan");
		request.setPassword("password");
		UserSignUpResponseDto response = userController.SignUp(request);
		System.out.println(response.getResponseStatus());
		System.out.println(response.getUserId());
	}
}
