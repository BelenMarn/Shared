package com.izertis.SimpleShared.primaryAdapter.restController.integrationTest;

import com.izertis.SimpleShared.SimpleSharedApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestBalanceControllerIT {

    private final MockMvc mvc;

    @Autowired
    public RestBalanceControllerIT(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    public void shouldGetAllBalanceAndReturnStatus200() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/rest/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].name").exists())
                .andExpect(jsonPath("$[*].balance").exists());

    }


}
