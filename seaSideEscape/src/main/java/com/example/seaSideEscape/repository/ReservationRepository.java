package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

   // @Query("SELECT u FROM Reservation u JOIN u.rooms rm WHERE " +
        //    "rm.isSmokingAllowed = ?1 AND rm.qualityLevel = ?2 AND rm.bedType = ?3 AND " +
      //      "rm.oceanView = ?4 AND rm.theme = ?5 AND u.endDate >= ?6 AND u.startDate <= ?7")
    //List<Reservation> findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndThemeBetweenCheckInDateAndCheckOutDate(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme, LocalDate startDate, LocalDate endDate);
    @Query("SELECT res FROM Reservation res JOIN Account acc ON acc = res.account WHERE acc.username = ?1")
    List<Reservation> findAllReservationsByUser(String username);

    @Query("SELECT res FROM Reservation res WHERE res.account = ?1 AND NOT res.paid")
    Optional<Reservation> findByAccountAndPaidFalse(Account account);
}

