package com.zafer.RestfulServicesUdemy.controller;

import com.zafer.RestfulServicesUdemy.Exception.UserNotFoundException;
import com.zafer.RestfulServicesUdemy.model.User;
import com.zafer.RestfulServicesUdemy.model.dao.UserDaoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {

    @Autowired
    private UserDaoResource service;

    @GetMapping("/allUsers")
    public List<User> findAllUsers(){
        return service.getUsers();
    }

    @GetMapping("/user/{userId}")
    public User findUser(@PathVariable int userId) {
        Optional<User> user = service.findUser(userId);
        return user.orElseThrow( () -> new UserNotFoundException("userId "+userId));
    }

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //CREATED
    // input-detail of user
    // output- CREATED & return the created URI
}
