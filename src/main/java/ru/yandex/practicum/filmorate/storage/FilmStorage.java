package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;

public interface FilmStorage {

    Collection<Film> findAll();

    Film createFilm(@RequestBody @Valid Film film);

    Film updateFilm(Film film);

    void deleteFilm(@PathVariable int id);

    Film findFilm(@PathVariable int id);

    Collection<Film> findPopularFilms(Integer count);

}
