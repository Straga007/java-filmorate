package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> findAll();

    ResponseEntity<Film> createFilm(Film film);

    Film updateFilm(Film film);

    ResponseEntity<Void> deleteFilm(@PathVariable int id);

    Film findFilm(@PathVariable int id);

    Collection<Film> findPopularFilms(Integer count);

}
