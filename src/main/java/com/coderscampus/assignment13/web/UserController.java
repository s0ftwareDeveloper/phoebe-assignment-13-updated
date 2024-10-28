package com.coderscampus.assignment13.web;

import java.util.Set;
import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {
        model.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        System.out.println(user);
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        Set<User> users = userService.findAll();
        model.put("users", users);
        if (users.size() == 1) {
            model.put("user", users.iterator().next());
        }
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        if(user == null)
        {
            return "error";
        }
        //model.put("users", Arrays.asList(user));
        model.put("user", user);
        return "user";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(User user, @PathVariable Long userId) {
        user.setAccounts(userService.findById(userId).getAccounts());
        userService.save(user);
        return "redirect:/users/" + userId;
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
        account.getUsers().add(user);
        user.getAccounts().add(account);
        accountService.save(account);
        userService.save(user);
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
        accountService.save(account);
        return "redirect:/users/" + userId;
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }
}