package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NonNull
@ToString
public class Film {
    private static int nextId = 1;

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;

    public Film(@NonNull String name, @NonNull String description, @NonNull LocalDate releaseDate, long duration, int id) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name can not be empty");
        }
        if (description.length() > 200) {
            throw new IllegalArgumentException("description length cannot be more than 200 characters");
        }
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("The release date should be after December 28, 1895");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("The duration of the film should be positive");
        }
        this.id = (id == 0) ? nextId++ : id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
