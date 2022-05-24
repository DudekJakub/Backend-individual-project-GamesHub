package com.gameshub.domain.book;

import com.gameshub.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "ID", unique = true)
    private Long id;

    @EqualsAndHashCode.Include
    private String googleId;
    private String title;
    private String author;
    private String category;
    private String link;
    private LocalDate publishedDate;

    @ManyToMany(mappedBy = "booksMemorized")
    private final Set<User> users = new HashSet<>();
}
