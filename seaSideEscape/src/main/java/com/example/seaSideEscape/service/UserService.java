//package com.example.seaSideEscape.service;
//
//import com.example.seaSideEscape.model.User;
//import com.example.seaSideEscape.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public User getOrCreateSessionUser(String sessionKey) {
//        Optional<User> existingUser = userRepository.findBySessionKey(sessionKey);
//        if (existingUser.isPresent()) {
//            return existingUser.get();
//        }
//
//        // Create new session-based user if none exists
//        User sessionUser = new User();
//        sessionUser.setSessionKey(UUID.randomUUID().toString());
//        return userRepository.save(sessionUser);
//    }
//}
