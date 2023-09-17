package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.LikeDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeDao likesDao;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikeDao likesDao, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
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

    public List<Film> findRecommendedFilms(int id) {
        HashMap<User, List<Film>> filmsTable = new HashMap<>();
        List<Film> userFilms = filmStorage.findLikedFilmsByUser(id);
        List<User> users = (List<User>) userStorage.findAll();

        users.remove(userStorage.findUser(id));

        for (User other : users) {
            List<Film> otherFilms = filmStorage.findLikedFilmsByUser(other.getId());
            filmsTable.put(other, otherFilms);
        }

        List<List<Film>> differencesTable = new ArrayList<>();
        for (List<Film> value : filmsTable.values()) {
            List<Film> filmsPackage = new ArrayList<>();

            for (Film film : value) {
                film = findFilm(film.getId());
                if (!userFilms.contains(film)) {
                    filmsPackage.add(film);
                }
            }
            differencesTable.add(filmsPackage);
        }

        differencesTable.removeIf(List::isEmpty);

        return differencesTable.stream()
                .min(Comparator.comparing(List<Film>::size))
                .orElse(new ArrayList<>());
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
