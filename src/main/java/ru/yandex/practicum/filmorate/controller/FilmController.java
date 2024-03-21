package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.FilmValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private HashMap<Integer, Film> filmHashMap = new HashMap<>();
    private int id = 0;


    @PutMapping("/films")
    public Film addOrUpdateFilm(@RequestBody Film film) {
        log.info("Получен PUT запрос.");
        if (FilmValidator.validate(film)) {
            filmHashMap.put(film.getId(), film);
            log.info("Фильм обновлён!");
            return film;
        } else {
            log.warn("Фильм не прошёл валидацию!");
            return null;
        }
    }


    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) {
        log.info("Получен POST запрос.");
        if (FilmValidator.validate(film)) {
            film.setId(id++);
            filmHashMap.put(film.getId(), film);
            log.info("Фильм добавлен!");
            return film;
        } else {
            log.warn("Фильм не прошёл валидацию!");
            return null;
        }
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен GET запрос.");
        return new ArrayList<>(filmHashMap.values());
    }



}
