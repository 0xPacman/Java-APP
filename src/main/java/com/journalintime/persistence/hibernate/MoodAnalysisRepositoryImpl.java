package com.journalintime.persistence.hibernate;

import com.journalintime.domain.entity.MoodAnalysis;
import com.journalintime.domain.entity.User;
import com.journalintime.domain.repository.MoodAnalysisRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation Hibernate du repository MoodAnalysis.
 */
@Repository
@Transactional
public class MoodAnalysisRepositoryImpl implements MoodAnalysisRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MoodAnalysis save(MoodAnalysis analysis) {
        if (analysis.getId() == null) {
            entityManager.persist(analysis);
            return analysis;
        } else {
            return entityManager.merge(analysis);
        }
    }

    @Override
    public Optional<MoodAnalysis> findById(Long id) {
        MoodAnalysis analysis = entityManager.find(MoodAnalysis.class, id);
        return Optional.ofNullable(analysis);
    }

    @Override
    public List<MoodAnalysis> findByUser(User user) {
        return entityManager
                .createQuery("SELECT m FROM MoodAnalysis m WHERE m.user = :user ORDER BY m.analysisDate DESC", MoodAnalysis.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Optional<MoodAnalysis> findByUserAndDate(User user, LocalDate date) {
        List<MoodAnalysis> results = entityManager
                .createQuery("SELECT m FROM MoodAnalysis m WHERE m.user = :user AND m.analysisDate = :date", MoodAnalysis.class)
                .setParameter("user", user)
                .setParameter("date", date)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<MoodAnalysis> findRecentByUser(User user, int days) {
        LocalDate since = LocalDate.now().minusDays(days);
        return entityManager
                .createQuery("SELECT m FROM MoodAnalysis m WHERE m.user = :user AND m.analysisDate >= :since ORDER BY m.analysisDate DESC", MoodAnalysis.class)
                .setParameter("user", user)
                .setParameter("since", since)
                .getResultList();
    }

    @Override
    public void delete(MoodAnalysis analysis) {
        if (entityManager.contains(analysis)) {
            entityManager.remove(analysis);
        } else {
            entityManager.remove(entityManager.merge(analysis));
        }
    }
}
