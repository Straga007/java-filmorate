package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class FilmController {
    private static final LocalDate invalidReleaseDate = LocalDate.of(1895, 12, 28);
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @PutMapping("film/{id}/like/{userId}") //  пользователь ставит лайк фильму.
    public void addLike(@PathVariable("id") int filmId, @PathVariable("userId") int userId) {
        filmService.addLike(filmId, userId);
        log.info("Пользователь c id {} поставил лайк фильму с id {}", userId, filmId);
    }
    @GetMapping("film/{id}/likes")
    public int getAllLikes(@PathVariable int id ){
        return filmService.getAllLikes(id);
    }

    public void validate(Film film) {
        String name = film.getName();
        String description = film.getDescription();
        LocalDate releaseDate = film.getReleaseDate();
        long duration = film.getDuration();

        if (name.isEmpty()) {
            throw new ValidationException("name can not be empty");
        }
        if (description.length() > 200) {
            throw new ValidationException("description length cannot be more than 200 characters");
        }
        if (releaseDate.isBefore(invalidReleaseDate)) {
            throw new ValidationException("The release date should be after December 28, 1895");
        }
        if (duration <= 0) {
            throw new ValidationException("The duration of the film should be positive");
        }
    }


}
