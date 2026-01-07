package com.journalintime.application.service;

import com.journalintime.domain.entity.Exercise;
import com.journalintime.domain.entity.User;
import com.journalintime.domain.enums.ExerciseCategory;
import com.journalintime.domain.enums.ExerciseDuration;
import com.journalintime.domain.enums.ExerciseLevel;
import com.journalintime.domain.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service de recommandation d'exercices de relaxation.
 */
@Service
@Transactional
public class RecommendationService {

    private final ExerciseRepository exerciseRepository;
    private final AnalysisService analysisService;

    public RecommendationService(ExerciseRepository exerciseRepository,
                                AnalysisService analysisService) {
        this.exerciseRepository = exerciseRepository;
        this.analysisService = analysisService;
    }

    /**
     * Recommande des exercices basés sur le profil de l'utilisateur.
     */
    public List<Exercise> recommendExercises(User user) {
        double burnoutRisk = analysisService.calculateBurnoutRisk(user, 7);
        List<Exercise> recommendations = new ArrayList<>();

        if (burnoutRisk > 0.7) {
            // Burnout élevé: repos et méditation
            recommendations.add(createExercise(user, "HIGH_BURNOUT",
                    "Méditation guidée pour le repos",
                    "Prenez 15 minutes pour vous reposer et méditer. Trouvez un endroit calme et concentrez-vous sur votre respiration.",
                    ExerciseCategory.MEDITATION, ExerciseLevel.DEBUTANT, ExerciseDuration.MOYEN));
            
            recommendations.add(createExercise(user, "HIGH_BURNOUT",
                    "Repos complet",
                    "Accordez-vous une pause complète. Déconnectez-vous du travail et des écrans.",
                    ExerciseCategory.REPOS, ExerciseLevel.DEBUTANT, ExerciseDuration.LONG));
        } else if (burnoutRisk > 0.4) {
            // Stress modéré: exercices de respiration
            recommendations.add(createExercise(user, "MODERATE_STRESS",
                    "Respiration 4-7-8",
                    "Inspirez pendant 4 secondes, retenez pendant 7 secondes, expirez pendant 8 secondes. Répétez 4 fois.",
                    ExerciseCategory.RESPIRATION, ExerciseLevel.DEBUTANT, ExerciseDuration.COURT));
            
            recommendations.add(createExercise(user, "MODERATE_STRESS",
                    "Marche en pleine conscience",
                    "Faites une marche de 15 minutes en vous concentrant sur vos sensations et votre environnement.",
                    ExerciseCategory.ACTIVITE_PHYSIQUE, ExerciseLevel.DEBUTANT, ExerciseDuration.MOYEN));
        } else {
            // Bien-être général
            recommendations.add(createExercise(user, "WELLNESS",
                    "Yoga doux",
                    "Pratiquez quelques postures de yoga pour maintenir votre bien-être.",
                    ExerciseCategory.YOGA, ExerciseLevel.INTERMEDIAIRE, ExerciseDuration.MOYEN));
        }

        // Sauvegarder les recommandations
        for (Exercise exercise : recommendations) {
            exerciseRepository.save(exercise);
        }

        return recommendations;
    }

    /**
     * Crée un exercice de relaxation.
     */
    private Exercise createExercise(User user, String triggerReason, String title,
                                   String description, ExerciseCategory category,
                                   ExerciseLevel level, ExerciseDuration duration) {
        return Exercise.builder()
                .user(user)
                .title(title)
                .description(description)
                .category(category)
                .level(level)
                .duration(duration)
                .completed(false)
                .triggerReason(triggerReason)
                .instructions(description)
                .build();
    }

    /**
     * Marque un exercice comme complété.
     */
    public void completeExercise(Long exerciseId) {
        exerciseRepository.findById(exerciseId).ifPresent(exercise -> {
            exercise.markAsCompleted();
            exerciseRepository.save(exercise);
        });
    }

    /**
     * Récupère les exercices actifs d'un utilisateur.
     */
    public List<Exercise> getActiveExercises(User user) {
        return exerciseRepository.findActiveByUser(user);
    }
}
