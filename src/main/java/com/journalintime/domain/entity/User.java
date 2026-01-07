package com.journalintime.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

/**
 * Entité User pour l'authentification et la gestion des utilisateurs.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Indexed
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    @FullTextField
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    @FullTextField
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(length = 100)
    @FullTextField
    private String fullName;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(length = 20)
    @Builder.Default
    private String role = "USER";

    @Column(columnDefinition = "TEXT")
    private String encryptionKey;
}
