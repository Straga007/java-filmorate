package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private static int nextId = 0;

    public Collection<User> findCommonFriends(int id, int otherId) {
        User user = users.get(id);
        User otherUser = users.get(otherId);
        if (user == null || otherUser == null) {
            throw new NoSuchElementException("User not found");
        }
        Set<Integer> userFriends = new HashSet<>(user.getFriends());
        Set<Integer> otherUserFriends = new HashSet<>(otherUser.getFriends());
        userFriends.retainAll(otherUserFriends);
        List<User> commonFriends = new ArrayList<>();
        for (Integer friendId : userFriends) {
            User friend = users.get(friendId);
            if (friend != null) {
                commonFriends.add(friend);
            }
        }
        return commonFriends;
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void deleteUser(int id) {
        if (users.remove(id) == null) {
            throw new NotFoundException("User with id " + id + " not found");
        }
    }

    public User createUser(User user) {
        validate(user);
        int id = ++nextId;
        User newUser = new User(user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), id);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public User updateUser(User userToUpdate) {
        User existingUser = users.get(userToUpdate.getId());
        if (existingUser == null) {
            throw new InternalServerException("User with id " + userToUpdate.getId() + " not found");
        }
        validate(userToUpdate);

        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setBirthday(userToUpdate.getBirthday());
        existingUser.setLogin(userToUpdate.getLogin());
        existingUser.setName(userToUpdate.getName());

        return existingUser;
    }

    public User findUser(int id) {
        if (users.get(id) != null) {
            return users.get(id);
        } else {
            throw new NotFoundException("user with id:" + id + " not found");
        }
    }


    public void validate(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthday = user.getBirthday();

        if (email.trim().isEmpty() || !email.contains("@")) {
            throw new ValidationException("Email must contain @ symbol");
        }
        if (login.trim().isEmpty() || login.contains(" ")) {
            throw new ValidationException("Login must not contain spaces");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new ValidationException("Date of birth cannot be in the future");
        }
    }
}
