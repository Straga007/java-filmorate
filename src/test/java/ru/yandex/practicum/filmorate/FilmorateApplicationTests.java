
package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class FilmorateApplicationTests {

    @Mock
    private FilmStorage filmStorage;
    @Mock
    private UserStorage userStorage;
    @InjectMocks
    private UserService userService;
    @InjectMocks
    private FilmService filmService;


    @Test
    public void testAddLike() {
        int filmId = 1;
        int userId = 1;

        Film film = new Film("Film 1", "Description 1", LocalDate.now(), 120, filmId);
        User user = new User("yooho@", "Jenry", "", LocalDate.now().minusDays(1), userId);

        when(filmStorage.findFilm(filmId)).thenReturn(film);

        filmService.addLike(filmId, userId);

        assertTrue(film.getLikes().contains(userId));
    }

    @Test
    public void testAddFriend() {
        int userId = 1;
        int friendId = 2;

        User user = new User("yooho@", "Jenry", "", LocalDate.now().minusDays(1), userId);
        User friend = new User("friend@", "Friend", "", LocalDate.now().minusDays(1), friendId);

        when(userStorage.findUser(userId)).thenReturn(user);
        when(userStorage.findUser(friendId)).thenReturn(friend);

        userService.addFriend(userId, friendId);

        assertTrue(user.getFriends().contains(friendId));
        assertTrue(friend.getFriends().contains(userId));
    }

    @Test
    public void testDeleteFriend() {
        int userId = 1;
        int friendId = 2;

        User user = new User("user1@example.com", "user1", "User 1", LocalDate.now().minusDays(1), userId);
        User friend = new User("user2@example.com", "user2", "User 2", LocalDate.now().minusDays(1), friendId);

        user.setFriend(friendId);
        friend.setFriend(userId);

        when(userStorage.findUser(userId)).thenReturn(user);
        when(userStorage.findUser(friendId)).thenReturn(friend);

        userService.delFriend(userId, friendId);

        assertFalse(user.getFriends().contains(friendId));
        assertFalse(friend.getFriends().contains(userId));
    }

    @Test
    public void testCommonFriends() {
        int userId1 = 1;
        User user1 = new User("user1@example.com", "user1", "User 1", LocalDate.now().minusDays(1), userId1);
        user1.setFriend(2);

        int userId3 = 3;
        User user3 = new User("user3@example.com", "user3", "User 3", LocalDate.now().minusDays(1), userId3);
        user3.setFriend(2);

        int userId2 = 2;
        User user2 = new User("user2@example.com", "user2", "User 2", LocalDate.now().minusDays(1), userId2);
        user2.setFriend(1);
        user2.setFriend(3);

        when(userStorage.findUser(userId1)).thenReturn(user1);
        when(userStorage.findUser(userId2)).thenReturn(user2);
        when(userStorage.findUser(userId3)).thenReturn(user3);


        Collection<User> commonFriends = userService.getCommonFriends(userId1, userId3);
        Assertions.assertNotNull(commonFriends);
    }


  /*  @Test
    public void testRemoveLike() {
        final InMemoryUserStorage inUserStorage = new InMemoryUserStorage();
        final InMemoryFilmStorage inFilmStorage = new InMemoryFilmStorage();
        int filmId = 1;
        int userId = 1;

        Film film = new Film("Film 1", "Description 1", LocalDate.now(), 120, filmId);
        inFilmStorage.createFilm(film);
        User user = new User("yooho@", "Jenry", "", LocalDate.now().minusDays(1), userId);
        inUserStorage.createUser(user);

        Film initialFilm = inFilmStorage.findFilm(filmId);
        initialFilm.setLikesIncrease(userId);

        Film updatedFilm = inFilmStorage.findFilm(filmId);
        assertTrue(updatedFilm.getLikes().contains(userId));

        inFilmStorage.findFilm(filmId).setLikesDecrease(userId);
        Film finalFilm = inFilmStorage.findFilm(filmId);
        assertFalse(finalFilm.getLikes().contains(userId));
    }
*/
    @Test
    public void testTopLike() {
        Film film1 = new Film("Film 1", "Description 1", LocalDate.now(), 120, 1);
        Film film2 = new Film("Film 2", "Description 2", LocalDate.now(), 90, 2);

        when(filmStorage.findPopularFilms(anyInt())).thenReturn(Arrays.asList(film1, film2));

        Collection<Film> popularFilms = filmService.getPopularFilms(2);

        verify(filmStorage).findPopularFilms(2);
        assertEquals(2, popularFilms.size());
        assertTrue(popularFilms.contains(film1));
        assertTrue(popularFilms.contains(film2));
    }

    @Test
    void testInvalidUser() {
        final InMemoryUserStorage inUserStorage = new InMemoryUserStorage();


        Assertions.assertThrows(ValidationException.class, () -> {
            inUserStorage.validate((new User("", " ", "   ", LocalDate.now().plusDays(1), 1)));
        });
    }

    @Test
    public void testValidUser() {
        String email = "yooho@";
        String login = "Jenry";
        String name = "";
        LocalDate dateOfBirth = LocalDate.now().minusDays(1);
        User user = new User(email, login, name, dateOfBirth, 1);
        Assertions.assertNotNull(user);

    }


    @Test
    public void testValidFilm() {
        String name = "The Matrix";
        String description = "A computer hacker learns from mysterious rebels about the true nature of his reality.";
        LocalDate releaseDate = LocalDate.of(1999, 3, 31);
        long duration = 136;

        Film film = new Film(name, description, releaseDate, duration, 1);

        Assertions.assertNotNull(film);
    }
}

