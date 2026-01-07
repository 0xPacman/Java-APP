package com.journalintime.domain.repository;

import com.journalintime.domain.entity.Emotion;
import com.journalintime.domain.entity.Note;
import com.journalintime.domain.enums.EmotionType;

import java.util.List;
import java.util.Optional;

/**
 * Interface du repository Emotion (couche domaine).
 */
public interface EmotionRepository {
    
    Emotion save(Emotion emotion);
    
    Optional<Emotion> findById(Long id);
    
    List<Emotion> findByNote(Note note);
    
    List<Emotion> findByType(EmotionType type);
    
    void delete(Emotion emotion);
}
