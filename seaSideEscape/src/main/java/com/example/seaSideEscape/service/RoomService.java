package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.BookingRepository;
import com.example.seaSideEscape.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing rooms in the SeaSide Escape application.
 * Provides functionalities such as retrieving available rooms, managing room reservations,
 * and performing room-related database operations.
 */
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingService bookingService;
    private final AccountService accountService;
    private final BookingRepository bookingRepository;

    /**
     * Constructs a RoomService with the required dependencies.
     *
     * @param roomRepository    the repository for managing room data
     * @param bookingService    the service for managing bookings
     * @param accountService    the service for managing accounts
     * @param bookingRepository the repository for managing bookings
     */
    public RoomService(RoomRepository roomRepository, BookingService bookingService, AccountService accountService,
                       BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
        this.accountService = accountService;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Checks if a room exists by its ID.
     *
     * @param roomId the ID of the room
     * @return true if the room exists, false otherwise
     */
    public boolean roomExists(Long roomId) {
        return roomRepository.existsById(roomId);
    }

    /**
     * Retrieves a list of rooms available within the specified date range.
     *
     * @param checkInDate  the start date of the availability range
     * @param checkOutDate the end date of the availability range
     * @return a list of available rooms
     */
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomRepository.getAvailableRooms(checkInDate, checkOutDate);
    }

    /**
     * Retrieves a list of rooms available for the unbooked reservation of a user.
     *
     * @param username the username of the account
     * @return a list of available rooms
     */
    public List<Room> getAvailableRooms(String username) {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Account accountObject = account.orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return roomRepository.getAvailableRooms(accountObject.getUnbookedReservation().getCheckInDate(),
                accountObject.getUnbookedReservation().getCheckOutDate());
    }

    /**
     * Finds a specific room matching given criteria within a date range.
     *
     * @param room         the room criteria to match
     * @param checkInDate  the start date of the availability range
     * @param checkOutDate the end date of the availability range
     * @return the first matching room, or null if none found
     */
    public Room findSpecificAvailableRoom(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Room> rooms = roomRepository.findSpecificAvailableRooms(room.isSmokingAllowed(),
                room.getQualityLevel(), room.getBedType(), room.isOceanView(), room.getTheme(), checkInDate, checkOutDate);
        return (rooms == null || rooms.isEmpty()) ? null : rooms.get(0);
    }

    /**
     * Sets up the database with randomly generated rooms.
     */
    public void setupDB() {
        for (int i = 0; i < 100; ++i) {
            Room room = new Room();
            room.setQualityLevel(Room.QualityLevel.values()[(int) (Math.random() * 4)]);
            room.setSmokingAllowed((int) (Math.random() * 5) == 0);
            room.setBedType(room.getQualityLevel().ordinal() <= 1 ? "Queen" : "King");
            room.setRoomNumber(String.valueOf(i + 101));
            room.setTheme(Room.Themes.values()[(int) (Math.random() * 3)]);
            room.setOceanView((int) (Math.random() * 2) == 0);
            room.setMaxRate(calculateRoomRate(room));
            roomRepository.save(room);
        }
    }

    /**
     * Calculates the rate for a room based on its attributes.
     *
     * @param room the room for which to calculate the rate
     * @return the calculated room rate
     */
    private double calculateRoomRate(Room room) {
        double baseRate = 100.0;

        switch (room.getQualityLevel()) {
            case Executive -> baseRate *= 1.5;
            case Business -> baseRate *= 1.3;
            case Comfort -> baseRate *= 1.1;
            case Economy -> baseRate *= 1.0;
        }

        if (room.isOceanView()) {
            baseRate += 20.0;
        }
        if (room.isSmokingAllowed()) {
            baseRate += 10.0;
        }

        BigDecimal roundedRate = BigDecimal.valueOf(Math.max(baseRate, 100.0)).setScale(2, RoundingMode.HALF_UP);
        return roundedRate.doubleValue();
    }

    /**
     * Calculates the total cost of rooms in a user's cart.
     *
     * @param username the username of the account
     * @return the total cost of rooms in the cart
     */
    public double calculateTotalCartCost(String username) {
        List<Room> cartRooms = getRoomsInCart(username);
        return (cartRooms == null || cartRooms.isEmpty()) ? 0.0 :
                cartRooms.stream().mapToDouble(Room::getMaxRate).sum();
    }

    /**
     * Clears all rooms from a user's cart.
     *
     * @param username the username of the account
     */
    @Transactional
    public void clearCart(String username) {
        Optional<Account> account = accountService.findAccountByUsername(username);
        account.ifPresent(accountObject -> {
            List<Room> cartRooms = getRoomsInCart(username);
            if (cartRooms != null) {
                cartRooms.forEach(room -> bookingService.removeRoomFromCart(accountObject, room));
            }
        });
    }

    /**
     * Retrieves a room by its ID.
     *
     * @param roomId the ID of the room
     * @return the room, or null if not found
     */
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    /**
     * Removes a room from a user's cart.
     *
     * @param account the account associated with the cart
     * @param room    the room to remove
     * @return true if the room was removed, false otherwise
     */
    public boolean removeRoomFromCart(Account account, Room room) {
        Optional<Booking> booking = bookingRepository.findByReservation_AccountAndRoom(account, room);
        if (booking.isPresent()) {
            bookingRepository.delete(booking.get());
            return true;
        }
        return false;
    }

    /**
     * Retrieves rooms in a user's cart.
     *
     * @param username the username of the account
     * @return a list of rooms in the user's cart
     */
    public List<Room> getRoomsInCart(String username) {
        Optional<Account> account = accountService.findAccountByUsername(username);
        return account.map(roomRepository::getCart).orElse(null);
    }

    /**
     * Saves a room to the database.
     *
     * @param room the room to save
     */
    public void addRoomToDB(Room room) {
        roomRepository.save(room);
    }

    /**
     * Retrieves rooms associated with a specific reservation.
     *
     * @param res the reservation
     * @return a list of rooms in the reservation
     */
    public List<Room> getRoomsInReservation(Reservation res) {
        return roomRepository.getRoomsInReservation(res);
    }

    /**
     * Retrieves information about a room by its room number.
     *
     * @param roomNumber the room number
     * @return an Optional containing the room if found, or empty otherwise
     */
    public Optional<Room> getRoomInfo(String roomNumber) {
        return roomRepository.findRoomByNumber(roomNumber);
    }

    /**
     * Checks if a room is booked on a specific date.
     *
     * @param room the room to check
     * @param date the date to check
     * @return true if the room is booked, false otherwise
     */
    public boolean isRoomBooked(Room room, LocalDate date) {
        return roomRepository.isRoomBooked(room, date);
    }

    /**
     * Saves or updates a room in the database.
     *
     * @param room the room to save or update
     * @return the saved or updated room
     */
    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
