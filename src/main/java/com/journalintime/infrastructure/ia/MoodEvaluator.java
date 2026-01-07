package com.journalintime.infrastructure.ia;

import com.journalintime.domain.enums.EmotionType;
import com.journalintime.domain.enums.MoodLevel;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Évaluateur d'humeur basé sur les émotions et le sentiment.
 */
@Component
public class MoodEvaluator {

    /**
     * Évalue le niveau d'humeur global basé sur le texte.
     */
    public MoodLevel evaluateMood(String text) {
        ClassificationResult result = new EmotionClassifier().classify(text);
        double sentimentScore = calculateSentiment(result);
        
        return getMoodFromSentiment(sentimentScore);
    }

    /**
     * Calcule un score de sentiment (-1 à 1) basé sur les émotions.
     */
    public double calculateSentiment(ClassificationResult result) {
        EmotionType emotion = result.getDominantEmotion();
        double intensity = result.getIntensity();

        // Attribution de polarité aux émotions
        double polarity = switch (emotion) {
            case JOIE, SERENITE, EXCITATION -> 1.0;
            case SURPRISE -> 0.3;
            case ANXIETE, PEUR, FRUSTRATION -> -0.7;
            case TRISTESSE, COLERE, DEGOUT -> -1.0;
        };

        return polarity * intensity;
    }

    /**
     * Calcule le niveau de stress (0-1).
     */
    public double calculateStressLevel(ClassificationResult result) {
        EmotionType emotion = result.getDominantEmotion();
        double intensity = result.getIntensity();

        // Émotions associées au stress
        double stressFactor = switch (emotion) {
            case ANXIETE, PEUR -> 1.0;
            case FRUSTRATION, COLERE -> 0.8;
            case TRISTESSE -> 0.6;
            case SURPRISE -> 0.3;
            case JOIE, SERENITE, EXCITATION, DEGOUT -> 0.1;
        };

        return stressFactor * intensity;
    }

    /**
     * Convertit un score de sentiment en niveau d'humeur.
     */
    private MoodLevel getMoodFromSentiment(double sentiment) {
        if (sentiment > 0.6) return MoodLevel.EXCELLENT;
        if (sentiment > 0.2) return MoodLevel.BIEN;
        if (sentiment > -0.2) return MoodLevel.MOYEN;
        if (sentiment > -0.6) return MoodLevel.MAL;
        return MoodLevel.TRES_MAL;
    }

    /**
     * Évaluation complète d'une note.
     */
    public MoodEvaluationResult evaluate(String text) {
        ClassificationResult classification = new EmotionClassifier().classify(text);
        double sentiment = calculateSentiment(classification);
        double stress = calculateStressLevel(classification);
        MoodLevel mood = getMoodFromSentiment(sentiment);

        return new MoodEvaluationResult(classification, sentiment, stress, mood);
    }

    /**
     * Résultat complet de l'évaluation d'humeur.
     */
    public static class MoodEvaluationResult {
        private final ClassificationResult classification;
        private final double sentimentScore;
        private final double stressLevel;
        private final MoodLevel moodLevel;

        public MoodEvaluationResult(ClassificationResult classification, 
                                   double sentimentScore,
                                   double stressLevel,
                                   MoodLevel moodLevel) {
            this.classification = classification;
            this.sentimentScore = sentimentScore;
            this.stressLevel = stressLevel;
            this.moodLevel = moodLevel;
        }

        public ClassificationResult getClassification() { return classification; }
        public double getSentimentScore() { return sentimentScore; }
        public double getStressLevel() { return stressLevel; }
        public MoodLevel getMoodLevel() { return moodLevel; }
    }
}
