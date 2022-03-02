package com.anabneri.registrationschool.api;

import com.anabneri.registrationschool.controller.EnrollmentController;
import com.anabneri.registrationschool.model.EnrollmentDTO;
import com.anabneri.registrationschool.model.entity.Enrollment;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.EnrollmentService;
import com.anabneri.registrationschool.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {EnrollmentController.class})
@AutoConfigureMockMvc
public class EnrollmentControllerTest {

    static final String ENROLLMENT_API = "/api/enrollments";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    @DisplayName("Should register")
    public void createEnrollmentTest() throws Exception{

        // vai pegar na base de dados o student registration e salvar tb o course
        EnrollmentDTO dto = EnrollmentDTO.builder().studentRegistration("123").course("Database").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        Student student = Student.builder().studentId(11).studentName("Ana Neri").registration("123").build();

        BDDMockito.given(studentService.getStudentByRegistration("123"))
                .willReturn(Optional.of(student));

        Enrollment enrollment = Enrollment.builder().id(11).course("Database").student(student).enrollmentDate(LocalDate.now()).build();

        BDDMockito.given(enrollmentService.save(Mockito.any(Enrollment.class))).willReturn(enrollment);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(ENROLLMENT_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("studentId").value(11));
    }

}
