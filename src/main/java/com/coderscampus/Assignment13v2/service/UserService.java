package com.coderscampus.Assignment13v2.service;

import com.coderscampus.Assignment13v2.domain.Account;
import com.coderscampus.Assignment13v2.domain.Address;
import com.coderscampus.Assignment13v2.repository.AccountRepository;
import com.coderscampus.Assignment13v2.repository.AddressRepository;
import com.coderscampus.Assignment13v2.repository.UserRepository;
import com.coderscampus.Assignment13v2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private AddressRepository addressRepo;

    public List<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }


    public List<User> findByNameAndUsername(String name, String username) {
        return userRepo.findByNameAndUsername(name, username);
    }

    public  List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
        return userRepo.findByCreatedDateBetween(date1, date2);
    }

    public User findExactlyOneUserByUsername(String username) {
        List<User> users = userRepo.findExactlyOneUserByUsername(username);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return new User();
        }
    }

    public Set<User> findAllUsersWithAccountsAndAddress() {
        return userRepo.findAllUsersWithAccountsAndAddress();

    }
    public User findById(Long userid) {
        Optional<User> userOpt = userRepo.findById(userid);
        return userOpt.orElse(new User());
    }

    public Account createAccount(Long userid) {
        User user = findById(userid);

        if (user != null) {
            Account newaccount = new Account();
            newaccount.getUsers().add(user);
            newaccount.setAccountName("Account #" + user.getAccounts().size());

            user.getAccounts().add(newaccount);
            userRepo.save(user);
            accountRepo.save(newaccount);

            return newaccount;
        }
        return null;
    }

    public User saveUser(User user) {
        //user.getAddress().setUserId(user.getUserid());
        User newUser = new User();
        user.setUserid(newUser.getUserid());
        user.getAddress().setUser(user);
        if (user.getUserid() == null) {
            Account checking = new Account();
            checking.setAccountName("Checking Account");
            checking.getUsers().add(user);
            Account savings = new Account();
            savings.setAccountName("Savings Account");
            savings.getUsers().add(user);

//            Address address = new Address();
//            address.setAddress_line1(user.getAddress().getAddress_line1());
//            address.setAddress_line2(user.getAddress().getAddress_line2());
//            address.setCity(user.getAddress().getCity());
//            address.setCountry(user.getAddress().getCountry());
//            address.setRegion(user.getAddress().getRegion());

            // Set the address property on the User object
//            user.setAddress(user.getAddress());
//            addressRepo.save(address);



            user.getAccounts().add(checking);
            user.getAccounts().add(savings);
            accountRepo.save(checking);
            accountRepo.save(savings);
        }

        return userRepo.save(user);
    }


    public void delete(Long userid) {
        userRepo.deleteById(userid);
    }

    public User findByIdWithAccounts(Long userid) {
        Optional<User> userOpt = userRepo.findByIdWithAccounts(userid);
        return userOpt.orElse(new User());
    }
}
