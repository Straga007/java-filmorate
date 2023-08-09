package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.LikeDao;

import java.util.Collection;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    private final LikeDao likesDao;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikeDao likesDao) {
        this.filmStorage = filmStorage;
        this.likesDao = likesDao;
    }

    public void createFilm(Film film) {
        filmStorage.createFilm(film);
    }

    public void updateFilm(Film filmToUpdate) {
        filmStorage.updateFilm(filmToUpdate);
    }

    public void deleteFilm(int filmToDelite) {
        filmStorage.deleteFilm(filmToDelite);
    }

    public Film findFilm(int id) {
        return filmStorage.findFilm(id);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public void addLike(int filmId, Integer userId) {
        filmStorage.findFilm(filmId).setLikesIncrease(userId);
    }

    public void deleteLike(int filmId, Integer userId) {
        Film film = filmStorage.findFilm(filmId);
        if (!film.hasLiked(userId)) {
            throw new NotFoundException("User with id " + userId + " has not liked the film with id " + filmId);
        }
        film.setLikesDecrease(userId);
    }

    public int getAllLikes(int filmId) {
        return filmStorage.findFilm(filmId).getLikes().size();
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.findPopularFilms(count);
    }

    public LikeDao getLikesDao() {
        return likesDao;
    }
}
