package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    @Override
    public Collection<Film> findAll() {
        return null;
    }

    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public Film findFilmById(Integer filmId) {
        return null;
    }

    @Override
    public Collection<Film> findPopularFilms(Integer count) {
        return null;
    }
}
