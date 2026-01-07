package com.journalintime.application.service;

import com.journalintime.domain.entity.Emotion;
import com.journalintime.domain.entity.Note;
import com.journalintime.domain.enums.EmotionType;
import com.journalintime.domain.repository.EmotionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service métier pour la gestion des émotions.
 */
@Service
@Transactional
public class EmotionService {

    private final EmotionRepository emotionRepository;

    public EmotionService(EmotionRepository emotionRepository) {
        this.emotionRepository = emotionRepository;
    }

    /**
     * Crée une nouvelle émotion pour une note.
     */
    public Emotion createEmotion(Note note, EmotionType type, double intensity, double confidence) {
        Emotion emotion = Emotion.builder()
                .note(note)
                .type(type)
                .intensity(intensity)
                .confidence(confidence)
                .build();

        return emotionRepository.save(emotion);
    }

    /**
     * Récupère toutes les émotions d'une note.
     */
    public List<Emotion> getNoteEmotions(Note note) {
        return emotionRepository.findByNote(note);
    }

    /**
     * Récupère toutes les émotions d'un type donné.
     */
    public List<Emotion> getEmotionsByType(EmotionType type) {
        return emotionRepository.findByType(type);
    }
}
