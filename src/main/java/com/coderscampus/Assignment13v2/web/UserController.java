package com.coderscampus.Assignment13v2.web;

import com.coderscampus.Assignment13v2.domain.User;
import com.coderscampus.Assignment13v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {
        model.put("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/users";

    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        Set<User> users = userService.findAllUsersWithAccountsAndAddress();
        model.put("users", users);
        if (users.size() == 1) {
            model.put("user", users.iterator().next());
        }
        return "users";
    }

    @GetMapping("/users/{userid}")
    public String getOneUser(ModelMap model, @PathVariable Long userid) {
        User user = userService.findById(userid);
        model.put("users", Arrays.asList(user));
        model.put("user", user);
        return "users";

        }

      @PostMapping("/users/{userid}")
      public String updateThisUser(@PathVariable Long userid, User user) {
        userService.saveUser(user);
        return "redirect:/users/"+user.getUserid();
      }

      @PostMapping("/users/{userid}/delete")
      public String deleteThisUser(@PathVariable Long userid) {
        userService.delete(userid);
        return "redirect:/users";
      }

    }

