package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private HashMap<Integer,User> userStorage = new HashMap<>();

    private int id = 1;

    @Override
    public User addUser(User user) {
        user.setId(id++);
        userStorage.put(user.getId(),user);
        return user;
    }

    @Override
    public void removeUser(int userId) {
        userStorage.remove(userId);
    }

    @Override
    public User updateUser(User user) {
        if (userStorage.containsKey(user.getId())) {
            userStorage.put(user.getId(), user);
            log.info("Пользователь обновлён");
            return user;
        } else {
            throw new NotFoundException("Пользователь с таким id не найден!");
        }
    }

    @Override
    public User getUserById(int id) {
        if (userStorage.containsKey(id)) {
            return userStorage.get(id);
        } else {
            throw new NotFoundException("Пользователь с таким id не найден!");
        }
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userStorage.values());
    }
}
