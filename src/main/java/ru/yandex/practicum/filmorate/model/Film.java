package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NonNull
@ToString
public class Film {
    private static int nextId = 0;

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;

    public Film(@NonNull String name, @NonNull String description, @NonNull LocalDate releaseDate, long duration, int id) {

        this.id = (id == 0) ? nextId++ : id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
