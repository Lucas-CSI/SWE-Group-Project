package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AdminPortalService {
    private final AccountService accountService;

    @Autowired
    public AdminPortalService(AccountService accountService) {
        this.accountService = accountService;
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

    public ResponseEntity<String> createNonGuestAccount(@RequestParam("username") String toCreateUsername, @RequestParam("email") String toCreateEmail, @CookieValue("username") String username, Account.PermissionLevel permissionLevel) throws NoSuchAlgorithmException {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Account accountObject = account.orElse(null);

        if(account.isPresent() && accountObject.getPermissionLevel().ordinal() > 1){
            accountService.createAccount(makeAccount(toCreateUsername, toCreateEmail, permissionLevel));
        }else{
            return new ResponseEntity<>("Error: Invalid permissions", HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok().body("Successfully created clerk account");
    }
}
