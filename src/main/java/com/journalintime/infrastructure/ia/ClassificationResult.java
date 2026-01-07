package com.journalintime.infrastructure.ia;

import com.journalintime.domain.enums.EmotionType;

import java.util.Map;

/**
 * Résultat de la classification d'émotion.
 */
public class ClassificationResult {

    private final EmotionType dominantEmotion;
    private final double intensity;
    private final double confidence;
    private final Map<EmotionType, Double> allScores;

    public ClassificationResult(EmotionType dominantEmotion, 
                               double intensity, 
                               double confidence,
                               Map<EmotionType, Double> allScores) {
        this.dominantEmotion = dominantEmotion;
        this.intensity = intensity;
        this.confidence = confidence;
        this.allScores = allScores;
    }

    public EmotionType getDominantEmotion() {
        return dominantEmotion;
    }

    public double getIntensity() {
        return intensity;
    }

    public double getConfidence() {
        return confidence;
    }

    public Map<EmotionType, Double> getAllScores() {
        return allScores;
    }

    @Override
    public String toString() {
        return String.format("ClassificationResult{emotion=%s, intensity=%.2f, confidence=%.2f}", 
                           dominantEmotion, intensity, confidence);
    }
}
