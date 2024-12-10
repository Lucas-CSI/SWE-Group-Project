package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT u FROM Account u WHERE u.username = ?1 OR u.email = ?2")
    List<Account> findByUsernameOrEmail(String userName, String email);

    @Query("SELECT u FROM Account u WHERE u.username = ?1")
    Optional<Account> findByUsername(String username);

    @Query("SELECT u FROM Account u WHERE u.username = ?1 AND u.password = ?2")
    Optional<Account> findByUsernameAndPassword(String username, String password);

    Optional<Account> findByEmail(String email);
}