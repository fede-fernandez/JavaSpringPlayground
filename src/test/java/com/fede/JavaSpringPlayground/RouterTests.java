package com.fede.JavaSpringPlayground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RouterTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ping() throws Exception {
        mockMvc.perform(get("/fede/api/v1/ping"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }

    @Test
    public void pingWithInvalidMethod() throws Exception {
        mockMvc.perform(post("/api/v1/fede/ping"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }
}
