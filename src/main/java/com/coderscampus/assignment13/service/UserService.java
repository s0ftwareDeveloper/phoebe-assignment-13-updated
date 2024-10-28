package com.coderscampus.assignment13.service;


import java.util.Optional;
import java.util.Set;
import com.coderscampus.assignment13.domain.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepo;
    private final AccountService accountService;
    private final AddressService addressService;

    public Set<User> findAll() {
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    public User findById(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.orElse(null);
    }

    public User save(User user) {
        if (user.getUserId() == null) {
            //accounts
            Account checking = new Account();
            checking.setAccountName("Checking Account");
            checking.getUsers().add(user);
            Account savings = new Account();
            savings.setAccountName("Savings Account");
            savings.getUsers().add(user);
            user.getAccounts().add(checking);
            user.getAccounts().add(savings);
            accountService.save(checking);
            accountService.save(savings);

            Address address = new Address();
            address.setUser(user);
            user.setAddress(address);
            addressService.save(address);
        } else {
            User originalUser = findById(user.getUserId());
            Address address = user.getAddress();
            address.setUser(user);
            address.setUserId(user.getUserId());
            addressService.save(address);

            originalUser.setUsername(user.getUsername());
            originalUser.setName(user.getName());
            if (!user.getPassword().isEmpty()) {
                originalUser.setPassword(user.getPassword());
            }
            return userRepo.save(originalUser);
        }

        return userRepo.save(user);
    }

    public void delete(Long userId) {
        addressService.deleteById(userId);
        userRepo.deleteById(userId);
    }

}