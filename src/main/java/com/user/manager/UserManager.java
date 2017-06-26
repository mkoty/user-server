package com.user.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.entity.User;
import com.user.entity.UserList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public JSONObject getUsersAsJson () {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser
                    .parse(new InputStreamReader(new FileInputStream(context.getRealPath("/users.json"))));

            JSONObject jsonUsers = (JSONObject) obj;

            return jsonUsers;

        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
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

    public String getUserAsJson (User user) {

        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(user);

            return mapper.writeValueAsString(user);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonInString);

        return null;
    }

}
