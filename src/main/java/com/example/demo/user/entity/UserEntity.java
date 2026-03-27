package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * JPA entity representing a user in the system.
 * Maps to the "users" table in the database.
 *
 * Lombok annotations used:
 * - @Getter / @Setter instead of @Data to avoid JPA pitfalls
 *   with equals() and hashCode() on lazy-loaded collections.
 * - @NoArgsConstructor required by JPA spec (proxy instantiation).
 * - @AllArgsConstructor for convenience when building instances manually.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * Primary key, auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for the user.
     * Cannot be null or duplicated across users.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Hashed password for the user.
     * Should never be stored or returned as plain text.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Unique email address for the user.
     * Cannot be null or duplicated across users.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Timestamp of when the user was created.
     * Set automatically via @PrePersist — never updated after insert.
     * updatable = false ensures JPA never modifies this column after creation.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Lifecycle callback executed automatically before the entity is persisted.
     * Sets createdAt to the current timestamp at insert time.
     * Never called manually.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}