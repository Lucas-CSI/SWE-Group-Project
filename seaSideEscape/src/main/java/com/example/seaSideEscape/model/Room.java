package com.example.seaSideEscape.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String roomNumber;
    private Themes theme; //TBD
    private QualityLevel qualityLevel;  // executive, business, comfort, economy
    private String bedType; // twin, full, queen, king
    private boolean isSmokingAllowed;
    private double maxRate;
    public enum Themes{NATURE_RETREAT, URBAN_ELEGANCE, VINTAGE_CHARM}
    public enum QualityLevel{Economy, Comfort, Business, Executive}
    private boolean oceanView;

    public boolean isOceanView() {
        return oceanView;
    }

    public void setOceanView(boolean oceanView) {
        this.oceanView = oceanView;
    }

    @ManyToOne
    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getRoomNumber(){return roomNumber;}

    public void setRoomNumber(String roomNumber){this.roomNumber = roomNumber;}

    public Themes getTheme(){
        return theme;
    }

    public void setTheme(Themes theme){this.theme = theme;}

    public QualityLevel getQualityLevel(){return qualityLevel;}

    public void setQualityLevel(QualityLevel qualityLevel){this.qualityLevel = qualityLevel;}

    public String getBedType(){return bedType;};

    public void setBedType(String bedType){this.bedType = bedType;}

    public Boolean isSmokingAllowed(){return isSmokingAllowed;}

    public void setSmokingAllowed(Boolean smokingAllowed){isSmokingAllowed = smokingAllowed;}

    public double getMaxRate(){return maxRate;}

    public void setMaxRate(double maxRate){this.maxRate = maxRate;}
}
