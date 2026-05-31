package com.example.educore.auth.domain.model;

import com.example.educore.auth.domain.events.UserRegisteredEvent;
import com.example.educore.sharedkernel.domain.AggregateRoot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "users")
@SQLRestriction("deleted = false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AggregateRoot {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String identification;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private User(String name, String lastName, String identification, String email, String passwordHash, Role role) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.lastName = lastName;
        this.identification = identification;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public static User register(String name, String lastName, String identification, String email, String passwordHash, Role role) {
        User user = new User(name, lastName, identification, email, passwordHash, role);
        user.registerEvent(new UserRegisteredEvent(user.getId(), user.getEmail()));
        return user;
    }

    public void promoteTo(Role newRole) {
        this.role = newRole;
    }
}
