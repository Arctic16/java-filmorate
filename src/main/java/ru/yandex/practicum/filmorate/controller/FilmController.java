package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.FilmValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    HashMap<Integer, Film> filmHashMap = new HashMap<>();


    @PutMapping("/film")
    public Film addOrUpdateFilm(@RequestBody Film film) throws ValidationException {
        log.info("Получен PUT запрос.");
        if(FilmValidator.validate(film)){
            filmHashMap.put(film.getId(), film);
            log.info("Фильм добавлен или обновлён");
            return film;
        }else {
            log.warn("Фильм не прошёл валидацию!");
            return null;
        }
    }
    @GetMapping("/films")
    public List<Film> getFilms(){
        log.info("Получен GET запрос.");
        return new ArrayList<>(filmHashMap.values());
    }
}
