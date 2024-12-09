package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT u FROM Room u WHERE u.theme = ?1")
    Optional<Room> findByTheme(String theme);

    @Query("SELECT u FROM Room u WHERE u.isSmokingAllowed = ?1 AND u.qualityLevel = ?2 AND u.bedType = ?3 AND u.oceanView = ?4 AND u.theme = ?5 " +
            "AND u NOT IN (SELECT rm2 FROM Room rm2 JOIN Booking b ON b.room = rm2 JOIN Reservation res ON b.reservation = res WHERE res.checkInDate <= ?7 AND ?6 <= res.checkOutDate)")
    List<Room> findSpecificAvailableRooms(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme, LocalDate checkInDate, LocalDate checkOutDate);

    @Query("SELECT rm FROM Room rm WHERE rm NOT IN (SELECT rm2 FROM Room rm2 JOIN Booking b " +
            "ON b.room = rm2 JOIN Reservation res ON b.reservation = res WHERE res.checkInDate <= ?2 AND ?1 <= res.checkOutDate)")
    List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate);

    @Query("SELECT rm FROM Room rm JOIN Booking b ON rm = b.room WHERE :#{#account.unbookedReservation} = b.reservation")
    List<Room> getCart(@Param("account") Account account);
}