package com.training.accountservice.repository;

import com.training.accountservice.domin.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,String> {

}
