package com.training.accountservice.integration;

import com.training.accountservice.domain.AccountType;
import com.training.accountservice.dto.CreateAccountRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccountIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void shouldSaveAccountToDB(){
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/accounts",
                HttpMethod.POST,
                new HttpEntity<>(CreateAccountRequestDto.builder().customerId("CUST12345").accountType(AccountType.SAVINGS).build(),null),
                Void.class
                );
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders()).containsKey("Location");
    }
}
