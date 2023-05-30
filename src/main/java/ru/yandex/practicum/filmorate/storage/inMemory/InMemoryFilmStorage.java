package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;


@RestController
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer nextId = 0;
    final LocalDate invalidReleaseDate = LocalDate.of(1895, 12, 28);

    @Override
    public Collection<Film> findPopularFilms(Integer count) {
        List<Film> allFilms = new ArrayList<>(films.values());
        allFilms.sort((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()));
        int filmsCount = count != null ? count : 10;
        return allFilms.subList(0, Math.min(filmsCount, allFilms.size()));
    }


    @Override
    @GetMapping("/films")
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody @Valid Film film) {
        validate(film);
        int id = ++nextId;
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    @PutMapping("/films")
    public Film updateFilm(@RequestBody @Valid Film filmToUpdate) {
        Film existingFilm = films.get(filmToUpdate.getId());
        if (existingFilm == null) {
            throw new InternalServerException("Film with id " + filmToUpdate.getId() + " not found");
        }
        validate(filmToUpdate);
        existingFilm.setName(filmToUpdate.getName());
        existingFilm.setDescription(filmToUpdate.getDescription());
        existingFilm.setReleaseDate(filmToUpdate.getReleaseDate());
        existingFilm.setDuration(filmToUpdate.getDuration());
        return existingFilm;
    }

    @Override
    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable int id) {
        if (films.remove(id) == null) {
            throw new NotFoundException("Film with id " + id + " not found");
        }
    }


    @Override
    @GetMapping("/films/{id}")
    public Film findFilm(@PathVariable int id) {
        if (films.get(id) != null) {
            return films.get(id);
        } else {
            throw new NotFoundException("Не нашли фильм с id=" + id);
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

}

