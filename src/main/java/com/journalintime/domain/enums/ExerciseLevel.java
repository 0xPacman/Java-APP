package com.journalintime.domain.enums;

/**
 * Niveau de difficulté d'un exercice.
 */
public enum ExerciseLevel {
    DEBUTANT("Débutant"),
    INTERMEDIAIRE("Intermédiaire"),
    AVANCE("Avancé");

    private final String displayName;

    ExerciseLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
