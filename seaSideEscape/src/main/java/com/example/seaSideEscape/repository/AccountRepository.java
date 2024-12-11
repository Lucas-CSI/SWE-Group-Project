package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Account} entities.
 * Provides CRUD operations and custom queries for account management.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds accounts by either username or email.
     *
     * @param username the username to search for
     * @param email    the email to search for
     * @return a list of accounts matching the given username or email
     */
    @Query("SELECT u FROM Account u WHERE u.username = ?1 OR u.email = ?2")
    List<Account> findByUsernameOrEmail(String username, String email);

    /**
     * Finds an account by its username.
     *
     * @param username the username of the account
     * @return an Optional containing the account if found, or empty otherwise
     */
    @Query("SELECT u FROM Account u WHERE u.username = ?1")
    Optional<Account> findByUsername(String username);

    /**
     * Finds an account by its username and password.
     *
     * @param username the username of the account
     * @param password the password of the account
     * @return an Optional containing the account if found, or empty otherwise
     */
    @Query("SELECT u FROM Account u WHERE u.username = ?1 AND u.password = ?2")
    Optional<Account> findByUsernameAndPassword(String username, String password);

    /**
     * Finds an account by its email.
     *
     * @param email the email of the account
     * @return an Optional containing the account if found, or empty otherwise
     */
    Optional<Account> findByEmail(String email);
}
