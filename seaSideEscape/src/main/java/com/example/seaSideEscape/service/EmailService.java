package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendConfirmationEmail(String to, String eventName, LocalDateTime eventDate, Venue venue) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Event Booking Confirmation");

        String formattedDate = eventDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String venueDetails = (venue.getName().isEmpty() ? "a venue" : venue.getName()) +
                " on Floor " + venue.getFloorNumber();

        message.setText("Dear Customer,\n\n" +
                "Your event '" + eventName + "' has been successfully booked for " + formattedDate +
                " at " + venueDetails + ".\n\n" +
                "Thank you for choosing our service!\n\nBest regards,\nSeaSide Escape Team");

        emailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText(
                "We received a request to reset your password. Click the link below to reset your password:\n\n" +
                        resetLink + "\n\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Best regards,\nSeaSide Escape Team");

        emailSender.send(message);
    }
}
