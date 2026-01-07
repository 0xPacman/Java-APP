package com.journalintime.domain.enums;

/**
 * Durée d'un exercice.
 */
public enum ExerciseDuration {
    COURT("Court (5 min)", 5),
    MOYEN("Moyen (10-15 min)", 15),
    LONG("Long (20-30 min)", 30),
    TRES_LONG("Très long (30+ min)", 45);

    private final String displayName;
    private final int minutes;

    ExerciseDuration(String displayName, int minutes) {
        this.displayName = displayName;
        this.minutes = minutes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMinutes() {
        return minutes;
    }
}
