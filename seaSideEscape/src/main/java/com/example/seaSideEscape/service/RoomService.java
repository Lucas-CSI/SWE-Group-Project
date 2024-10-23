package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean isRoomBooked(Long roomId) throws Exception {
        if(roomExists(roomId)) {
            return roomRepository.findById(roomId).get().isBooked();
        }else{
            throw new Exception("Error: Room does not exist.");
        }
    }
}
