package com.coderscampus.Assignment13v2.web;

import com.coderscampus.Assignment13v2.domain.Account;
import com.coderscampus.Assignment13v2.domain.Address;
import com.coderscampus.Assignment13v2.domain.User;
import com.coderscampus.Assignment13v2.repository.AccountRepository;
import com.coderscampus.Assignment13v2.service.AccountService;
import com.coderscampus.Assignment13v2.service.AddressService;
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

    @Autowired
    private AddressService addressService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/users/register")
    public String getCreateUser(ModelMap model) {
        model.put("user", new User());

        return "register";
    }

    @PostMapping("/users/register")
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
        User user = userService.findByIdWithAccounts(userid);
        Address address = new Address();
        //List<Account> accounts = userService.findByAccountId(userid);
        if (user.getAddress() == null) {
            address.setUser(user);
            address.setUserId(userid);
            user.setAddress(address);
        }

        model.put("users", Arrays.asList(user));
        model.put("user", user);
        model.put("address", user.getAddress());
        return "users";
        }

      @PostMapping("/users/{userid}")
      public String updatePostThisUser(@PathVariable Long userid, User user) {
        user.getAddress().setUserId(userid);
        user.getAddress().setUser(user);
        User existingUser = userService.findByIdWithAccounts(userid);
        user.setAccounts(existingUser.getAccounts());
        user.setAddress(addressService.save(user.getAddress()));
        //Address address = addressService.save(user.getAddress(), userid, user);
        //user.setAddress(address);
        userService.saveUser(user);

        return "redirect:/users/"+user.getUserid();
      }

      @PostMapping("/users/{userid}/delete")
      public String deleteThisUser(@PathVariable Long userid) {
        userService.delete(userid);
        return "redirect:/users";
      }

//      @GetMapping("/users/{userid}/useraccount/{accountId}")
//      public String goToUserAccountPg(ModelMap model, @PathVariable Long userid, @PathVariable Long accountId) {
//          User thisUser = userService.findById(userid);
//          Account account = null;
//          for (Account userAcc : thisUser.getAccounts()) {
//              if (thisUser.getUserid() == userAcc.getAccountId()) {
//                  account = accountService.findById(userid);
//              }
//          }
//          //Account account = accountService.findById(thisUser.getAccounts());
//          model.put("userid", userid);
//          model.put("account", account);
//          return "useraccount";
//      }


    }

