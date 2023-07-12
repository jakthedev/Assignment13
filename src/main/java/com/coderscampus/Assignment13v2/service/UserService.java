package com.coderscampus.Assignment13v2.service;

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

    public User saveUser(User user) {
        return userRepo.save(user);
    }


    public void delete(Long userid) {
        userRepo.deleteById(userid);
    }
}
