package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.BookingRepository;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PriceCalculatorService priceCalculatorService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId,
                             List<Long> seatIds,
                             Long showId) {

        // 1. Get the user with the user id
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) throw new RuntimeException("User not present");
        User user = userOptional.get();

        // 2. Get the show with that show id
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()) throw new RuntimeException("Show not present");
        Show show = showOptional.get();

        // ----------- Start Transaction -------
        // 3. Get the seats with that seat ids
        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatIds);

        // 4. Check if the seats are available
        // 5. If no, return error
        for (ShowSeat showSeat: showSeats) {
            if(showSeat.getShowSeatStatus() == ShowSeatStatus.AVAILABLE) continue;
            if(showSeat.getShowSeatStatus() == ShowSeatStatus.BLOCKED && Duration.between(showSeat.getBlockedAt().toInstant(), new Date().toInstant()).toMinutes() <= 15) continue;
            throw new RuntimeException("Seat not available");
        }

        // 6. If yes, mark the status of the seats as locked
        // 7. Save the updated seat status in the DB
        for (ShowSeat showSeat: showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(new Date());
            showSeatRepository.save(showSeat);
        }

        // Create booking object
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(showSeats);
        booking.setUser(user);
        booking.setBookedAt(new Date());
        booking.setShow(show);
        booking.setAmount(priceCalculatorService.CalculatePrice(show, showSeats));

        // Save booking object to DB
        Booking bookingSaved = bookingRepository.save(booking);

        // --------------- END Transaction -------
        // 8. return success
        return bookingSaved;
    }
}
