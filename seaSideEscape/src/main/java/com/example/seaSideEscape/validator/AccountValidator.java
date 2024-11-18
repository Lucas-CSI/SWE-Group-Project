package com.example.seaSideEscape.validator;

import com.example.seaSideEscape.model.Account;

import java.util.HashMap;

public class AccountValidator implements Validator{
    private final HashMap<String, String> invalidItems = new HashMap<>();

    public AccountValidator(Account account){
        validateUsername(account.getUsername());
        validatePassword(account.getPassword());
    }

    private void validatePassword(String password){
        if(password.length() < 8){
            invalidItems.put("password", "Password is too short (min 8 characters).");
        }
    }

    private void validateUsername(String username){
        if(username.length() < 5){
            invalidItems.put("username", "Username is too short (min 4 characters).");
        }
    }

    @Override
    public HashMap<String, String> getInvalidItems() {
        return invalidItems;
    }

    @Override
    public boolean isValid() {
        return invalidItems.isEmpty();
    }
}
