package com.scaler.bookmyshow;

import com.scaler.bookmyshow.controllers.BookingController;
import com.scaler.bookmyshow.controllers.UserController;
import com.scaler.bookmyshow.dtos.BookMovieRequestDto;
import com.scaler.bookmyshow.dtos.BookMovieResponseDto;
import com.scaler.bookmyshow.dtos.UserSignUpRequestDto;
import com.scaler.bookmyshow.dtos.UserSignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BookmyshowApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;
	@Autowired
	private BookingController bookingController;

	public static void main(String[] args) {
		SpringApplication.run(BookmyshowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		UserSignUpRequestDto request = new UserSignUpRequestDto();
//		request.setEmail("manthan@mail.com");
//		request.setName("Manthan");
//		request.setPassword("password");
//		UserSignUpResponseDto response = userController.SignUp(request);
//		System.out.println(response.getResponseStatus());
//		System.out.println(response.getUserId());

		BookMovieRequestDto request = new BookMovieRequestDto();
		request.setUserId((long)1);
		request.setShowId((long)1);
		List<Long> showSeatIds = new ArrayList<Long>();
		showSeatIds.add((long)1);
		showSeatIds.add((long)2);
		showSeatIds.add((long)3);
		request.setShowSeatIds(showSeatIds);
		BookMovieResponseDto response = bookingController.bookMovie(request);
		System.out.println(response.getResponseStatus());
		System.out.println(response.getBookingId());
		System.out.println(response.getAmount());
	}
}
