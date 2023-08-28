package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import ru.yandex.practicum.filmorate.validations.film.interfaces.ValidFilm;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidFilm
public class Film {

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    public Set<Integer> likes;
    private Collection<Genre> genres;
    private Mpa mpa;


    public Film(String name, String description, LocalDate releaseDate, int duration, int id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
        this.genres = new HashSet<>();
    }

}
