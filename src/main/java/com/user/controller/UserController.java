package com.user.controller;

import com.user.entity.User;
import com.user.manager.UserManager;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> getUserNames(@RequestParam String prefix) {

        List<User> users = manager.getUsers();
        List<String> names = new ArrayList<String>();

        users.forEach(user -> {
            if (user.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
                names.add(user.getName());
            }
        });

        return names;
    }

    @RequestMapping(value = "/lastNames", method = RequestMethod.POST)
    public List<String> getUserLastNames(@RequestParam String prefix) {

        List<User> users = manager.getUsers();
        List<String> lastNames = new ArrayList<String>();

        users.forEach(user -> {
            if (user.getLastName().toLowerCase().startsWith(prefix.toLowerCase())) {
                lastNames.add(user.getLastName());
            }
        });

        return lastNames;
    }

    @RequestMapping(value = "/fatherNames", method = RequestMethod.POST)
    public List<String> getUserFatherNames(@RequestParam String prefix) {

        List<User> users = manager.getUsers();
        List<String> fatherNames = new ArrayList<String>();

        users.forEach(user -> {
            if (user.getFatherName().toLowerCase().startsWith(prefix.toLowerCase())) {
                fatherNames.add(user.getFatherName());
            }
        });

        return fatherNames;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String getUserById(@RequestParam String userId) {

        List<User> users = manager.getUsers();

        return manager.getUserAsJson(
                users.stream()
                        .filter(u -> u.getId().equals(userId))
                        .findFirst()
                        .orElse(null)
        );
    }

}