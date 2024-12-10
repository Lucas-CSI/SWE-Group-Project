package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

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
            room.setMaxRate(calculateRoomRate(room));
            roomRepository.save(room);
        }
    }

    private double calculateRoomRate(Room room) {
        double baseRate = 100.0;

        switch (room.getQualityLevel()) {
            case Executive -> baseRate *= 1.5;
            case Business -> baseRate *= 1.3;
            case Comfort -> baseRate *= 1.1;
            case Economy -> baseRate *= 1.0;
        }

        if (room.isOceanView()) {
            baseRate += 20.0;
        }
        if (room.isSmokingAllowed()) {
            baseRate += 10.0;
        }

        baseRate = Math.max(baseRate, 100.0);

        BigDecimal roundedRate = BigDecimal.valueOf(baseRate).setScale(2, RoundingMode.HALF_UP);
        return roundedRate.doubleValue();
    }
    public double calculateTotalCartCost(String username) {
        List<Room> cartRooms = getRoomsInCart(username);
        if (cartRooms == null || cartRooms.isEmpty()) {
            return 0.0;
        }
        return cartRooms.stream()
                .mapToDouble(Room::getMaxRate)
                .sum();
    }
    @Transactional
    public void clearCart(String username) {
        Optional<Account> account = accountService.findAccountByUsername(username);
        if (account.isPresent()) {
            Account accountObject = account.get();
            List<Room> cartRooms = getRoomsInCart(username);

            if (cartRooms != null && !cartRooms.isEmpty()) {
                for (Room room : cartRooms) {
                    bookingService.removeRoomFromCart(accountObject, room);
                }
            }
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
  

    public List<Map<String, Object>> getRoomDetailsInCart(String username) {
        Optional<Account> accountOptional = accountService.findAccountByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new IllegalArgumentException("Account not found for username: " + username);
        }

        Account account = accountOptional.get();
        List<Room> cartRooms = roomRepository.getCart(account);
        if (cartRooms == null || cartRooms.isEmpty()) {
            return Collections.emptyList();
        }

        return cartRooms.stream()
                .map(room -> {
                    Map<String, Object> roomDetails = new HashMap<>();
                    roomDetails.put("theme", room.getTheme().toString());
                    roomDetails.put("qualityLevel", room.getQualityLevel().toString());
                    roomDetails.put("oceanView", room.isOceanView());
                    roomDetails.put("smokingAllowed", room.isSmokingAllowed());
                    roomDetails.put("bedType", room.getBedType());
                    roomDetails.put("roomRate", room.getMaxRate());
                    return roomDetails;
                })
                .toList();
    }

    public Optional<Room> getRoomInfo(String roomNumber){
        return roomRepository.findRoomByNumber(roomNumber);
    }

    public Optional<Room> getRoomInfo(String roomNumber, String username){
        Account account = accountService.getAccountObject(username);
        if(account != null && accountService.checkPermission(account, Account.PermissionLevel.Clerk)){
            return getRoomInfo(roomNumber);
        }
        return Optional.empty();
    }

    public boolean isRoomBooked(Room room, LocalDate date){
        return roomRepository.isRoomBooked(room, date);
    }

}