package com.journalintime.infrastructure.ia;

import com.journalintime.domain.enums.EmotionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calculateur de score d'émotion basé sur les mots-clés.
 */
public class EmotionScoreCalculator {

    /**
     * Calcule le score de chaque émotion dans le texte.
     */
    public static Map<EmotionType, Double> calculateScores(String text) {
        Map<EmotionType, Double> scores = new HashMap<>();
        
        String normalizedText = TextProcessor.normalize(text);
        List<String> tokens = TextProcessor.tokenize(normalizedText);
        
        if (tokens.isEmpty()) {
            return scores;
        }
        
        for (EmotionType emotionType : EmotionType.values()) {
            double score = calculateEmotionScore(tokens, emotionType);
            scores.put(emotionType, score);
        }
        
        return scores;
    }

    /**
     * Calcule le score d'une émotion spécifique.
     */
    private static double calculateEmotionScore(List<String> tokens, EmotionType emotionType) {
        List<String> keywords = EmotionKeywordDictionary.getKeywordsForEmotion(emotionType);
        
        if (keywords.isEmpty()) {
            return 0.0;
        }
        
        int matchCount = 0;
        for (String token : tokens) {
            for (String keyword : keywords) {
                String normalizedKeyword = TextProcessor.normalize(keyword);
                if (token.equals(normalizedKeyword) || token.contains(normalizedKeyword)) {
                    matchCount++;
                }
            }
        }
        
        // Score normalisé par le nombre de tokens
        return (double) matchCount / tokens.size();
    }

    /**
     * Calcule l'intensité d'une émotion (0-1).
     */
    public static double calculateIntensity(String text, EmotionType emotionType) {
        String normalizedText = TextProcessor.normalize(text);
        List<String> tokens = TextProcessor.tokenize(normalizedText);
        
        if (tokens.isEmpty()) {
            return 0.0;
        }
        
        double rawScore = calculateEmotionScore(tokens, emotionType);
        
        // Normalisation avec saturation à 0.5
        return Math.min(1.0, rawScore * 10);
    }

    /**
     * Calcule la confiance de la détection.
     */
    public static double calculateConfidence(Map<EmotionType, Double> scores) {
        if (scores.isEmpty()) {
            return 0.0;
        }
        
        double maxScore = scores.values().stream()
                .max(Double::compareTo)
                .orElse(0.0);
        
        double totalScore = scores.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        
        if (totalScore == 0) {
            return 0.0;
        }
        
        // Confiance basée sur la proportion du score maximum
        return maxScore / totalScore;
    }
}
