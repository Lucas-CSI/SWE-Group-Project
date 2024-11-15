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
    @Query("SELECT u FROM Reservation u WHERE u.room = ?1")
    Optional<Reservation> findByRoom(Room room);

    @Query("SELECT u FROM Reservation u WHERE u.room.isSmokingAllowed = ?1 AND u.room.qualityLevel = ?2 AND u.room.bedType = ?3 AND u.room.oceanView = ?4 AND u.room.theme = ?5 AND u.endDate BETWEEN ?6 AND ?7")
    List<Reservation> findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndThemeBetweenCheckInDateAndCheckOutDate(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme, LocalDate startDate, LocalDate endDate);
}

