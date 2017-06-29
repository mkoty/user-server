package com.user.controller;

import com.user.entity.User;
import com.user.manager.UserManager;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by stepan.sichkar on 6/20/2017.
 */

@RestController
public class UserController {

    @Autowired
    UserManager manager;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public JSONObject getAllUsers() {

        JSONObject users = manager.getUsersAsJson();

        return users;
    }

    @RequestMapping(value = "/names", method = RequestMethod.POST)
    public Set<String> getUserNames(@RequestParam String prefix) {

        List<User> users = manager.getUsers();
        Set<String> names = new HashSet<String>();

        users.forEach(user -> {
            if (user.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
                names.add(user.getName());
            }
        });

        return names;
    }

    @RequestMapping(value = "/lastNames", method = RequestMethod.POST)
    public Set<String> getUserLastNames(@RequestParam String prefix) {

        List<User> users = manager.getUsers();
        Set<String> lastNames = new HashSet<String>();

        users.forEach(user -> {
            if (user.getLastName().toLowerCase().startsWith(prefix.toLowerCase())) {
                lastNames.add(user.getLastName());
            }
        });

        return lastNames;
    }

    @RequestMapping(value = "/fatherNames", method = RequestMethod.POST)
    public Set<String> getUserFatherNames(@RequestParam String prefix) {

        List<User> users = manager.getUsers();
        Set<String> fatherNames = new HashSet<String>();

        users.forEach(user -> {
            if (user.getFatherName().toLowerCase().startsWith(prefix.toLowerCase())) {
                fatherNames.add(user.getFatherName());
            }
        });

        return fatherNames;
    }

    @RequestMapping(value = "/userById", method = RequestMethod.POST)
    public String getUserById(@RequestParam String userId) {

        List<User> users = manager.getUsers();

        return manager.getUserAsJson(
                users.stream()
                        .filter(u -> u.getId().equals(userId))
                        .findFirst()
                        .orElse(null)
        );
    }

    @RequestMapping(value = "/usersByFullName", method = RequestMethod.POST)
    public List getUserByFullName(@RequestParam(value = "name", defaultValue = "") String name,
                                        @RequestParam(value = "lastName", defaultValue = "") String lastName,
                                        @RequestParam(value = "fatherName", defaultValue = "") String fatherName) {

        if (name.isEmpty() && lastName.isEmpty() && fatherName.isEmpty()) {
            return new ArrayList();
        }

        List<User> users = manager.getUsers();

        List filteredUsers;

        filteredUsers = users.stream()
                .filter(user -> name.isEmpty() ? true : user.getName().toLowerCase().equals(name.toLowerCase()))
                .filter(user -> lastName.isEmpty() ? true : user.getLastName().toLowerCase().equals(lastName.toLowerCase()))
                .filter(user -> fatherName.isEmpty() ? true : user.getFatherName().toLowerCase().equals(fatherName.toLowerCase()))
                .collect(Collectors.toList());

        return filteredUsers;
    }

}