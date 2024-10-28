package com.coderscampus.assignment13.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Address {
    @Id
    private Long userId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    @Column(length = 200)
    private String addressLine1;
    @Column(length = 200)
    private String addressLine2;
    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private String region;
    @Column(length = 100)
    private String country;
    @Column(length = 15)
    private String zipCode;
}