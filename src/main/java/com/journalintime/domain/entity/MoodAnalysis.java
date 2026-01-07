package com.journalintime.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entité MoodAnalysis - représente une analyse agrégée de l'humeur sur une période.
 */
@Entity
@Table(name = "mood_analysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodAnalysis extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate analysisDate;

    @Column(nullable = false)
    private Integer daysAnalyzed;

    @Column
    private Double averageSentiment;

    @Column
    private Double averageStress;

    @Column
    private Double burnoutRisk;

    @Column
    private Integer totalNotes;

    @Column
    private Integer negativeNotesCount;

    @Column
    private Integer positiveNotesCount;

    @Column(columnDefinition = "TEXT")
    private String insights;

    @Column(columnDefinition = "TEXT")
    private String recommendations;
}
