package com.journalintime.domain.entity;

import com.journalintime.domain.enums.EmotionType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entité Emotion - représente une émotion détectée dans une note.
 */
@Entity
@Table(name = "emotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emotion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EmotionType type;

    @Column(nullable = false)
    private Double intensity;

    @Column
    private Double confidence;

    @Column(columnDefinition = "TEXT")
    private String context;
}
