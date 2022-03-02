package com.anabneri.registrationschool.api;

import com.anabneri.registrationschool.controller.StudentController;
import com.anabneri.registrationschool.exception.BusinessException;
import com.anabneri.registrationschool.model.StudentDTO;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {StudentController.class})
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

        BDDMockito.given(studentService.save(any(Student.class))).willReturn(savedStudent);


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
                .andExpect(jsonPath("dateOfRegistration").value(studentDTOBuilder.getDateOfRegistration()));
//                .andExpect(jsonPath("registration").value(studentDTOBuilder.getStudentRegistration()));


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
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("errors", hasSize(3)));

    }

    @Test
    @DisplayName("Should throw an exception when try to create a new student with an registration already created.")
    public void createStudentWithDuplicatedRegistration() throws Exception {

        StudentDTO dto = createNewStudent();
        String json  = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(studentService.save(any(Student.class))).willThrow(new BusinessException("Registration already created!"));

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
                .registration(createNewStudent().getStudentRegistration()).build();

        BDDMockito.given(studentService.getByStudentId(studentId)).willReturn(Optional.of(student));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(STUDENT_API.concat("/" + studentId))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("studentId").value(studentId))
                .andExpect(jsonPath("studentName").value(createNewStudent().getStudentName()))
                .andExpect(jsonPath("dateOfRegistration").value(createNewStudent().getDateOfRegistration()));
//                .andExpect(jsonPath("registration").value(createNewStudent().getStudentRegistration()));


    }

    @Test
    @DisplayName("Should return not found when the student doesn't exists")
    public void studentNotFoundTest() throws Exception {

        BDDMockito.given(studentService.getByStudentId(anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(STUDENT_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }



    @Test
    @DisplayName("Should delete the student")
    public void deleteStudentTest() throws Exception {

        BDDMockito.given(studentService.getByStudentId(anyInt())).willReturn(Optional.of(Student.builder().studentId(11).build()));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(STUDENT_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

    }


    @Test
    @DisplayName("Should return resource not found when no student is found to delete.")
    public void deleteNonexistentStudentTest() throws Exception {

        BDDMockito.given(studentService.getByStudentId(anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(STUDENT_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());

    }


    @Test
    @DisplayName("Should update an student info")
    public void updateStudentTest() throws Exception {

        Integer studentId = 11;
        String json = new ObjectMapper().writeValueAsString(createNewStudent());


        Student updatingStudent = Student.builder().studentId(11).studentName("Erick Wendel").dateOfRegistration("10/01/2022").registration("323").build();
        BDDMockito.given(studentService.getByStudentId(anyInt()))
                .willReturn(Optional.of(updatingStudent));

        Student updatedStudent = Student.builder().studentId(studentId).studentName("Ana Neri").dateOfRegistration("10/10/2021").registration("323").build();
        BDDMockito.given(studentService.update(updatingStudent)).willReturn(updatedStudent);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(STUDENT_API.concat("/" + 1))
                .contentType(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("studentId").value(studentId))
                .andExpect(jsonPath("studentName").value(createNewStudent().getStudentName()))
                .andExpect(jsonPath("dateOfRegistration").value(createNewStudent().getDateOfRegistration()))
                .andExpect(jsonPath("registration").value("323"));


    }

    @Test
    @DisplayName("Should return 404 when try to update an student no existent")
    public void updateNoExistentStudentTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(createNewStudent());
        BDDMockito.given(studentService.getByStudentId(anyInt()))
                .willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(STUDENT_API.concat("/" + 1))
                .contentType(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should filter students")
    public void findStudentsTest() throws Exception {

        Integer studentId = 11;

        Student student = Student.builder()
                .studentId(studentId)
                .studentName(createNewStudent().getStudentName())
                .dateOfRegistration(createNewStudent().getDateOfRegistration())
                .registration(createNewStudent().getStudentRegistration()).build();

        // nao interessa o teste o q importa é o retorno
        BDDMockito.given(studentService.find(Mockito.any(Student.class), Mockito.any(Pageable.class)) )
                // deve retornar uma paginacao e nao uma lista
                .willReturn(new PageImpl<Student>(Arrays.asList(student), PageRequest.of(0,100), 1));

        // pesquisa numa api rest
        // ? significa que irei passar parametros /api/students?
        // &  - serve pra separar os parametros
        String queryString = String.format("?studentName=%s&dateOfRegistration=%s&page=0&size=100",
                student.getStudentName(), student.getDateOfRegistration());


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(STUDENT_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", Matchers.hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(100))
                .andExpect(jsonPath("pageable.pageNumber").value(0));
    }

    private StudentDTO createNewStudent() {
        return StudentDTO.builder().studentName("Ana Neri").dateOfRegistration("10/10/2021").studentRegistration("001").build();
    }



}
