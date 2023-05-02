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
    private static final LocalDate invalidReleaseDate = LocalDate.of(1895, 12, 28);
    private static int nextId = 0;

    @PostMapping(value = "/films")
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        validate(film);
        int id = film.getId() == 0 ? ++nextId : film.getId();
        Film newFilm = new Film(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), id);
        films.put(newFilm.getId(), newFilm);
        return ResponseEntity.ok(newFilm);

    }


    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film filmToUpdate) {
        Film existingFilm = films.get(filmToUpdate.getId());
        validate(filmToUpdate);

        existingFilm.setName(filmToUpdate.getName());
        existingFilm.setDescription(filmToUpdate.getDescription());
        existingFilm.setReleaseDate(filmToUpdate.getReleaseDate());
        existingFilm.setDuration(filmToUpdate.getDuration());
        return existingFilm;
    }


    @DeleteMapping("/films/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable int id) {
        if (films.remove(id) != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Текущее количество постов: {}", films.size());
        return new ArrayList<>(films.values());
    }

}
