package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userId, int friendId) {
        userStorage.findUser(userId).setFriend(friendId);
        userStorage.findUser(friendId).setFriend(userId);
    }

    public Set<Integer> getAllFriend(int userId) {
        return userStorage.findUser(userId).getFriends();
    }

    public void delFriend(int userId, int friendId) {
        userStorage.findUser(userId).delFriend(friendId);
        userStorage.findUser(friendId).delFriend(userId);
    }

    public Collection<User> getCommonFriends(int userId, int friendId) {

        return userStorage.findCommonFriends(userId, friendId);
    }
}
