package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class AdminPortalService {
    private final AccountService accountService;
    private final EmailService emailService;

    @Autowired
    public AdminPortalService(AccountService accountService, EmailService emailService) {
        this.accountService = accountService;
        this.emailService = emailService;
    }

    private Account makeAccount(String username, String email, Account.PermissionLevel permissionLevel){
        Account toCreate = new Account();
        String password = accountService.createSalt();
        toCreate.setUsername(username);
        toCreate.setEmail(email);
        toCreate.setPassword(password);
        toCreate.setPermissionLevel(permissionLevel);

        return toCreate;
    }

    private boolean checkPermission(String username, Account.PermissionLevel permissionLevel){
        Account account = accountService.getAccountObject(username);
        return account != null && accountService.checkPermission(account, permissionLevel);
    }

    public ResponseEntity<String> createNonGuestAccount(String toCreateUsername, String toCreateEmail, String username, Account.PermissionLevel permissionLevel) throws NoSuchAlgorithmException {
        if(checkPermission(username, Account.PermissionLevel.Admin)){
            Account account = makeAccount(toCreateUsername, toCreateEmail, permissionLevel);
            String password = account.getPassword();
            ResponseEntity<String> result = accountService.createAccount(account);
            if(result.getStatusCode() == HttpStatus.OK){
                emailService.sendAccountDetails(toCreateEmail, toCreateUsername, password, permissionLevel);
            }
            return result;
        }else{
            return new ResponseEntity<>("Error: Invalid permissions", HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public String modifyPermissionLevel(String toModifyUsername, Account.PermissionLevel permissionLevel, String username) throws NoSuchAlgorithmException {
        if(checkPermission(username, Account.PermissionLevel.Admin)){
            Account account = accountService.getAccountObject(toModifyUsername);
            if(account != null) {
                account.setPermissionLevel(permissionLevel);
                accountService.saveAccount(account);
            }else{
                return "Error: Account does not exist";
            }
            return "Error: Not enough permission";
        }
        return "Successfully modified permission level";
    }
}
