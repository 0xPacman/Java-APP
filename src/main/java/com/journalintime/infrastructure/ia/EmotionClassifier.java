package com.journalintime.infrastructure.ia;

import com.journalintime.domain.enums.EmotionType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Classificateur d'émotions basé sur l'analyse de texte.
 * Utilise un système de mots-clés et de scoring.
 */
@Component
public class EmotionClassifier {

    /**
     * Classifie les émotions dans un texte.
     */
    public ClassificationResult classify(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new ClassificationResult(
                EmotionType.SERENITE, 
                0.0, 
                0.0,
                Map.of()
            );
        }

        // Calcul des scores pour toutes les émotions
        Map<EmotionType, Double> scores = EmotionScoreCalculator.calculateScores(text);

        // Trouver l'émotion dominante
        EmotionType dominantEmotion = findDominantEmotion(scores);

        // Calculer l'intensité et la confiance
        double intensity = EmotionScoreCalculator.calculateIntensity(text, dominantEmotion);
        double confidence = EmotionScoreCalculator.calculateConfidence(scores);

        return new ClassificationResult(dominantEmotion, intensity, confidence, scores);
    }

    /**
     * Trouve l'émotion avec le score le plus élevé.
     */
    private EmotionType findDominantEmotion(Map<EmotionType, Double> scores) {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(EmotionType.SERENITE);
    }
}
