package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.repository.AccountRepository;
import com.example.seaSideEscape.validator.AccountValidator;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

/**
 * Service class for managing accounts.
 * Provides methods for creating, updating, and validating user accounts,
 * as well as utility functions for password hashing and salt generation.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * Constructs a new AccountService with the specified AccountRepository.
     *
     * @param accountRepository the repository used for account persistence and retrieval
     */
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Saves a new account to the repository.
     *
     * @param account the account to be saved
     */
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    /**
     * Generates a random salt string for securing passwords.
     *
     * @return a 16-character random string
     */
    public String createSalt() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param hash the byte array to convert
     * @return the hexadecimal representation of the byte array
     */
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Hashes a string using the SHA-256 algorithm.
     *
     * @param str the string to hash
     * @return the hashed representation of the input string in hexadecimal format
     * @throws NoSuchAlgorithmException if the SHA-256 algorithm is not available
     */
    private String getSHA256(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    /**
     * Hashes the password of an account using its salt and the SHA-256 algorithm.
     *
     * @param account the account whose password is to be hashed
     * @return the hashed password
     * @throws NoSuchAlgorithmException if the SHA-256 algorithm is not available
     */
    private String getHashedPassword(Account account) throws NoSuchAlgorithmException {
        return getSHA256(account.getPassword() + account.getSalt());
    }

    /**
     * Creates a new account if it does not already exist.
     *
     * @param account the account to create
     * @return a ResponseEntity with the operation status
     * @throws NoSuchAlgorithmException if the SHA-256 algorithm is not available
     */
    @Transactional
    public ResponseEntity<String> createAccount(Account account) throws NoSuchAlgorithmException {
        AccountValidator validator = new AccountValidator(account);
        if (accountRepository.findByUsernameOrEmail(account.getUsername(), account.getEmail()).isEmpty()) {
            if (validator.isValid()) {
                String saltStr = createSalt();
                account.setSalt(saltStr);
                String password = getHashedPassword(account);

                account.setPassword(password);
                accountRepository.save(account);
                return new ResponseEntity<>("Successfully created account.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(validator.getInvalidItems().values().stream().findFirst().get(), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>("Account already exists.", HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves an account by its username.
     *
     * @param username the username of the account to find
     * @return an Optional containing the account if found, or empty otherwise
     */
    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    /**
     * Retrieves an account object by its username, or null if not found.
     *
     * @param username the username of the account to retrieve
     * @return the account object, or null if not found
     */
    public Account getAccountObject(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    /**
     * Checks if an account has the required permission level.
     *
     * @param account               the account to check
     * @param minimumPermissionLevel the minimum required permission level
     * @return true if the account has the required permission level, false otherwise
     */
    public boolean checkPermission(Account account, Account.PermissionLevel minimumPermissionLevel) {
        return account.getPermissionLevel().ordinal() >= minimumPermissionLevel.ordinal();
    }

    /**
     * Verifies login credentials for an account.
     *
     * @param account the account with the credentials to validate
     * @return an Optional containing the account if the credentials are valid, or empty otherwise
     * @throws NoSuchAlgorithmException if the SHA-256 algorithm is not available
     */
    public Optional<Account> canLogin(Account account) throws NoSuchAlgorithmException {
        Optional<Account> acc = accountRepository.findByUsername(account.getUsername());
        String password;
        if (acc.isPresent()) {
            account.setSalt(acc.get().getSalt());
            password = getHashedPassword(account);
            if (password.equals(acc.get().getPassword())) {
                return acc;
            }
        }
        return Optional.empty();
    }

    /**
     * Updates the password of an account identified by its email.
     *
     * @param email       the email of the account to update
     * @param newPassword the new password to set
     * @return a ResponseEntity with the operation status
     */
    @Transactional
    public ResponseEntity<String> updatePassword(String email, String newPassword) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            String newSalt = createSalt();
            account.setSalt(newSalt);

            try {
                String hashedPassword = getSHA256(newPassword + newSalt);
                account.setPassword(hashedPassword);

                accountRepository.save(account);

                return new ResponseEntity<>("Password updated successfully.", HttpStatus.OK);
            } catch (NoSuchAlgorithmException e) {
                return new ResponseEntity<>("Error updating password. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Account not found.", HttpStatus.NOT_FOUND);
    }
}
