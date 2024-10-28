package com.example.seaSideEscape.inputValidation;

import java.util.HashMap;

public interface Validator {
    public HashMap<String, String> getInvalidItems();
    public boolean isValid();
}
