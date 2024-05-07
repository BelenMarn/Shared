package com.izertis.SimpleShared.secondaryAdapter.repository.integrationTest.flywayContainer;

import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.secondaryAdapter.repository.BalanceRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestPropertySource("/application-test.properties")
public class MysqlBalanceRepositoryITflyway {

    private final BalanceRepository balanceRepository;

    @Autowired
    public MysqlBalanceRepositoryITflyway(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
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
    public void shouldReturnNumberOfFriends() throws EmptyExpenseListException {
        // GIVEN
        int expected = 4;

        // WHEN
        int response = balanceRepository.getNumberOfFriends();

        // THEN
        assertThat(response).isEqualTo(expected);
    }

    @Test
    @Order(2)
    public void shouldReturnAverageSpending() throws EmptyExpenseListException {
        // GIVEN
        double expected = 35.00;
        // WHEN
        double response = balanceRepository.getAverageSpending();
        // THEN

        assertThat(response).isGreaterThan(expected);
    }

    @Test
    @Order(3)
    public void shouldReturnBalanceOfFriend(){
        // GIVEN
        int id = 4;
        double expected = 60.00;

        // WHEN
        double response = balanceRepository.getBalanceOfFriend(id);

        // THEN
        assertThat(response).isEqualTo(expected);
    }
}
