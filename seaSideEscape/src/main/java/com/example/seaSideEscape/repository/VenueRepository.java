package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByIsBooked(boolean isBooked);
}
