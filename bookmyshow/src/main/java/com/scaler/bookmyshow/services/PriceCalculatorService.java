package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Show;
import com.scaler.bookmyshow.models.ShowSeat;
import com.scaler.bookmyshow.models.ShowSeatType;
import com.scaler.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculatorService {
    @Autowired
    private ShowSeatTypeRepository showSeatTypeRepository;

    public int CalculatePrice(Show show, List<ShowSeat> showSeats){
        int amount = 0;

        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);

        for (ShowSeat showSeat: showSeats) {
            for (ShowSeatType showSeatType: showSeatTypes){
                if(showSeat.getSeat().getSeatType().getName().equals(showSeatType.getSeatType().getName())){
                    amount += showSeatType.getPrice();
                }
            }
        }

        return amount;
    }
}
