package me.shaneslone.fairshare.controllers;

import me.shaneslone.fairshare.models.User;
import me.shaneslone.fairshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<?> listAllUsers()
    {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userid}", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable long userid){
        User user = userService.findByUserId(userid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/user/name/{username}", produces = "application/json")
    public ResponseEntity<?> getUserByName(@PathVariable String username){
        User user = userService.findByName(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/getuserinfo", produces = "application/json")
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication){
        User user = userService.findByName(authentication.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User newuser){
        newuser.setUserId(0);
        newuser = userService.save(newuser);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserId())
                .toUri();
        responseHeaders.setLocation(newUserURI);
        return new ResponseEntity<>(newuser, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/user/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateFullUser(@Valid @RequestBody User updateUser, @PathVariable long userid){
        updateUser.setUserId(userid);
        updateUser = userService.save(updateUser);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @PatchMapping(value = "/user/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody User updateUser, @PathVariable long userid){
        User u = (User) userService.update(updateUser, userid);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @DeleteMapping(value ="/user/{userid}")
    public ResponseEntity<?> deleteUserById(@PathVariable long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
