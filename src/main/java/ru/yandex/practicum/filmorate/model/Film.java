package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;

@Slf4j
@Data
@NonNull
@ToString
public class Film {

    private int id;
    @NotEmpty(message = "name can not be empty")
    private String name;
    @Size(max = 200, message = "description length cannot be more than 200 characters")
    private String description;
    @PastOrPresent(message = "the release date should be after December 28, 1895")
    private LocalDate releaseDate;
    @Positive(message = "the duration of the film should be positive")
    private long duration;
    public Set<Integer> likes;

    public Film(@NonNull @NotEmpty String name, @NonNull String description, @NonNull LocalDate releaseDate, long duration, int id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
    }

    public void setLikesIncrease(int id) {
        likes.add(id);
    }

    public void setLikesDecrease(int id) {
        likes.remove(id);
    }

    public boolean hasLiked(Integer userId) {
        return likes.contains(userId);
    }
}
