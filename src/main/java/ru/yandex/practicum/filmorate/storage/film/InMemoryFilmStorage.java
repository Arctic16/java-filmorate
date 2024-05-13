package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.SearchException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private HashMap<Integer,Film> filmStorage = new HashMap<>();

    @Override
    public void addFilm(Film film) {
        filmStorage.put(film.getId(),film);
    }

    @Override
    public void removeFilm(int filmId) {
        filmStorage.remove(filmId);
    }

    @Override
    public void updateFilm(Film film) {
        if (filmStorage.containsKey(film.getId())) {
            filmStorage.put(film.getId(), film);
        }
    }

    @Override
    public Film getFilmById(int id) {
        if (filmStorage.containsKey(id)) {
            return filmStorage.get(id);
        } else {
            throw new SearchException("Фильм с данным id не найден");
        }
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(filmStorage.values());
    }
}
