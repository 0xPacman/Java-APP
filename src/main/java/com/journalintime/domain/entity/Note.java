package com.journalintime.domain.entity;

import com.journalintime.domain.enums.MoodLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.HashSet;
import java.util.Set;

/**
 * Entité Note - représente une entrée de journal utilisateur.
 * Le contenu est stocké en format Markdown et peut être chiffré.
 */
@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Indexed
public class Note extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    @FullTextField
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @FullTextField
    private String content;

    @Column(nullable = false)
    private Boolean encrypted = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MoodLevel moodLevel;

    @Column
    private Double sentimentScore;

    @Column
    private Double stressLevel;

    @Column
    private Double burnoutRisk;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Emotion> emotions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "note_tags",
        joinColumns = @JoinColumn(name = "note_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<NoteTag> tags = new HashSet<>();

    /**
     * Ajoute une émotion à la note.
     */
    public void addEmotion(Emotion emotion) {
        emotions.add(emotion);
        emotion.setNote(this);
    }

    /**
     * Retire une émotion de la note.
     */
    public void removeEmotion(Emotion emotion) {
        emotions.remove(emotion);
        emotion.setNote(null);
    }

    /**
     * Ajoute un tag à la note.
     */
    public void addTag(NoteTag tag) {
        tags.add(tag);
    }

    /**
     * Retire un tag de la note.
     */
    public void removeTag(NoteTag tag) {
        tags.remove(tag);
    }
}
