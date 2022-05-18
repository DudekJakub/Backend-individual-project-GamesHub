package com.gameshub.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PROFANITIES")
public class Profanity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @EqualsAndHashCode.Include
    @Column(name = "VULGARISM", unique = true)
    private String vulgarism;
}
