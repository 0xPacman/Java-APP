package com.journalintime.application.service;

import com.journalintime.domain.entity.Note;
import com.journalintime.domain.entity.User;
import com.journalintime.domain.entity.Emotion;
import com.journalintime.domain.repository.NoteRepository;
import com.journalintime.infrastructure.ia.ClassificationResult;
import com.journalintime.infrastructure.ia.EmotionClassifier;
import com.journalintime.infrastructure.ia.MoodEvaluator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service métier pour la gestion des notes.
 */
@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;
    private final EmotionService emotionService;
    private final EmotionClassifier emotionClassifier;
    private final MoodEvaluator moodEvaluator;

    public NoteService(NoteRepository noteRepository,
                      EmotionService emotionService,
                      EmotionClassifier emotionClassifier,
                      MoodEvaluator moodEvaluator) {
        this.noteRepository = noteRepository;
        this.emotionService = emotionService;
        this.emotionClassifier = emotionClassifier;
        this.moodEvaluator = moodEvaluator;
    }

    /**
     * Crée une nouvelle note avec analyse automatique.
     */
    public Note createNote(User user, String title, String content) {
        // Analyse du contenu
        MoodEvaluator.MoodEvaluationResult evaluation = moodEvaluator.evaluate(content);

        Note note = Note.builder()
                .user(user)
                .title(title)
                .content(content)
                .encrypted(false)
                .moodLevel(evaluation.getMoodLevel())
                .sentimentScore(evaluation.getSentimentScore())
                .stressLevel(evaluation.getStressLevel())
                .build();

        note = noteRepository.save(note);

        // Créer les émotions détectées
        ClassificationResult classification = evaluation.getClassification();
        emotionService.createEmotion(note, classification.getDominantEmotion(), 
                                    classification.getIntensity(), 
                                    classification.getConfidence());

        return note;
    }

    /**
     * Met à jour une note existante.
     */
    public Note updateNote(Long noteId, String title, String content) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if (optionalNote.isEmpty()) {
            throw new IllegalArgumentException("Note not found");
        }

        Note note = optionalNote.get();
        note.setTitle(title);
        note.setContent(content);

        // Re-analyser le contenu
        MoodEvaluator.MoodEvaluationResult evaluation = moodEvaluator.evaluate(content);
        note.setMoodLevel(evaluation.getMoodLevel());
        note.setSentimentScore(evaluation.getSentimentScore());
        note.setStressLevel(evaluation.getStressLevel());

        return noteRepository.save(note);
    }

    /**
     * Récupère toutes les notes actives d'un utilisateur.
     */
    public List<Note> getUserNotes(User user) {
        return noteRepository.findByUserAndNotDeleted(user);
    }

    /**
     * Récupère les notes récentes d'un utilisateur.
     */
    public List<Note> getRecentNotes(User user, int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return noteRepository.findRecentByUser(user, since);
    }

    /**
     * Recherche des notes par mot-clé.
     */
    public List<Note> searchNotes(String keyword) {
        return noteRepository.searchByKeyword(keyword);
    }

    /**
     * Supprime une note (soft delete).
     */
    public void deleteNote(Long noteId) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if (optionalNote.isPresent()) {
            noteRepository.softDelete(optionalNote.get());
        }
    }
}
