package com.nsteuerberg.todo.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;
    @Column(name = "username", unique = true)
    private String userName;
    @Column(name = "email", unique = true)
    private String userEmail;
    @Column(name = "password")
    private String userPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roleList;

    // fetch Lazy por defecto
    @OneToMany(
            mappedBy = "user", // indica que la relacion es bidereccional
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskEntity> tasks;
}
