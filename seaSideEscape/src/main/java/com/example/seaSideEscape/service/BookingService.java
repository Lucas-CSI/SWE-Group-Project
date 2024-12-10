package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getBookedRoomsInDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        return bookingRepository.getBookingByCheckInDateCheckOutDate(checkInDate, checkOutDate);
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
}
