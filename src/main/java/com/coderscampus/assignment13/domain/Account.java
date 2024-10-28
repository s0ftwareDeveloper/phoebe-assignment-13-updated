package com.coderscampus.assignment13.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(length = 100)
    private String accountName;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();
    @ManyToMany(mappedBy = "accounts")
    private List<User> users = new ArrayList<>();
}