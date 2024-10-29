package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean roomExists(Long roomId){
        return roomRepository.existsById(roomId);
    }

    public List<Room> getRoomsBySmokingAllowedByQualityLevelAndBedTypeAndViewAndTheme(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme){
        return roomRepository.findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndTheme(isSmokingAllowed, qualityLevel, bedType, oceanView, theme);
    }
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }
}
