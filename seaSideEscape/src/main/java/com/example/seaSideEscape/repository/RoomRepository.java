package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Room} entities.
 * Provides methods for CRUD operations and custom queries for room availability, booking status, and more.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Finds a room by its theme.
     *
     * @param theme the theme of the room
     * @return an Optional containing the room if found, or empty otherwise
     */
    @Query("SELECT u FROM Room u WHERE u.theme = ?1")
    Optional<Room> findByTheme(String theme);

    /**
     * Finds specific available rooms matching the given criteria within a specified date range.
     *
     * @param isSmokingAllowed whether smoking is allowed in the room
     * @param qualityLevel     the quality level of the room
     * @param bedType          the bed type of the room
     * @param oceanView        whether the room has an ocean view
     * @param theme            the theme of the room
     * @param checkInDate      the check-in date for availability
     * @param checkOutDate     the check-out date for availability
     * @return a list of rooms matching the criteria that are available in the date range
     */
    @Query("SELECT u FROM Room u WHERE u.isSmokingAllowed = ?1 AND u.qualityLevel = ?2 AND u.bedType = ?3 AND u.oceanView = ?4 AND u.theme = ?5 " +
            "AND u NOT IN (SELECT rm2 FROM Room rm2 JOIN Booking b ON b.room = rm2 JOIN Reservation res ON b.reservation = res WHERE res.checkInDate <= ?7 AND ?6 <= res.checkOutDate)")
    List<Room> findSpecificAvailableRooms(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme, LocalDate checkInDate, LocalDate checkOutDate);

    /**
     * Checks if a room is booked on a specific date.
     *
     * @param room        the room to check
     * @param currentDate the date to check for booking status
     * @return true if the room is booked on the specified date, false otherwise
     */
    @Query("SELECT CASE WHEN (count(room) > 0) then true else false end FROM Room room JOIN Booking b ON b.room = room " +
            "JOIN Reservation res ON res = b.reservation WHERE room = ?1 AND res.checkOutDate >= ?2 AND res.checkInDate <= ?2")
    boolean isRoomBooked(Room room, LocalDate currentDate);

    /**
     * Retrieves a list of rooms available in a specific date range.
     *
     * @param checkInDate  the start date of the availability range
     * @param checkOutDate the end date of the availability range
     * @return a list of rooms that are available in the specified date range
     */
    @Query("SELECT rm FROM Room rm WHERE rm NOT IN (SELECT rm2 FROM Room rm2 JOIN Booking b " +
            "ON b.room = rm2 JOIN Reservation res ON b.reservation = res WHERE res.checkInDate <= ?2 AND ?1 <= res.checkOutDate)")
    List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate);

    /**
     * Retrieves rooms in the cart (unbooked reservation) for a specific account.
     *
     * @param account the account whose cart is to be retrieved
     * @return a list of rooms in the account's cart
     */
    @Query("SELECT rm FROM Room rm JOIN Booking b ON rm = b.room WHERE :#{#account.unbookedReservation} = b.reservation")
    List<Room> getCart(@Param("account") Account account);

    /**
     * Finds a room by its room number.
     *
     * @param roomNumber the room number to search for
     * @return an Optional containing the room if found, or empty otherwise
     */
    @Query("Select rm FROM Room rm WHERE rm.roomNumber = ?1")
    Optional<Room> findRoomByNumber(String roomNumber);

    /**
     * Retrieves rooms associated with a specific reservation.
     *
     * @param reservation the reservation for which to retrieve rooms
     * @return a list of rooms associated with the reservation
     */
    @Query("SELECT rm FROM Room rm JOIN Booking b ON b.room = rm JOIN Reservation res ON b.reservation = ?1")
    List<Room> getRoomsInReservation(Reservation reservation);
}
