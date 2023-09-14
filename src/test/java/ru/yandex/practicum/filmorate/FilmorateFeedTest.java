package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FeedService;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.feed.FeedStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.DirectorDao;
import ru.yandex.practicum.filmorate.storage.film.dao.LikeDao;
import ru.yandex.practicum.filmorate.storage.user.daoImpl.FriendListDaoImpl;
import ru.yandex.practicum.filmorate.storage.user.storageImpl.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmorateFeedTest {
    private final UserDbStorage userStorage;
    private final FriendListDaoImpl friendListDaoImpl;
    @Mock
    private LikeDao likeDao;
    @Mock
    private FilmStorage filmStorage;
    private FilmService filmService;
    private FeedService feedService;
    @Mock
    private DirectorDao directorDao;
    @Mock
    private FeedStorage feedStorage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        filmService = new FilmService(filmStorage, likeDao, directorDao);
    }
    @BeforeEach
    public void start() {
        filmService.createFilm(new Film("Test Film", "This is a test film", LocalDate.of(2023, 8, 24), 150, 1));
        filmService.createFilm(new Film("Test Film 2", "This is a test film 2", LocalDate.of(2003, 3, 9), 190, 2));
        userStorage.createUser(new User("test@test.com", "test", "Test User", LocalDate.of(1995, 12, 18), 1));
        userStorage.createUser(new User("test2@test.com", "test2", "Test User 2", LocalDate.of(1998, 1, 30), 2));
    }



    @Test
    public void getFeedTest() {
        friendListDaoImpl.addFriend(1, 2);

        Collection<Event> feed = feedService.getFeed(1);
        assertNotNull(feed);
        assertEquals(1, feed.size(), "Количество объектов в ленте неверно");
    }
}
