package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NonNull
@ToString
public class Film {

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    public Set<Integer> likes;

    public Film(@NonNull String name, @NonNull String description, @NonNull LocalDate releaseDate, long duration, int id) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
    }

    public void setLikesIncrease(int ID) {
        likes.add(ID);
    }

    public void setLikesDecrease(int ID) {
        likes.remove(ID);
    }
}
