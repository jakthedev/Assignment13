package com.coderscampus.Assignment13v2.repository;

import com.coderscampus.Assignment13v2.domain.Address;
import com.coderscampus.Assignment13v2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {


}
