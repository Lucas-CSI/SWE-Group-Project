package com.example.seaSideEscape.validator;
import com.example.seaSideEscape.model.Reservation;

import java.time.LocalDate;
import java.util.HashMap;

public class ReservationValidator implements Validator{
    HashMap<String, String> invalidItems = new HashMap<String, String>();

    public ReservationValidator(Reservation reservation){
        validateDate(reservation.getStartDate(), "Start Date");
        validateDate(reservation.getEndDate(), "End Date");
    }

    public HashMap<String, String> getInvalidItems() {
        return invalidItems;
    }

    public boolean isValid() {
        return invalidItems.isEmpty();
    }

    public void validateDate(LocalDate date, String dateType){
        if(!date.isAfter(LocalDate.now()))
            invalidItems.put(dateType, "Invalid date");
    }

    public void validateDateRange(LocalDate startDate, LocalDate endDate){
        if(endDate.isAfter(startDate))
            invalidItems.put("Date Range", "Invalid date range");
    }
}
