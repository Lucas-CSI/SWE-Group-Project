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

    @Query("SELECT u FROM Reservation u WHERE u.room.qualityLevel = ?1 AND u.room.bedType = ?2 AND u.room.oceanView = ?3 AND u.room.theme = ?4 AND u.endDate BETWEEN ?5 AND ?6")
    List<Reservation> findByQualityLevelAndBedTypeAndViewAndThemeBetweenCheckInDateAndCheckOutDate(Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme, LocalDate startDate, LocalDate endDate);

}

