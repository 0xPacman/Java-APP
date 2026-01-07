package com.journalintime.domain.enums;

/**
 * Niveau d'humeur général.
 */
public enum MoodLevel {
    EXCELLENT("Excellent", 5),
    BIEN("Bien", 4),
    MOYEN("Moyen", 3),
    MAL("Mal", 2),
    TRES_MAL("Très mal", 1);

    private final String displayName;
    private final int level;

    MoodLevel(String displayName, int level) {
        this.displayName = displayName;
        this.level = level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }
}
