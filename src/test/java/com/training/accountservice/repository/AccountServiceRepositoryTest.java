package com.training.accountservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class AccountServiceRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    AccountRepository accountRepository;

}
