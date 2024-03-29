package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.LikeDao;

import java.time.LocalDate;
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
        validation(film);
        filmStorage.createFilm(film);
    }

    public Film updateFilm(Film filmToUpdate) {
        validation(filmToUpdate);
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

    private void validation(Film film) {
        final LocalDate latestReleaseDate = LocalDate.of(1895, 12, 28);

        if (film.getReleaseDate().isBefore(latestReleaseDate)) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года.");
        }
        if (film.getName().isEmpty() && film.getName().isBlank()) {
            throw new ValidationException("name cod not be blank or empty");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("duration mast be positive ");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("duration must be less then 200");
        }
    }
}
