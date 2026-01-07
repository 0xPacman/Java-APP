package com.journalintime.domain.repository;

import com.journalintime.domain.entity.Note;
import com.journalintime.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface du repository Note (couche domaine).
 */
public interface NoteRepository {
    
    Note save(Note note);
    
    Optional<Note> findById(Long id);
    
    List<Note> findByUser(User user);
    
    List<Note> findByUserAndNotDeleted(User user);
    
    List<Note> findRecentByUser(User user, LocalDateTime since);
    
    List<Note> findHighBurnoutRisk(User user, Double threshold);
    
    List<Note> searchByKeyword(String keyword);
    
    void delete(Note note);
    
    void softDelete(Note note);
}
