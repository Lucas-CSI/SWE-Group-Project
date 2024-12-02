package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final BookingService bookingService;

    @Autowired
    public RoomService(RoomRepository roomRepository, BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
    }

    public boolean roomExists(Long roomId){
        return roomRepository.existsById(roomId);
    }
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate){
        return roomRepository.getBookedRooms(checkInDate, checkOutDate);
    }

    public boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate){
        return true;
    }

    public List<Room> getRoomsBySmokingAllowedByQualityLevelAndBedTypeAndViewAndTheme(boolean isSmokingAllowed, Room.QualityLevel qualityLevel, String bedType, boolean oceanView, Room.Themes theme){
        return roomRepository.findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndTheme(isSmokingAllowed, qualityLevel, bedType, oceanView, theme);
    }
    static String[] bedTypes = {"Queen", "King"};
    public void setupDB(){
        for(int i = 0; i < 100; ++i){
            Room room = new Room();
            room.setSmokingAllowed((int) (Math.random() * 5) == 0);
            room.setBedType(bedTypes[(int) (Math.random() * 2)]);
            room.setRoomNumber(String.valueOf(i));
            room.setId((long) (-i));
            room.setTheme(Room.Themes.values()[(int) (Math.random() * 3)]);
            room.setOceanView((int)(Math.random() * 2) == 0);
            room.setQualityLevel(Room.QualityLevel.values()[(int)(Math.random() * 4)]);
            addRoomToDB(room);
        }
    }
    public void addRoomToDB(Room room){
        roomRepository.save(room);
    }

}
