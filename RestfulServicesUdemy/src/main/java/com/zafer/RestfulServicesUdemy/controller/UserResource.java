package com.zafer.RestfulServicesUdemy.controller;

import com.zafer.RestfulServicesUdemy.Exception.UserNotFoundException;
import com.zafer.RestfulServicesUdemy.model.User;
import com.zafer.RestfulServicesUdemy.model.dao.UserDaoResource;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
    public EntityModel<User> findUser(@PathVariable int userId) {
        Optional<User> user = service.findUser(userId);

        if(!user.isPresent()) {
            throw new UserNotFoundException("userId "+userId);
        } else {
            EntityModel<User> userEntityModel = new EntityModel<>(user.get());
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findAllUsers());
            userEntityModel.add(linkTo.withRel("all-users"));
            return userEntityModel;
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable int userId) {
        System.out.println();
        Optional<User> user = service.deleteUser(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException("userId "+userId);
        }
    }

    //CREATED
    // input-detail of user
    // output- CREATED & return the created URI
}
