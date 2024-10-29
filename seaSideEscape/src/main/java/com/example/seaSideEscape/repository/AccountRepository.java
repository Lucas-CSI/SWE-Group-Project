package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT u FROM Account u WHERE u.username = ?1")
    Optional<Account> findByUsername(String userName);
}