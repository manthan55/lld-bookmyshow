package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.BookMovieRequestDto;
import com.scaler.bookmyshow.dtos.BookMovieResponseDto;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.models.BookingStatus;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    @Autowired
    private BookingService bookingService;

    public BookMovieResponseDto bookMovie(BookMovieRequestDto request) {
        BookMovieResponseDto response = new BookMovieResponseDto();

        try{
            Booking booking = bookingService.bookMovie(request.getUserId(), request.getShowSeatIds(), request.getShowId());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setBookingId(booking.getId());
            response.setAmount(booking.getAmount());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }
}
