package com.izertis.SimpleShared.primaryAdapter.restController.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RestExpenseControllerIT {

    private final MockMvc mvc;

    @Autowired
    public RestExpenseControllerIT(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    public void shouldGetAllExpensesAndReturnStatus200() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/rest/expense")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].idExpense").exists())
                .andExpect(jsonPath("$[*].idFriend").exists())
                .andExpect(jsonPath("$[*].amount").exists())
                .andExpect(jsonPath("$[*].description").exists())
                .andExpect(jsonPath("$[*].expenseDate").exists());
    }

    @Test
    public void shouldAddNewExpenseAndReturnStatus200() throws Exception {
        long idFriend = 1;
        String newExpense = "{\"amount\": 20.00, \"description\": \"New Expense Test\"}";

        mvc.perform(MockMvcRequestBuilders
                .post("/rest/expense/friend/{id}", idFriend)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newExpense))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnFriendNotFoundExceptionWhenAddingNewExpenseForFriend() throws Exception {
        long nonExistentId = 90;
        String newExpense = "{\"amount\": 20.00, \"description\": \"New Expense Test\"}";

        mvc.perform(MockMvcRequestBuilders
                        .post("/rest/expense/friend/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newExpense))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNegativeExpenseAmountExceptionWhenAmountIsNegative() throws Exception {
        long nonExistentId = 1;
        String newExpense = "{\"amount\": -20.00, \"description\": \"New Expense Test\"}";

        mvc.perform(MockMvcRequestBuilders
                        .post("/rest/expense/friend/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newExpense))
                .andExpect(status().isBadRequest());
    }

}
