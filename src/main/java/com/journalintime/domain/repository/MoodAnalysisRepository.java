package com.journalintime.domain.repository;

import com.journalintime.domain.entity.MoodAnalysis;
import com.journalintime.domain.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface du repository MoodAnalysis (couche domaine).
 */
public interface MoodAnalysisRepository {
    
    MoodAnalysis save(MoodAnalysis analysis);
    
    Optional<MoodAnalysis> findById(Long id);
    
    List<MoodAnalysis> findByUser(User user);
    
    Optional<MoodAnalysis> findByUserAndDate(User user, LocalDate date);
    
    List<MoodAnalysis> findRecentByUser(User user, int days);
    
    void delete(MoodAnalysis analysis);
}
