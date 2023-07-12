package com.coderscampus.Assignment13v2.repository;

import com.coderscampus.Assignment13v2.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
