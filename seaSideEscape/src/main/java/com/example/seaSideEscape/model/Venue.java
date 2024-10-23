package com.example.seaSideEscape.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int capacity;
    private boolean isBooked;
    private int floorNumber;

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getLocation(){return location;}

    public void setLocation(String location){this.location = location;}

    public int getCapacity(){return capacity;}

    public void setCapacity(int capacity){this.capacity = capacity;}

    public boolean isBooked(){return isBooked;}

    public void setBooked(boolean booked){this.isBooked = booked;}

    public int getFloorNumber() { return floorNumber; }

    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
}
