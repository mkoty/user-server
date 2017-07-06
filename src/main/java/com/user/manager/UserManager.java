package com.user.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.entity.User;
import com.user.entity.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by stepan.sichkar on 6/26/2017.
 */

@Component
public class UserManager {

    ServletContext context;
    ObjectMapper mapper;

    @Autowired
    public UserManager(ServletContext context, ObjectMapper mapper) {
        this.context = context;
        this.mapper = mapper;
    }

    public List<User> getUsers() {
        try {
            UserList users = mapper.readValue(new File(context.getRealPath("/users.json")), UserList.class);

            return users.getUsers();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
