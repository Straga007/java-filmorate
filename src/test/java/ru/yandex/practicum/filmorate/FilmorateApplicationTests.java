package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmorateApplicationTests {


    @Test
    void testInvalidUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User("", " ", "   ", LocalDate.now().plusDays(1));
        });
    }

    @Test
    public void testValidUser() {
        String email = "yooho@";
        String login = "Jenry";
        String name = "";
        LocalDate dateOfBirth = LocalDate.now().minusDays(1);
        User user = new User(email, login, name, dateOfBirth);
        Assertions.assertNotNull(user);

    }

    @Test
    public void testInvalidFilm() {
        String name = "";
        String description = "This is a description";
        LocalDate releaseDate = LocalDate.of(2021, 4, 27);
        long duration = -1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Film(name, description, releaseDate, duration);
        });
    }

    @Test
    public void testValidFilm() {
        String name = "The Matrix";
        String description = "A computer hacker learns from mysterious rebels about the true nature of his reality.";
        LocalDate releaseDate = LocalDate.of(1999, 3, 31);
        long duration = 136;

        Film film = new Film(name, description, releaseDate, duration);

        Assertions.assertNotNull(film);
    }
}
