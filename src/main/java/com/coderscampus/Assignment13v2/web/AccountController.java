package com.coderscampus.Assignment13v2.web;

import com.coderscampus.Assignment13v2.domain.Account;
import com.coderscampus.Assignment13v2.domain.User;
import com.coderscampus.Assignment13v2.service.AccountService;
import com.coderscampus.Assignment13v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/users/{userid}/registerNewUserAccount/")
    public String getNewAccount(ModelMap model, @PathVariable Long userid) {
        Account account = new Account();
        Account lastAccount = null;
        User thisUser = userService.findByIdWithAccounts(userid);
        Account thisUserAcc = userService.createAccount(userid);

        model.put("registerNewUserAccount", thisUserAcc);
        model.put("lastAccount", lastAccount);
        return "registerNewUserAccount";
    }

    @PostMapping("/users/{userid}/registerNewUserAccount/")
    public String updateNewAccount(ModelMap model, @PathVariable Long userid, Account account) {
        User user = userService.findById(userid);
        List<Account> users = user.getAccounts();
        Stream<Account> stream = users.stream();
        long count = users.stream().count();
        Account la = stream.skip(count - 1).findFirst().get();
        la.setAccountName(account.getAccountName());
        accountService.saveAccount(la);

        return "redirect:/users/"+user.getUserid();
    }



    @GetMapping("/users/{userid}/useraccount/{accountid}")
    public String userAccountPg(ModelMap model, @PathVariable Long userid, @PathVariable Long accountid) {
        User user = userService.findById(userid);
        Account account = accountService.findById(accountid);


        model.put("userid", userid);
        model.put("user", user);
        model.put("account", account);
        return "useraccount";
    }

    @PostMapping("users/{userid}/useraccount")
    public String saveAccount(@PathVariable Long userid) {
        User user = userService.findById(userid);
        userService.createAccount(userid);
        //account = accountService.saveAccount(account);

        return "redirect:/users/" + userid;
    }

    //@RequestMapping(value = "/users/{userid}/useraccount/{accountid}", method = RequestMethod.POST)
    @PostMapping("/users/{userid}/useraccount/{accountid}")
    public String updateAccount(ModelMap model, @PathVariable Long userid, @PathVariable Long accountid,
                                @ModelAttribute Account account) {
        Account existingAccount = accountService.findById(accountid);
        if (existingAccount != null) {
            existingAccount.setAccountName(account.getAccountName());
        }
        User user = userService.findById(userid);

        accountService.saveAccount(existingAccount);

        return "redirect:/users/"+user.getUserid();

    }
}
