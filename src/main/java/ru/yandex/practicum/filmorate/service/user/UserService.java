package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.UserValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriends(int user1Id, int user2Id) {
        if (userStorage.getUserById(user1Id) != null && userStorage.getUserById(user2Id).getFriends() != null
        && user1Id != user2Id && (!userStorage.getUserById(user1Id).getFriends().contains(user2Id))
        && !userStorage.getUserById(user2Id).getFriends().contains(user1Id)) {
            userStorage.getUserById(user1Id).getFriends().add(user2Id);
            userStorage.getUserById(user2Id).getFriends().add(user1Id);
        }
    }

    public void removeFriends(int user1Id, int user2Id) {
        if (userStorage.getUserById(user1Id) != null && userStorage.getUserById(user2Id).getFriends() != null
                && user1Id != user2Id
        && (userStorage.getUserById(user1Id).getFriends().contains(user2Id))
                && userStorage.getUserById(user2Id).getFriends().contains(user1Id)) {
            userStorage.getUserById(user1Id).getFriends().remove(user2Id);
            userStorage.getUserById(user2Id).getFriends().remove(user1Id);
        }
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>();
        for (int i : userStorage.getUserById(userId).getFriends()) {
            friends.add(userStorage.getUserById(i));
        }
        return friends;
    }

    public List<User> getCommonFriends(int user1Id, int user2Id) {
        List<User> users = new ArrayList<>();
        Set<Integer> common = new HashSet<>(userStorage.getUserById(user1Id).getFriends());
        common.retainAll(userStorage.getUserById(user2Id).getFriends());
        for (int i : common) {
            users.add(userStorage.getUserById(i));
        }
        return users;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public User addUser(User user) {
        if (UserValidator.validate(user)) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            } else if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            log.info("Пользователь добавлен");
            return userStorage.addUser(user);

        } else {
            log.warn("Пользователь не прошёл валидацию!");
            return null;
        }
    }

    public User updateUser(User user) {
        if (UserValidator.validate(user)) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            } else if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            return userStorage.updateUser(user);
        } else {
            log.warn("Пользователь не прошёл валидацию или его не существует чтобы обновить!");
            return null;
        }
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }
}
