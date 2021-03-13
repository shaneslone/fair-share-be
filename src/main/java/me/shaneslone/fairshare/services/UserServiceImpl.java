package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.exceptions.ResourceNotFoundException;
import me.shaneslone.fairshare.models.User;
import me.shaneslone.fairshare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<User> findAll() {
        List<User> rtnList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public User findByName(String name) {
        User user = userRepository.findByUsername(name.toLowerCase());
        if(user == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return user;
    }

    @Override
    public void delete(long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user, long id) {
        return null;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
