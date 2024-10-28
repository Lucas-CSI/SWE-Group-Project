package com.example.seaSideEscape.inputValidation;
import com.example.seaSideEscape.model.Reservation;

import java.time.LocalDate;
import java.util.HashMap;

public class reservationValidator implements Validator{
    HashMap<String, String> invalidItems = new HashMap<String, String>();

    public reservationValidator(Reservation reservation){
        validateDate(reservation.getStartDate());
        validateDate(reservation.getEndDate());
    }

    public HashMap<String, String> getInvalidItems() {
        return invalidItems;
    }

    public boolean isValid() {
        return invalidItems.isEmpty();
    }

    public void validateDate(LocalDate date){
        if(date.isAfter(LocalDate.now()))
            invalidItems.put("date", "Invalid date");
    }
}
