package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> findAll();

    Film createFilm(@RequestBody Film film);

    Film updateFilm(Film film);

    void deleteFilm(@PathVariable int id);

    Film findFilm(@PathVariable int id);

    Collection<Film> findPopularFilms(Integer count);

}
