package com.zafer.RestfulServicesUdemy.model.dao;

import com.zafer.RestfulServicesUdemy.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoResource {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Henk", LocalDate.of(2000,1,1)));
        users.add(new User(2, "Peter", LocalDate.of(2000,1,1)));
        users.add(new User(3, "Nobody", LocalDate.of(2000,1,1)));
    }

    private static int userCount = 3;

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        UserDaoResource.users = users;
    }

    public User save(User user){
        if(user.getId()==0) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public Optional<User> findUser(int userId){
        for(User user : users) {
            if(user.getId()==userId){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> deleteUser(int userId){
        Iterator<User> userIterator = users.iterator();
        while(userIterator.hasNext()) {
            User user = userIterator.next();
            if(user.getId() == userId) {
                userIterator.remove();
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}