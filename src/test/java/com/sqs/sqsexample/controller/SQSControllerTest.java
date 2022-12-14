package com.sqs.sqsexample.controller;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SQSControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @RepeatedTest(4)
    void whenGetBackendAThenFirstCall200AndThreeCalls429(RepetitionInfo repetitionInfo) throws Exception {
        ResultMatcher result = repetitionInfo.getCurrentRepetition() == 1 ? status().isOk() :
                status().isTooManyRequests();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/send")
                        .content("Test Message"))
                .andExpect(result);
    }
}