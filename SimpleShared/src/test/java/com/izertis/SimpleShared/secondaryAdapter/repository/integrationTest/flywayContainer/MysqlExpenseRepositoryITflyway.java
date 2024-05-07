package com.izertis.SimpleShared.secondaryAdapter.repository.integrationTest.flywayContainer;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;
import com.izertis.SimpleShared.secondaryAdapter.repository.ExpenseRepository;
import io.restassured.RestAssured;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestPropertySource("/application-test.properties")
public class MysqlExpenseRepositoryITflyway {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public MysqlExpenseRepositoryITflyway(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("simple_shared")
            .withUsername("root")
            .withPassword("root")
            .withExposedPorts(3306);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void doBeforeAll(@Autowired Flyway flyway, @LocalServerPort int port) {
        RestAssured.baseURI = "http://localhost:" + port;
        flyway.migrate();
    }

    @Test
    @Order(1)
    public void shouldFindAllExpenses() throws EmptyExpenseListException {
        // WHEN
        List<Expense> expenses = expenseRepository.findExpenses();

        // THEN
        assertThat(expenses.size()).isEqualTo(9);
    }

    @Test
    @Order(2)
    public void shouldSaveNewExpense() throws NegativeExpenseAmountException, EmptyExpenseListException {
        // GIVEN
        long id = 8000;
        Friend friend = new Friend(3, "Pedro");
        Expense expected = new Expense(id, friend, 20.00, "Test", "2024-04-10 12:30:00");

        // WHEN
        expenseRepository.addExpense(expected);
        List<Expense> allExpenses = expenseRepository.findExpenses();

        // THEN
        assertTrue(allExpenses.contains(expected));

    }
}
