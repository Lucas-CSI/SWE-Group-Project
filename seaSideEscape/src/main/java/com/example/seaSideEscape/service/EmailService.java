package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.seaSideEscape.model.Account;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AccountRepository accountRepository;


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


    public void sendReservationConfirmation(String email, Long reservationId, List<Room> rooms,
                                            LocalDateTime checkInDate, LocalDateTime checkOutDate) {

        String subject = "Reservation Confirmation & Billing Information";
        StringBuilder message = new StringBuilder();
        message.append("Dear Guest,\n\n");
        message.append("Thank you for your reservation. Here are the details:\n\n");
        message.append("Reservation ID: ").append(reservationId).append("\n");
        message.append("Check-In: ").append(checkInDate).append("\n");
        message.append("Check-Out: ").append(checkOutDate).append("\n\n");
        message.append("Rooms:\n");

        double totalCost = 0.0;

        for (Room room : rooms) {
            double roomTotal = room.getMaxRate();
            if (room.isOceanView()) {
                roomTotal += 20.00;
            }
            if (room.isSmokingAllowed()) {
                roomTotal += 10.00;
            }
            totalCost += roomTotal;

            message.append("- Room Number: ").append(room.getRoomNumber()).append("\n");
            message.append("  Theme: ").append(room.getTheme()).append("\n");
            message.append("  Quality Level: ").append(room.getQualityLevel()).append("\n");
            message.append("  Ocean View: ").append(room.isOceanView() ? "Yes" : "No").append("\n");
            message.append("  Smoking Allowed: ").append(room.isSmokingAllowed() ? "Yes" : "No").append("\n");
            message.append("  Bed Type: ").append(room.getBedType()).append("\n");
            message.append("  Base Room Rate: $").append(String.format("%.2f", room.getMaxRate())).append("\n");
            if (room.isOceanView()) {
                message.append("  Ocean View Charge: $20.00\n");
            }
            if (room.isSmokingAllowed()) {
                message.append("  Smoking Charge: $10.00\n");
            }
            message.append("  Room Total: $").append(String.format("%.2f", roomTotal)).append("\n\n");
        }

        message.append("Subtotal: $").append(String.format("%.2f", totalCost)).append("\n");
        double tax = totalCost * 0.1;
        message.append("Tax (10%): $").append(String.format("%.2f", tax)).append("\n");
        message.append("Grand Total: $").append(String.format("%.2f", totalCost + tax)).append("\n\n");
        message.append("We look forward to hosting you!\n");
        message.append("SeaSide Escape Hotel");

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message.toString());

        emailSender.send(emailMessage);
    }


    public void sendAccountDetails(String to, String username, String password, Account.PermissionLevel permissionLevel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Clerk Account");
        message.setText(
                "We received a request to create a new " + permissionLevel.name().toLowerCase() + " account for you.\n"+
                        "Your username: " + username + "\nYour password: " + password + "\n\n Please delete this email once " +
                        "you have securely stored your password");

        emailSender.send(message);
    }
}
