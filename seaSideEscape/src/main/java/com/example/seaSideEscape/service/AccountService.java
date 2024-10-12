package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private String createSalt(){
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString();
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String getSHA256(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    private String getHashedPassword(Account account) throws NoSuchAlgorithmException {
        return getSHA256(account.getPassword() + account.getSalt());
    }

    public ResponseEntity<String> createAccount(Account account) throws NoSuchAlgorithmException {
        if(accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            String saltStr = createSalt();
            String password = getHashedPassword(account);

            account.setSalt(saltStr);
            account.setPassword(password);
            accountRepository.save(account);

            return new ResponseEntity<>("Successfully created account.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Account already exists.", HttpStatus.CONFLICT);
        }
    }

    public Optional<Account> canLogin(Account account) throws NoSuchAlgorithmException {
        Optional<Account> acc = accountRepository.findByUsername(account.getUsername());
        String password;
        if(acc.isPresent()) {
            password = getHashedPassword(account);
            if (password.equals(acc.get().getPassword())) {
                return acc;
            }
        }
        return Optional.empty();
    }
}