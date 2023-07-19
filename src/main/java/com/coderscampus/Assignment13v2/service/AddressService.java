package com.coderscampus.Assignment13v2.service;

import com.coderscampus.Assignment13v2.domain.Address;
import com.coderscampus.Assignment13v2.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService  {

    @Autowired
    private AddressRepository addressRepo;

    public Address save(Address addy) {
        return addressRepo.save(addy);
    }

}
