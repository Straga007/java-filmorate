package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
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

    public Film updateFilm(Film filmToUpdate) {
        return filmStorage.updateFilm(filmToUpdate);
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
        likesDao.addLikeToFilm(filmId, userId);
    }

    public void deleteLike(int filmId, Integer userId) {

        likesDao.deleteLikeFromFilm(filmId, userId);
    }

    public int getAllLikes(int filmId) {
        return filmStorage.findFilm(filmId).getLikes().size();
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.findPopularFilms(count);
    }

}
