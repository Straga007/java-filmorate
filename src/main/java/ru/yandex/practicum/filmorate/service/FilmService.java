package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.dao.MarkDao;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.DirectorDao;
import ru.yandex.practicum.filmorate.storage.film.dao.LikeDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeDao likesDao;
    private final DirectorDao directorDao;
    @Qualifier("markDaoImplement")
    private final MarkDao markStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, LikeDao likesDao, DirectorDao directorDao, MarkDao markStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesDao = likesDao;
        this.directorDao = directorDao;
        this.markStorage = markStorage;
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
        return filmStorage.findFilmById(id).stream()
                .peek(f -> f.setLikes(new HashSet<>(markStorage.findLikes(f))))
                .peek(film -> film.setMarks(new HashSet<>(markStorage.findMarks(film))))
                .findFirst().get();
//        return filmStorage.findFilm(id);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll().stream()
                .peek(film -> film.setLikes(new HashSet<>(markStorage.findLikes(film))))
                .peek(film -> film.setMarks(new HashSet<>(markStorage.findMarks(film))))
                .collect(Collectors.toList());
//        return filmStorage.findAll();
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

    public Collection<Film> getPopularFilms(Integer count, Integer genreId, Integer year) {
        return filmStorage.findPopularFilms(count, genreId, year);
    }

    public Collection<Film> getListOfCommonFilms(int userId, int friendId) {
        return filmStorage.findListOfCommonFilms(userId, friendId);
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

    public Collection<Film> getSortFilmByDirector(Integer directorId, String sortBy) {
        Director director = directorDao.getDirectorById(directorId);
        if (sortBy.equals("year") || sortBy.equals("likes")) {
            return filmStorage.findAll().stream()
                    .filter(film -> film.getDirectors().contains(director))
                    .sorted((f1, f2) -> {
                        if (sortBy.equals("year")) {
                            return f1.getReleaseDate().compareTo(f2.getReleaseDate());
                        } else {
                            return f2.getLikes().size() - f1.getLikes().size();
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            throw new ValidationException("Некорректный запрос на sort" + sortBy);
        }
    }

    public Collection<Film> getAllPopularFilms(String query, String by) {
        return filmStorage.findAllPopularFilms(query, by);
    }

    public List<Film> findRecommendedFilms(int id) {
        // проверка id пользователя
        if (!userStorage.isFindUserById(id)) {
            return null;
        }
        Map<Integer, Set<Integer>> usersWithPositiveMarks = markStorage.findAllUsersWithPositiveMarks();
        // Set с filmId для пользователя id
        Set<Integer> userMarkFilms = usersWithPositiveMarks.get(id);
        usersWithPositiveMarks.remove(id);
        if (userMarkFilms == null || usersWithPositiveMarks.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        // Ищем пользователя с наибольшим совпадением
        Integer userIdWithTopFreq = -1;
        int topFreq = -1;
        for (Integer userId : usersWithPositiveMarks.keySet()) {
            Set<Integer> filmsId = new HashSet<>(usersWithPositiveMarks.get(userId));
            filmsId.retainAll(userMarkFilms);
            int countFreq = filmsId.size();
            if (countFreq > topFreq) {
                topFreq = countFreq;
                userIdWithTopFreq = userId;
            }
        }
        // Получаем Set с filmId для пользователя с наибольшим совпадением
        Set<Integer> filmsId = usersWithPositiveMarks.get(userIdWithTopFreq);
        // Удаляем совпадающие filmId
        filmsId.removeAll(userMarkFilms);
        // Получаем список фильмов
        List<Film> films = new ArrayList<>();
        for (Integer filmId : filmsId) {
            films.add(filmStorage.findFilmById(filmId).stream()
                    .peek(f -> f.setLikes(new HashSet<>(markStorage.findLikes(f))))
                    .peek(film -> film.setMarks(new HashSet<>(markStorage.findMarks(film))))
                    .findFirst().get());
        }

        return films;
    }
}
