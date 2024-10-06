package com.coderscampus.assignment13.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {
        model.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/register";
    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        Set<User> users = userService.findAll();
        model.put("users", users);
        if (users.size() == 1) {
            Object user = model.put("user", users.iterator()
                                                 .next());
        }
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        model.put("users", Arrays.asList(user));
        model.put("user", user);
        return "users";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(User user, @PathVariable Long userId) {
        user.setAccounts(userService.findById(userId)
                                    .getAccounts());
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}/accounts")
    public String getNewBankAccount(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        Account account = new Account();
        account.setAccountName("New Account");
        model.put("account", account);
        model.put("user", user);
        return "account";
    }

    @PostMapping("/users/{userId}/accounts")
    public String postNewBankAccount(Account account, @PathVariable Long userId) {
        User user = userService.findById(userId);
        account.getUsers()
               .add(user);
        user.getAccounts()
            .add(account);
        accountService.saveAccount(account);
        userService.saveUser(user);
        return "redirect:/users/" + userId;
    }

    @GetMapping("/users/{userId}/accounts/{accountId}")
    public String getOneAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
        User user = userService.findById(userId);
        Account account = accountService.findByAccountId(accountId);
        model.put("account", account);
        model.put("user", user);
        return "account";
    }

    @PostMapping("/users/{userId}/accounts/{accountId}")
    public String postOneAccount(Account account, @PathVariable Long userId) {
        accountService.saveAccount(account);
        return "redirect:/users/" + userId;
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }
}
