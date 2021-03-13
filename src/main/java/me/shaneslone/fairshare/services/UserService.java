package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByName(String name);
    void delete(long id);
    User save(User user);
    User update(User user, long id);
    public void deleteAll();
}
