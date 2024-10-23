package com.example.seaSideEscape.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long roomId;

    private String roomNumber;
    private String theme; //TBD
    private String qualityLevel;  // executive, business, comfort, economy
    private String bedType; // twin, full, queen, king
    private boolean isSmokingAllowed;
    private double maxRate;
    private boolean isBooked;

    @ManyToOne
    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Long getId(){return roomId;}

    public void setId(Long id){this.roomId = id;}

    public String getRoomNumber(){return roomNumber;}

    public void setRoomNumber(String roomNumber){this.roomNumber = roomNumber;}

    public String getTheme(){return theme;}

    public void setTheme(String theme){this.theme = theme;}

    public String getQualityLevel(){return qualityLevel;}

    public void setQualityLevel(String qualityLevel){this.qualityLevel = qualityLevel;}

    public String getBedType(){return bedType;};

    public void setBedType(String bedType){this.bedType = bedType;}

    public Boolean isSmokingAllowed(){return isSmokingAllowed;}

    public void setSmokingAllowed(Boolean smokingAllowed){isSmokingAllowed = smokingAllowed;}

    public double getMaxRate(){return maxRate;}

    public void setMaxRate(double maxRate){this.maxRate = maxRate;}

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
