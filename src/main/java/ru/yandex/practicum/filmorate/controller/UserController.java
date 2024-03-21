package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private HashMap<Integer, User> userHashMap = new HashMap<>();
    int id = 1;

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен PUT запрос.");
        if(!userHashMap.containsKey(user.getId())){
            throw new ValidationException("Пользователя с таким id не существует");
        }
        if (UserValidator.validate(user) && userHashMap.containsKey(user.getId())) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            } else if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            log.info("Пользователь обновлён");
            userHashMap.put(user.getId(), user);
            return user;
        } else {
            log.warn("Пользователь не прошёл валидацию или его не существует чтобы обновить!");
            return null;
        }
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        log.info("Получен POST запрос.");
        if (UserValidator.validate(user)) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            } else if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

            log.info("Пользователь добавлен");
            user.setId(id++);
            userHashMap.put(user.getId(), user);
            return user;
        } else {
            log.warn("Пользователь не прошёл валидацию!");
            return null;
        }
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен GET запрос.");
        return new ArrayList<>(userHashMap.values());
    }


}
