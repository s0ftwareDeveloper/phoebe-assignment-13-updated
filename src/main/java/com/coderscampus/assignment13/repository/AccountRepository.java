package com.coderscampus.assignment13.repository;

import com.coderscampus.assignment13.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.assignment13.domain.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // select * from accounts where accountId = :accountId
    Account findByAccountId(Long accountId);
}