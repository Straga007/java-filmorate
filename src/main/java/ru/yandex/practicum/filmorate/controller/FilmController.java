package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class FilmController {
    private final List<Film> posts = new ArrayList<>();
    private int lastAddedFilmId = 0;

    @PostMapping(value = "/films")
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        try {
            Film newFilm = new Film(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), ++lastAddedFilmId);
            posts.add(newFilm);
            return ResponseEntity.ok(newFilm);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film filmToUpdate) {
        Film existingFilm = posts.stream()
                .filter(f -> f.getId() == filmToUpdate.getId())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No film with id " + filmToUpdate.getId()));

        String name = filmToUpdate.getName();
        String description = filmToUpdate.getDescription();
        LocalDate releaseDate = filmToUpdate.getReleaseDate();
        long duration = filmToUpdate.getDuration();
        int newId = filmToUpdate.getId();

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
        existingFilm.setId(newId);
        existingFilm.setName(name);
        existingFilm.setDescription(description);
        existingFilm.setReleaseDate(releaseDate);
        existingFilm.setDuration(duration);

        log.info("Updating film with id {}: {}", filmToUpdate.getId(), existingFilm);

        return existingFilm;
    }

    @DeleteMapping("/films")
    public ResponseEntity<Void> deleteFilm(@PathVariable int id) {
        Film filmToDelete = null;
        for (Film film : posts) {
            if (film.getId() == id) {
                filmToDelete = film;
                break;
            }
        }
        if (filmToDelete != null) {
            posts.remove(filmToDelete);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Текущее количество постов: {}", posts.size());
        return posts;
    }

}
