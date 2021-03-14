package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUserId(long id);
    User findByName(String name);
    void delete(long id);
    User save(User user);
    Object update(User user, long id);
    void deleteAll();
}
