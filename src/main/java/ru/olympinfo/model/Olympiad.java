package ru.olympinfo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "olympiads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Olympiad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    private String subject;
    private String level;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String organizer; // username

    @ManyToMany(mappedBy = "favoriteOlympiads")
    private Set<User> users = new HashSet<>();
}