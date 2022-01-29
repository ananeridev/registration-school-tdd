package com.anabneri.registrationschool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    static String STUDENT_API = "/api/student";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should create a student registration with success.")
    public void createStudentTest() throws Exception {

        String json  = new ObjectMapper().writeValueAsString(null);

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post(STUDENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("studentId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("studentName").value("Ana Neri"))
                .andExpect(MockMvcResultMatchers.jsonPath("dateOfRegistration").isNotEmpty());

    }


    @Test
    @DisplayName("Should throw an exception when not have date enough for the test.")
    public void createInvalidStudentTest() {

    }
}
