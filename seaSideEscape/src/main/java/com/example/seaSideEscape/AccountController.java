package com.example.seaSideEscape;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Account account) throws NoSuchAlgorithmException {
        return accountService.createAccount(account);
    }

    @GetMapping("/createAccount")
    public String createAccountPage(){
        return "create acccount";
    }

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(account -> ResponseEntity.ok(account))
                .orElse(ResponseEntity.notFound().build());
    }

    // Optionally, you can add other endpoints, such as listing all accounts
    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
