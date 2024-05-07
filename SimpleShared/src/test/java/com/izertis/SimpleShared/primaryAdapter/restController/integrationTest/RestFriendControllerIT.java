package com.izertis.SimpleShared.primaryAdapter.restController.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestFriendControllerIT {

    private final MockMvc mvc;

    @Autowired
    public RestFriendControllerIT(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    public void shouldGetAllFriendsAndReturnStatus200() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/rest/friend")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].idFriend").exists())
                .andExpect(jsonPath("$[*].name").exists());

    }

    @Test
    public void shouldFindFriendByIdAndReturnStatus200() throws Exception {
        long id = 1;

        mvc.perform(MockMvcRequestBuilders
                .get("/rest/friend/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFriend").exists())
                .andExpect(jsonPath("$.name").exists());
    }


    @Test
    public void shouldReturnFriendNotFoundExceptionWhenFriendNotFound() throws Exception {
        long nonExistentId = 90;

        mvc.perform(MockMvcRequestBuilders
                        .get("/rest/friend/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldSaveNewFriendAndReturnStatus200() throws Exception {
       String newFriend = "{\"name\": \"Test\"}";

       mvc.perform(MockMvcRequestBuilders
               .post("/rest/friend")
               .contentType(MediaType.APPLICATION_JSON)
               .content(newFriend))
               .andExpect(status().isOk());

    }

    @Test
    public void shouldUpdateFriendAndReturnStatus200() throws Exception {
        long id = 1;
        String updatedname = "UpdateTest";

        mvc.perform(MockMvcRequestBuilders
                .put("/rest/friend/{id}?name={name}", id, updatedname))
                .andExpect(status().isOk());
    }

}
