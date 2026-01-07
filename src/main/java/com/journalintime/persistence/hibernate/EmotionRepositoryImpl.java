package com.journalintime.persistence.hibernate;

import com.journalintime.domain.entity.Emotion;
import com.journalintime.domain.entity.Note;
import com.journalintime.domain.enums.EmotionType;
import com.journalintime.domain.repository.EmotionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation Hibernate du repository Emotion.
 */
@Repository
@Transactional
public class EmotionRepositoryImpl implements EmotionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Emotion save(Emotion emotion) {
        if (emotion.getId() == null) {
            entityManager.persist(emotion);
            return emotion;
        } else {
            return entityManager.merge(emotion);
        }
    }

    @Override
    public Optional<Emotion> findById(Long id) {
        Emotion emotion = entityManager.find(Emotion.class, id);
        return Optional.ofNullable(emotion);
    }

    @Override
    public List<Emotion> findByNote(Note note) {
        return entityManager
                .createQuery("SELECT e FROM Emotion e WHERE e.note = :note ORDER BY e.intensity DESC", Emotion.class)
                .setParameter("note", note)
                .getResultList();
    }

    @Override
    public List<Emotion> findByType(EmotionType type) {
        return entityManager
                .createQuery("SELECT e FROM Emotion e WHERE e.type = :type", Emotion.class)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public void delete(Emotion emotion) {
        if (entityManager.contains(emotion)) {
            entityManager.remove(emotion);
        } else {
            entityManager.remove(entityManager.merge(emotion));
        }
    }
}
