package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.BookingRepository;
import jakarta.transaction.Transactional;
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

    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Transactional
    public void removeRoomFromCart(Account account, Room room) {
        Booking booking = bookingRepository.findByReservation_AccountAndRoom(account, room)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found for this account and room"));

        bookingRepository.delete(booking);
    }
}
