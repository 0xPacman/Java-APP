package com.journalintime.domain.repository;

import com.journalintime.domain.entity.Exercise;
import com.journalintime.domain.entity.User;
import com.journalintime.domain.enums.ExerciseCategory;

import java.util.List;
import java.util.Optional;

/**
 * Interface du repository Exercise (couche domaine).
 */
public interface ExerciseRepository {
    
    Exercise save(Exercise exercise);
    
    Optional<Exercise> findById(Long id);
    
    List<Exercise> findByUser(User user);
    
    List<Exercise> findActiveByUser(User user);
    
    List<Exercise> findByCategory(ExerciseCategory category);
    
    void delete(Exercise exercise);
}
