package com.example.seaSideEscape.validator;

import com.example.seaSideEscape.model.Room;

import java.util.HashMap;

public class RoomValidator implements Validator {
    private final HashMap<String, String> invalidItems = new HashMap<>();

    public RoomValidator(Room room) {
        validateQualityLevel(room.getQualityLevel(), "Quality Level");
        validateBedType(room.getBedType(), "Bed Type");
        validateTheme(room.getTheme(), "Theme");
        validateMaxRate(room.getMaxRate(), "Max Rate");
    }

    @Override
    public HashMap<String, String> getInvalidItems() {
        return invalidItems;
    }

    @Override
    public boolean isValid() {
        return invalidItems.isEmpty();
    }

    private void validateQualityLevel(Room.QualityLevel qualityLevel, String fieldName) {
        if (qualityLevel == null) {
            invalidItems.put(fieldName, "Quality level is required.");
        }
    }

    private void validateBedType(String bedType, String fieldName) {
        if (bedType == null || bedType.trim().isEmpty()) {
            invalidItems.put(fieldName, "Bed type is required.");
        }
    }

    private void validateTheme(Room.Themes theme, String fieldName) {
        if (theme == null) {
            invalidItems.put(fieldName, "Theme is required.");
        }
    }

    private void validateMaxRate(double maxRate, String fieldName) {
        if (maxRate <= 0) {
            invalidItems.put(fieldName, "Max rate must be positive.");
        }
    }
}
