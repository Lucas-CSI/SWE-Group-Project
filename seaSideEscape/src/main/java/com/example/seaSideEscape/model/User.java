//package com.example.seaSideEscape.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String phoneNumber;
//
//    // Optional session key for non-registered users
//    private String sessionKey;
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getSessionKey() {
//        return sessionKey;
//    }
//
//    public void setSessionKey(String sessionKey) {
//        this.sessionKey = sessionKey;
//    }
//}
