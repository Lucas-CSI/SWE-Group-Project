package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing bookings.
 * Provides methods for handling booking operations such as retrieving bookings, saving, and deleting bookings.
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    /**
     * Constructs a new BookingService with the specified BookingRepository.
     *
     * @param bookingRepository the repository for managing bookings
     */
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Retrieves a list of bookings for rooms within a specified date range.
     *
     * @param checkInDate  the start date of the booking range
     * @param checkOutDate the end date of the booking range
     * @return a list of bookings that fall within the specified date range
     */
    public List<Booking> getBookedRoomsInDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        return bookingRepository.getBookingByCheckInDateCheckOutDate(checkInDate, checkOutDate);
    }

    /**
     * Deletes the specified booking.
     *
     * @param booking the booking to delete
     */
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    /**
     * Saves or updates a booking.
     *
     * @param booking the booking to save or update
     * @return the saved or updated booking
     */
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    /**
     * Removes a room from a user's cart by deleting the associated booking.
     *
     * @param account the account associated with the booking
     * @param room    the room to remove from the cart
     * @throws IllegalArgumentException if no booking is found for the specified account and room
     */
    @Transactional
    public void removeRoomFromCart(Account account, Room room) {
        Booking booking = bookingRepository.findByReservation_AccountAndRoom(account, room)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found for this account and room"));

        bookingRepository.delete(booking);
    }
}
