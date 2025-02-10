package com.example.repository;

import java.lang.StackWalker.Option;
import java.nio.file.OpenOption;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    Account findByUsernameAndPassword(String username, String password);
    Account findByUsername(String username);
    Account findByPassword(String password);
    Account findById(int id);
}
