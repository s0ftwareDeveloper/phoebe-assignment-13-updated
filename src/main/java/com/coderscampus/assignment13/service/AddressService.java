package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AddressService {

    private AddressRepository addressRepo;

    public Address save(Address address) {

        return addressRepo.save(address);
    }

    public void deleteById(Long id) {
        addressRepo.deleteById(id);
    }
}