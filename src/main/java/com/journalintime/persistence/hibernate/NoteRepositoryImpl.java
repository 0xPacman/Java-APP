package com.journalintime.persistence.hibernate;

import com.journalintime.domain.entity.Note;
import com.journalintime.domain.entity.User;
import com.journalintime.domain.repository.NoteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation Hibernate du repository Note.
 */
@Repository
@Transactional
public class NoteRepositoryImpl implements NoteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Note save(Note note) {
        if (note.getId() == null) {
            entityManager.persist(note);
            return note;
        } else {
            return entityManager.merge(note);
        }
    }

    @Override
    public Optional<Note> findById(Long id) {
        Note note = entityManager.find(Note.class, id);
        return Optional.ofNullable(note);
    }

    @Override
    public List<Note> findByUser(User user) {
        return entityManager
                .createQuery("SELECT n FROM Note n WHERE n.user = :user ORDER BY n.createdAt DESC", Note.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Note> findByUserAndNotDeleted(User user) {
        return entityManager
                .createQuery("SELECT n FROM Note n WHERE n.user = :user AND n.deleted = false ORDER BY n.createdAt DESC", Note.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Note> findRecentByUser(User user, LocalDateTime since) {
        return entityManager
                .createQuery("SELECT n FROM Note n WHERE n.user = :user AND n.deleted = false AND n.createdAt >= :since ORDER BY n.createdAt DESC", Note.class)
                .setParameter("user", user)
                .setParameter("since", since)
                .getResultList();
    }

    @Override
    public List<Note> findHighBurnoutRisk(User user, Double threshold) {
        return entityManager
                .createQuery("SELECT n FROM Note n WHERE n.user = :user AND n.deleted = false AND n.burnoutRisk > :threshold ORDER BY n.createdAt DESC", Note.class)
                .setParameter("user", user)
                .setParameter("threshold", threshold)
                .getResultList();
    }

    @Override
    public List<Note> searchByKeyword(String keyword) {
        String pattern = "%" + keyword + "%";
        return entityManager
                .createQuery("SELECT n FROM Note n WHERE n.deleted = false AND (LOWER(n.title) LIKE LOWER(:keyword) OR LOWER(n.content) LIKE LOWER(:keyword))", Note.class)
                .setParameter("keyword", pattern)
                .getResultList();
    }

    @Override
    public void delete(Note note) {
        if (entityManager.contains(note)) {
            entityManager.remove(note);
        } else {
            entityManager.remove(entityManager.merge(note));
        }
    }

    @Override
    public void softDelete(Note note) {
        note.softDelete();
        save(note);
    }
}
