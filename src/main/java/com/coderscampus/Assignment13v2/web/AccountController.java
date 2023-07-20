package com.coderscampus.Assignment13v2.web;

import com.coderscampus.Assignment13v2.domain.Account;
import com.coderscampus.Assignment13v2.domain.User;
import com.coderscampus.Assignment13v2.service.AccountService;
import com.coderscampus.Assignment13v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/users/{userid}/userBankAccount")
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @PostMapping("")
    public String createAccount (@PathVariable Long userid) {
        Account account = new Account();
        User user = userService.findById(userid);
        account.getUsers().add(user);
        user.getAccounts().add(account);
        account.setAccountName("Account #" + user.getAccounts().size());
        account = accountService.saveAccount(account);

        return "redirect:/users/"+userid+"/accounts/"+account.getAccountId();
    }

    @PostMapping("{accountId}")
    public String saveAccount(Account account, @PathVariable Long userid) {
        account = accountService.saveAccount(account);
        return "redirect:/users/"+userid+"/accounts/"+account.getAccountId();
    }

    @GetMapping("/users/{userid}/userBankAccount/{accountId}")
    public String getAccount(ModelMap model, @PathVariable Long userid, @PathVariable Long accountId) {
        Account account = accountService.findById(accountId);
        User user = userService.findById(userid);
        model.put("userBankAccount", account);
        model.put("users", user);
        return "userBankAccount";
    }
}
