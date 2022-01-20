package com.training.accountservice.component;

import com.training.accountservice.domain.AccountType;
import com.training.accountservice.dto.CreateAccountRequestDto;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
,properties = {"spring.jpa.generate-ddl=false","spring.jpa.show-sql=true","spring.flyway.enabled=true"})
public class AccountIntegrationTest {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres")
            .withUsername("accounts")
            .withPassword("password")
            ;

    @Autowired
    TestRestTemplate testRestTemplate;

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
        registry.add("spring.jpa.database-platform", PostgreSQL9Dialect.class::getName);
    }

    @Test
    void shouldSaveAccountToDB(){
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/accounts",
                HttpMethod.POST,
                new HttpEntity<>(CreateAccountRequestDto.builder().customerId("CUST12345").accountType(AccountType.SAVINGS).build(),null),
                Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        System.out.println(responseEntity.getHeaders().get("Location"));
        assertThat(responseEntity.getHeaders()).containsKey("Location");
    }
}
