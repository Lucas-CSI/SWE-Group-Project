package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT u FROM Room u WHERE u.theme = ?1")
    Optional<Room> findByTheme(String theme);

    @Query("SELECT u FROM Room u WHERE u.isSmokingAllowed = ?1 AND u.qualityLevel = ?2 AND u.bedType = ?3 AND u.oceanView = ?4 AND u.theme = ?5")
    List<Room> findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndTheme(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme);
}