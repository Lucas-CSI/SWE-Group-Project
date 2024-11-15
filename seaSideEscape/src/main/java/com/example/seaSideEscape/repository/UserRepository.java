//package com.example.seaSideEscape.repository;
//
//import com.example.seaSideEscape.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u FROM User u WHERE u.sessionKey = ?1")
//    Optional<User> findBySessionKey(String sessionKey);
//}
