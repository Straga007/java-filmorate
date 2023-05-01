package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping(value = "/films")
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        try {
            validate(film);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }

        Film newFilm = new Film(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());
        films.put(newFilm.getId(), newFilm);
        return ResponseEntity.ok(newFilm);

    }


    @PutMapping("/films")
    public ResponseEntity<Film> updateFilm(@RequestBody Film filmToUpdate) {
        Film existingFilm = films.get(filmToUpdate.getId());
        if (existingFilm == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            validate(filmToUpdate);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }

            existingFilm.setName(filmToUpdate.getName());
            existingFilm.setDescription(filmToUpdate.getDescription());
            existingFilm.setReleaseDate(filmToUpdate.getReleaseDate());
            existingFilm.setDuration(filmToUpdate.getDuration());
            return ResponseEntity.ok(existingFilm);

    }


    @DeleteMapping("/films/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable int id) {
        Film filmToDelete = films.get(id);
        if (filmToDelete != null) {
            films.remove(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void validate(Film film) throws IllegalArgumentException {
        String name = film.getName();
        String description = film.getDescription();
        LocalDate releaseDate = film.getReleaseDate();
        long duration = film.getDuration();

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
    }

    @GetMapping("/films")
    public Map<Integer, Film> findAll() {
        log.info("Текущее количество постов: {}", films.size());
        return films;
    }

}
