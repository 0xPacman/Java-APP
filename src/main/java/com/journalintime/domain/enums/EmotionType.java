package com.journalintime.domain.enums;

/**
 * Enumération des types d'émotions.
 */
public enum EmotionType {
    JOIE("Joie"),
    TRISTESSE("Tristesse"),
    COLERE("Colère"),
    PEUR("Peur"),
    DEGOUT("Dégoût"),
    SURPRISE("Surprise"),
    ANXIETE("Anxiété"),
    SERENITE("Sérénité"),
    FRUSTRATION("Frustration"),
    EXCITATION("Excitation");

    private final String displayName;

    EmotionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
