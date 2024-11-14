package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r JOIN r.rooms room WHERE room = ?1")
    Optional<Reservation> findByRoom(Room room);

    @Query("SELECT r FROM Reservation r JOIN r.rooms room WHERE room.isSmokingAllowed = ?1 AND room.qualityLevel = ?2 AND room.bedType = ?3 AND room.oceanView = ?4 AND room.theme = ?5 AND r.endDate BETWEEN ?6 AND ?7")
    List<Reservation> findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndThemeBetweenCheckInDateAndCheckOutDate(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme, LocalDate startDate, LocalDate endDate);
}

