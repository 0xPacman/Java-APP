package com.journalintime.application.service;

import com.journalintime.domain.entity.Note;
import com.journalintime.domain.entity.User;
import com.journalintime.domain.entity.MoodAnalysis;
import com.journalintime.domain.repository.NoteRepository;
import com.journalintime.domain.repository.MoodAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service d'analyse des tendances d'humeur.
 */
@Service
@Transactional
public class AnalysisService {

    private final NoteRepository noteRepository;
    private final MoodAnalysisRepository moodAnalysisRepository;

    public AnalysisService(NoteRepository noteRepository,
                          MoodAnalysisRepository moodAnalysisRepository) {
        this.noteRepository = noteRepository;
        this.moodAnalysisRepository = moodAnalysisRepository;
    }

    /**
     * Calcule le risque de burnout pour un utilisateur.
     */
    public double calculateBurnoutRisk(User user, int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<Note> recentNotes = noteRepository.findRecentByUser(user, since);

        if (recentNotes.isEmpty()) {
            return 0.0;
        }

        double totalStress = 0.0;
        double totalSentiment = 0.0;
        int count = 0;

        for (Note note : recentNotes) {
            if (note.getStressLevel() != null) {
                totalStress += note.getStressLevel();
            }
            if (note.getSentimentScore() != null) {
                totalSentiment += note.getSentimentScore();
            }
            count++;
        }

        if (count == 0) {
            return 0.0;
        }

        double avgStress = totalStress / count;
        double avgSentiment = totalSentiment / count;

        // Combine stress and negative sentiment
        double stressComponent = avgStress * 0.6;
        double sentimentComponent = Math.max(0, -avgSentiment) * 0.4;

        return Math.min(1.0, stressComponent + sentimentComponent);
    }

    /**
     * Crée une analyse d'humeur pour un utilisateur.
     */
    public MoodAnalysis createAnalysis(User user, int daysToAnalyze) {
        LocalDateTime since = LocalDateTime.now().minusDays(daysToAnalyze);
        List<Note> notes = noteRepository.findRecentByUser(user, since);

        double avgSentiment = notes.stream()
                .filter(n -> n.getSentimentScore() != null)
                .mapToDouble(Note::getSentimentScore)
                .average()
                .orElse(0.0);

        double avgStress = notes.stream()
                .filter(n -> n.getStressLevel() != null)
                .mapToDouble(Note::getStressLevel)
                .average()
                .orElse(0.0);

        long negativeCount = notes.stream()
                .filter(n -> n.getSentimentScore() != null && n.getSentimentScore() < 0)
                .count();

        long positiveCount = notes.stream()
                .filter(n -> n.getSentimentScore() != null && n.getSentimentScore() > 0)
                .count();

        double burnoutRisk = calculateBurnoutRisk(user, daysToAnalyze);

        MoodAnalysis analysis = MoodAnalysis.builder()
                .user(user)
                .analysisDate(LocalDate.now())
                .daysAnalyzed(daysToAnalyze)
                .averageSentiment(avgSentiment)
                .averageStress(avgStress)
                .burnoutRisk(burnoutRisk)
                .totalNotes(notes.size())
                .negativeNotesCount((int) negativeCount)
                .positiveNotesCount((int) positiveCount)
                .insights(generateInsights(avgSentiment, avgStress, burnoutRisk))
                .recommendations(generateRecommendations(burnoutRisk, avgStress))
                .build();

        return moodAnalysisRepository.save(analysis);
    }

    private String generateInsights(double sentiment, double stress, double burnout) {
        StringBuilder insights = new StringBuilder();
        
        if (sentiment < -0.3) {
            insights.append("Sentiment majoritairement négatif détecté. ");
        } else if (sentiment > 0.3) {
            insights.append("Sentiment majoritairement positif. ");
        }
        
        if (stress > 0.6) {
            insights.append("Niveau de stress élevé. ");
        }
        
        if (burnout > 0.6) {
            insights.append("Risque de burnout détecté. ");
        }
        
        return insights.toString().trim();
    }

    private String generateRecommendations(double burnout, double stress) {
        if (burnout > 0.6 || stress > 0.6) {
            return "Il est recommandé de prendre du temps pour soi, pratiquer des exercices de relaxation et consulter un professionnel si nécessaire.";
        }
        return "Continuez à prendre soin de votre bien-être mental.";
    }
}
