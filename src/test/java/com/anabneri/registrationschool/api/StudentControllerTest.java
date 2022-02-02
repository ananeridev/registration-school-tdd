package com.anabneri.registrationschool.api;

import com.anabneri.registrationschool.exception.BusinessException;
import com.anabneri.registrationschool.model.StudentDTO;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    static String STUDENT_API = "/api/student";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    @DisplayName("Should create a student registration with success.")
    public void createStudentTest() throws Exception {

        StudentDTO studentDTOBuilder = createNewStudent();
        Student savedStudent = Student.builder().studentId(101).studentName("Ana Neri").dateOfRegistration("10/10/2021").registration("001").build();

        BDDMockito.given(studentService.save(Mockito.any(Student.class))).willReturn(savedStudent);


        String json  = new ObjectMapper().writeValueAsString(studentDTOBuilder);

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post(STUDENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("studentId").value(101))
                .andExpect(jsonPath("studentName").value(studentDTOBuilder.getStudentName()))
                .andExpect(jsonPath("dateOfRegistration").value(studentDTOBuilder.getDateOfRegistration()))
                .andExpect(jsonPath("registration").value(studentDTOBuilder.getRegistration()));


    }

    @Test
    @DisplayName("Should throw an exception when not have date enough for the test.")
    public void createInvalidStudentTest() throws Exception {

        String json  = new ObjectMapper().writeValueAsString(new StudentDTO());

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post(STUDENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));

    }

    @Test
    @DisplayName("Should throw an exception when try to create a new student with an registration already created.")
    public void createStudentWithDuplicatedRegistration() throws Exception {

        StudentDTO dto = createNewStudent();
        String json  = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(studentService.save(Mockito.any(Student.class))).willThrow(new BusinessException("Registration already created!"));

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post(STUDENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Registration already created!"));
    }

    @Test
    @DisplayName("Should get student informations")
    public void getStudentTest() throws Exception {

        Integer studentId = 11;

        Student student = Student.builder()
                .studentId(studentId)
                .studentName(createNewStudent().getStudentName())
                .dateOfRegistration(createNewStudent().getDateOfRegistration())
                .registration(createNewStudent().getRegistration()).build();

        BDDMockito.given(studentService.getByStudentId(studentId)).willReturn(Optional.of(student));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(STUDENT_API.concat("/" + studentId))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("studentId").value(studentId))
                .andExpect(jsonPath("studentName").value(createNewStudent().getStudentName()))
                .andExpect(jsonPath("dateOfRegistration").value(createNewStudent().getDateOfRegistration()))
                .andExpect(jsonPath("registration").value(createNewStudent().getRegistration()));


    }

    private StudentDTO createNewStudent() {
        return StudentDTO.builder().studentName("Ana Neri").dateOfRegistration("10/10/2021").registration("001").build();
    }



}
