package com.journalintime.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité NoteTag - représente un tag/étiquette pour classifier les notes.
 */
@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteTag extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 7)
    private String color;

    @Column(columnDefinition = "TEXT")
    private String description;
}
