package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.status.FriendshipStatus;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void createUser(User user) {
        userStorage.createUser(user);
    }

    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.findUser(userId);
        User friend = userStorage.findUser(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("User not found");
        }

        user.addPendingFriend(friendId);
        userStorage.updateUser(user);
    }

    public void confirmFriendship(int userId, int friendId) {
        User user = userStorage.findUser(userId);
        User friend = userStorage.findUser(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("User not found");
        }

            user.setFriend(friendId, FriendshipStatus.CONFIRMED);
            friend.setFriend(userId, FriendshipStatus.CONFIRMED);
            user.removePendingFriend(friendId);
            friend.removePendingFriend(userId);

            userStorage.updateUser(user);
            userStorage.updateUser(friend);

    }


    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public void updateUser(User userToUpdate) {
        userStorage.updateUser(userToUpdate);
    }

    public User findUser(int id) {
        return userStorage.findUser(id);
    }

    public List<User> getAllFriend(int userId) {
        try {
            Set<Integer> friendIds = userStorage.findUser(userId).getFriends();
            List<User> friends = new ArrayList<>();
            for (int friendId : friendIds) {
                User friend = userStorage.findUser(friendId);
                if (friend != null) {
                    friends.add(friend);
                }
            }
            return friends;
        } catch (NotFoundException e) {
            return Collections.emptyList();
        }
    }

    public void delFriend(int userId, int friendId) {
        userStorage.findUser(userId).delFriend(friendId);
        userStorage.findUser(friendId).delFriend(userId);

        userStorage.updateUser(userStorage.findUser(userId));
        userStorage.updateUser(userStorage.findUser(friendId));
    }

    public Collection<User> getCommonFriends(int userId, int friendId) {
        return userStorage.findCommonFriends(userId, friendId);
    }
}
