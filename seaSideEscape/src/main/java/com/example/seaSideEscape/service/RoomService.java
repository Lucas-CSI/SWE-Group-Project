package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
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
    private final AccountService accountService;

    @Autowired
    public RoomService(RoomRepository roomRepository, BookingService bookingService, AccountService accountService) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
        this.accountService = accountService;
    }

    public boolean roomExists(Long roomId){
        return roomRepository.existsById(roomId);
    }
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate){
        return roomRepository.getAvailableRooms(checkInDate, checkOutDate);
    }

    public List<Room> getAvailableRooms(String username){
        Optional<Account> account = accountService.findAccountByUsername(username);
        Account accountObject = account.get();
        return roomRepository.getAvailableRooms(accountObject.getUnbookedReservation().getCheckInDate(), accountObject.getUnbookedReservation().getCheckOutDate());
    }

    public Room findSpecificAvailableRoom(Room room, LocalDate checkInDate, LocalDate checkOutDate){
        List<Room> rooms = roomRepository.findSpecificAvailableRooms(room.isSmokingAllowed(),room.getQualityLevel(), room.getBedType(), room.isOceanView(), room.getTheme(), checkInDate, checkOutDate);
        if(rooms == null || rooms.isEmpty()) return null;
        return rooms.getFirst();
    }

    static String[] bedTypes = {"Queen", "King"};

    public void setupDB(){
        for(int i = 0; i < 100; ++i){
            Room room = new Room();
            room.setQualityLevel(Room.QualityLevel.values()[(int)(Math.random() * 4)]);
            room.setSmokingAllowed((int) (Math.random() * 5) == 0);
            room.setQualityLevel(Room.QualityLevel.values()[(int)(Math.random() * 4)]);
            if(room.getQualityLevel().ordinal() <= 1){
                room.setBedType("Queen");
            }else{
                room.setBedType("King");
            }
            room.setRoomNumber(String.valueOf(i + 101));
            room.setTheme(Room.Themes.values()[(int) (Math.random() * 3)]);
            room.setOceanView((int)(Math.random() * 2) == 0);
            addRoomToDB(room);
        }
    }

    public List<Room> getRoomsInCart(String username){
        Optional<Account> account = accountService.findAccountByUsername(username);
        Account accountObject;

        if(account.isPresent()){
            accountObject = account.get();
            return roomRepository.getCart(accountObject);
        }
        return null;
    }

    public void addRoomToDB(Room room){
        roomRepository.save(room);
    }

}
