package com.journalintime.domain.enums;

/**
 * Catégories d'exercices de relaxation.
 */
public enum ExerciseCategory {
    RESPIRATION("Respiration"),
    MEDITATION("Méditation"),
    ACTIVITE_PHYSIQUE("Activité physique"),
    CONNEXION_SOCIALE("Connexion sociale"),
    ACTIVITE_CREATIVE("Activité créative"),
    REPOS("Repos"),
    PLEINE_CONSCIENCE("Pleine conscience"),
    YOGA("Yoga");

    private final String displayName;

    ExerciseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
