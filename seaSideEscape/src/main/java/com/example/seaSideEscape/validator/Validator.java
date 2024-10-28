package com.example.seaSideEscape.validator;

import java.util.HashMap;

public interface Validator {
    public HashMap<String, String> getInvalidItems();
    public boolean isValid();
}
