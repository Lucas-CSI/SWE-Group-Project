package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT u FROM Room u WHERE u.theme = ?1")
    Optional<Room> findByTheme(String theme);

    @Query("SELECT u FROM Room u WHERE u.oceanView = ?1 AND u.theme = ?2 AND u.isBooked = ?3")
    Optional<Room> findByViewByThemeAndBooked(boolean oceanView, Room.Themes theme, boolean booked);
}