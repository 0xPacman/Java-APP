package com.journalintime.domain.entity;

import com.journalintime.domain.enums.ExerciseCategory;
import com.journalintime.domain.enums.ExerciseDuration;
import com.journalintime.domain.enums.ExerciseLevel;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entité Exercise - représente un exercice de relaxation recommandé.
 */
@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExerciseCategory category;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ExerciseLevel level;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ExerciseDuration duration;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(length = 50)
    private String triggerReason;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    /**
     * Marque l'exercice comme complété.
     */
    public void markAsCompleted() {
        this.completed = true;
    }
}
