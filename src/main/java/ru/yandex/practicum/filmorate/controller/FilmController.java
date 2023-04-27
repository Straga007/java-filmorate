package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
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


    @PostMapping(value = "/post")
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        try {
            posts.add(film);
            return ResponseEntity.ok(film);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/posts/post/update/{id}")
    public Film updateFilm(@PathVariable int id, @RequestBody Film filmToUpdate) {
        Film existingFilm = posts.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No film with id " + id));

        String name = filmToUpdate.getName();
        String description = filmToUpdate.getDescription();
        LocalDate releaseDate = filmToUpdate.getReleaseDate();
        long duration = filmToUpdate.getDuration();

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

        existingFilm.setName(name);
        existingFilm.setDescription(description);
        existingFilm.setReleaseDate(releaseDate);
        existingFilm.setDuration(duration);

        log.info("Updating film with id {}: {}", id, existingFilm);

        return existingFilm;
    }

    @DeleteMapping("/posts/post/delete/{id}")
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
