package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.*;

import javax.validation.constraints.*;

@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor

public class Film {

    private int id;
    @NotEmpty(message = "name can not be empty")
    private String name;
    @Size(max = 200, message = "description length cannot be more than 200 characters")
    private String description;
    @PastOrPresent(message = "the release date should be after December 28, 1895")
    private LocalDate releaseDate;
    @Positive(message = "the duration of the film should be positive")
    private int duration;
    public Set<Integer> likes;
    @NotEmpty(message = "genres can not be empty")
    @Size(min = 1, message = "at least one genre should be specified")
    private Collection<Genre> genres;
    @Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$", message = "Invalid MPA value. Allowed values: G, PG, PG-13, R, NC-17")
    @NotBlank(message = "MPA can not be blank")
    private Mpa mpa;


    public Film(@NonNull @NotEmpty String name, @NonNull String description, @NonNull LocalDate releaseDate, int duration, int id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
        this.genres = new HashSet<>();
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
