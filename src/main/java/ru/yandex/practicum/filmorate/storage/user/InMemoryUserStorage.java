package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.SearchException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{
    private HashMap<Integer,User> userStorage = new HashMap<>();
    @Override
    public void addUser(User user) {
        userStorage.put(user.getId(),user);
    }

    @Override
    public void removeUser(int userId) {
        userStorage.remove(userId);
    }

    @Override
    public void updateUser(User user) {
        if(userStorage.containsKey(user.getId())){
            userStorage.put(user.getId(), user);
        }else {
            throw new SearchException("Пользователь с таким id не найден!");
        }
    }
    @Override
    public User getUserById(int id){
        if(userStorage.containsKey(id)){
            return userStorage.get(id);
        }else{
            throw new SearchException("Пользователь с таким id не найден!");
        }
    }
    @Override
    public List<User> getUsers(){
        return new ArrayList<>(userStorage.values());
    }
}
