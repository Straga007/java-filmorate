package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(int filmId, Integer userId) {
        filmStorage.findFilm(filmId).setLikesIncrease(userId);
    }

    public void deleteLike(int filmId, Integer userId) {
        Film film = filmStorage.findFilm(filmId);
        if (film == null) {
            throw new NotFoundException("Film not found");
        }
        film.setLikesDecrease(userId);
    }

    public int getAllLikes(int filmId) {
        return filmStorage.findFilm(filmId).getLikes().size();
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.findPopularFilms(count);
    }

}
