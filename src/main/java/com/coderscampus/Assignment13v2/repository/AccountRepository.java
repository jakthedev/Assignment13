package com.coderscampus.Assignment13v2.repository;

import com.coderscampus.Assignment13v2.domain.Account;
import com.coderscampus.Assignment13v2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByAccountId(Long id);

}
