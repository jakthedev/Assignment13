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
//@RestController("/users/{userid}/useraccount/{accountId}")
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
        //model.put("registerNewUserAccount", )
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

        //List<Account> la = Stream.of(users).reduce((a, b) -> b).get();

//        Account lastAcc = users.stream().filter(c -> Boolean.parseBoolean(c.getAccountName()))
//                .reduce((first, second) -> second).get();
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
        //User user = userService.findById(userid);
        //account.setAccountId(userid);
        //Account thisaccount = user.getAccounts().stream().findFirst().get();
        //account.setAccountName(account.getAccountName());
        //account = accountService.saveAccount(account);
        return "useraccount";
        //return "redirect:/users/"+userid+"/accounts/"+account.getAccountId();
    }

    @PostMapping("users/{userid}/useraccount")
    public String saveAccount(@PathVariable Long userid) {
        User user = userService.findById(userid);
        userService.createAccount(userid);
        //account = accountService.saveAccount(account);

        return "redirect:/users/" + userid;
    }

    @PostMapping("/users/{userid}/useraccount/{accountid}")
    public String updateAccount(ModelMap model, @PathVariable Long userid, @PathVariable Long accountid,
                             @ModelAttribute Account account) {
        Account existingAccount = accountService.findById(accountid);
        existingAccount.setAccountName(account.getAccountName());
        accountService.saveAccount(existingAccount);

        return "redirect:/users/" + userid;

//        if (existingAccount != null) {
//            existingAccount.setAccountName(account.getAccountName());
//
//            accountService.saveAccount(existingAccount);
//
//            return "redirect:/users/" + userid + "/useraccount/" + existingAccount.getAccountId();
//        }
//        User user = userService.findById(userid);
//        user.setUserid(userid); //.setAccounts(userid);
//        for(Account userAccount : user.getAccounts()) {
//            if (userAccount.getAccountId() == userid) {
//                account.setAccountId(userid);
//            }
        //return "redirect:/error.html";

    }

//        model.put("useraccount", account);
//        model.put("users", user);
//        return "useraccount";
//    }

//    @PostMapping("/users/{userid}/useraccount/{accountId}")
//    public String getUserAccount(ModelMap model, @PathVariable Long userid, @PathVariable Long account) {
//        model.put("user", this.userService.findById(userid));
//        model.put("useraccount", accountService.findById(account));
//        return "useraccount";
//
//    }
}
