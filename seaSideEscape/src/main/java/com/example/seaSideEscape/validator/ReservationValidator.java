package com.example.seaSideEscape.validator;
import com.example.seaSideEscape.model.Reservation;

import java.time.LocalDate;
import java.util.HashMap;

public class ReservationValidator implements Validator{
    HashMap<String, String> invalidItems = new HashMap<String, String>();

    public ReservationValidator(Reservation reservation){
        validateDate(reservation.getCheckInDate(), "Start Date");
        validateDate(reservation.getCheckOutDate(), "End Date");
        validateDateRange(reservation.getCheckInDate(), reservation.getCheckOutDate());
    }

    public HashMap<String, String> getInvalidItems() {
        return invalidItems;
    }

    public boolean isValid() {
        return invalidItems.isEmpty();
    }

    private void validateDate(LocalDate date, String dateType){
        if(!date.isAfter(LocalDate.now()))
            invalidItems.put(dateType, "Invalid date");
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate){
        if(endDate.isAfter(startDate))
            invalidItems.put("Date Range", "Invalid date range");
    }
}
