package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private HashMap<Integer,Film> filmStorage = new HashMap<>();
    private int id = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(id++);
        filmStorage.put(film.getId(), film);
        log.info("Фильм добавлен!");
        return film;
    }

    @Override
    public void removeFilm(int filmId) {
        filmStorage.remove(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmStorage.containsKey(film.getId())) {
            filmStorage.put(film.getId(), film);
            log.info("Фильм обновлён!");
            return film;
        } else {
            throw new NotFoundException("Фильм с данным id не найден!");
        }
    }

    @Override
    public Film getFilmById(int id) {
        if (filmStorage.containsKey(id)) {
            return filmStorage.get(id);
        } else {
            throw new NotFoundException("Фильм с данным id не найден");
        }
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(filmStorage.values());
    }
}
