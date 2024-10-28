package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepo;

    public Account findByAccountId(Long accountId) {
        return accountRepo.findByAccountId(accountId);
    }

    public Account save(Account account) {
        return accountRepo.save(account);
    }
}