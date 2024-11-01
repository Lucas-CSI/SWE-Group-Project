package com.example.seaSideEscape;

import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.RoomService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ReservationTest {
    static String[] bedTypes = {"Queen", "Twin", "King"};
    @Mock
    private static RoomService roomService;

    @BeforeAll
    public static void setupDB(){
        for(int i = 0; i < 100; ++i){
            Room room = new Room();
            room.setSmokingAllowed((int) (Math.random() * 5) == 0);
            room.setBedType(bedTypes[(int) (Math.random() * 3)]);
            room.setRoomNumber(String.valueOf(i));
            room.setId((long) (-i));
            room.setTheme(Room.Themes.values()[(int) (Math.random() * 3)]);
            room.setOceanView((int)(Math.random() * 2) == 0);
            room.setQualityLevel(Room.QualityLevel.values()[(int)(Math.random() * 4)]);
            roomService.addRoomToDB(room);
        }
    }

    @Test
    public void test(){
        System.out.println("Db?");
    }
}
