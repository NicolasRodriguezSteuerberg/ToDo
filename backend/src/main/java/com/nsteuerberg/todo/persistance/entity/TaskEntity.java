package com.nsteuerberg.todo.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private boolean isFinished;
    @Column(nullable = false)
    private String createdIn;
    @Column()
    private String lastUpdateIn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")// nombre de la columna a generar
    private UserEntity user;
}
