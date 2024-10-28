package com.example.seaSideEscape.config;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

    @Configuration
    public class AdminInitializer {

        @Bean
        public CommandLineRunner initAdmin(AccountService accountService) {
            return args -> {
                String adminUsername = "admin"; // Default admin username
                String adminPassword = "password"; // Default admin password (secure in real applications)

                // Check if an admin already exists
                if (!accountService.adminExists(adminUsername)) {
                    Account adminAccount = new Account();
                    adminAccount.setUsername(adminUsername);

                    // Hash password before saving
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    adminAccount.setPassword(encoder.encode(adminPassword));
                    adminAccount.setIsAdmin(true); // Set admin flag

                    accountService.saveAccount(adminAccount);
                    System.out.println("Admin account created with username: " + adminUsername);
                } else {
                    System.out.println("Admin account already exists.");
                }
            };
        }
    }


