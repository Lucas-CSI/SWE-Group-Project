package com.example.seaSideEscape.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "reset_token")
public class ResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public Long getId(){return id;}

    public void setID(Long id){this.id = id;}

    public String getToken(){return token;}

    public void setToken(String token){this.token = token;}

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public LocalDateTime getExpiryDate(){return expiryDate;}

    public void setExpiryDate(LocalDateTime expiryDate){this.expiryDate = expiryDate;}

}