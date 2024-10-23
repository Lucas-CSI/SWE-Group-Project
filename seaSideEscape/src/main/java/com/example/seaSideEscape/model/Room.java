package com.example.seaSideEscape.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String roomNumber;
    private String theme; //TBD
    private String qualityLevel;  // executive, business, comfort, economy
    private String bedType; // twin, full, queen, king
    private boolean isSmokingAllowed;
    private double maxRate;

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

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


}
