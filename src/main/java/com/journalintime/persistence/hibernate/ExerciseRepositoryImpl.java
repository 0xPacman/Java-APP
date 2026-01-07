package com.journalintime.persistence.hibernate;

import com.journalintime.domain.entity.Exercise;
import com.journalintime.domain.entity.User;
import com.journalintime.domain.enums.ExerciseCategory;
import com.journalintime.domain.repository.ExerciseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation Hibernate du repository Exercise.
 */
@Repository
@Transactional
public class ExerciseRepositoryImpl implements ExerciseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Exercise save(Exercise exercise) {
        if (exercise.getId() == null) {
            entityManager.persist(exercise);
            return exercise;
        } else {
            return entityManager.merge(exercise);
        }
    }

    @Override
    public Optional<Exercise> findById(Long id) {
        Exercise exercise = entityManager.find(Exercise.class, id);
        return Optional.ofNullable(exercise);
    }

    @Override
    public List<Exercise> findByUser(User user) {
        return entityManager
                .createQuery("SELECT e FROM Exercise e WHERE e.user = :user ORDER BY e.createdAt DESC", Exercise.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Exercise> findActiveByUser(User user) {
        return entityManager
                .createQuery("SELECT e FROM Exercise e WHERE e.user = :user AND e.deleted = false AND e.completed = false ORDER BY e.createdAt DESC", Exercise.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Exercise> findByCategory(ExerciseCategory category) {
        return entityManager
                .createQuery("SELECT e FROM Exercise e WHERE e.category = :category AND e.deleted = false", Exercise.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public void delete(Exercise exercise) {
        if (entityManager.contains(exercise)) {
            entityManager.remove(exercise);
        } else {
            entityManager.remove(entityManager.merge(exercise));
        }
    }
}
