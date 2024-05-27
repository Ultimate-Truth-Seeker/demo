package com.example.demo.service;

import com.example.demo.repository.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceMap implements UsersService {
    private Map<String, User> users = new HashMap<>();
    private Integer nextId = 1;

    @Override
    public User save(User user) {
        user.setId(nextId.toString());
        users.put(nextId.toString(), user);
        nextId++;
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> all() {
        List<User> all = new ArrayList<>();
        for (String key : users.keySet()) {
            all.add(users.get(key));
        }
        return all;
    }

    @Override
    public void deleteById(String id) {
        users.remove(id);
    }

    @Override
    public User update(User user, String userId) {
        users.put(userId, user);
        return user;
    }
}
