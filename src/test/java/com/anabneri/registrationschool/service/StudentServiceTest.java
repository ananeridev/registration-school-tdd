package com.anabneri.registrationschool.service;


import com.anabneri.registrationschool.exception.BusinessException;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.repository.StudentRepository;
import com.anabneri.registrationschool.service.impl.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StudentServiceTest {

    StudentService studentService;

    @MockBean
    StudentRepository repository;

    @BeforeEach
    public void setUp() {
        this.studentService = new StudentServiceImpl(repository);
    }


    @Test
    @DisplayName("Should save an student")
    public void saveStudent() {

        Student student = createValidStudent();

        Mockito.when(repository.existsByRegistration(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(student)).thenReturn(createValidStudent());

       Student savedRegistrationStudent = studentService.save(student);

       assertThat(savedRegistrationStudent.getStudentId()).isEqualTo(101);
       assertThat(savedRegistrationStudent.getStudentName()).isEqualTo("Ana Neri");
       assertThat(savedRegistrationStudent.getDateOfRegistration()).isEqualTo("10/10/2021");
        assertThat(savedRegistrationStudent.getRegistration()).isEqualTo("001");


    }

    private Student createValidStudent() {
        return Student.builder()
                .studentId(101)
                .studentName("Ana Neri")
                .dateOfRegistration("10/10/2021")
                .registration("001")
                .build();
    }


    @Test
    @DisplayName("Should throw business error when try to save a new student with a registration duplicated")
        public void shouldNotSaveAStudentWithRegistrationDuplicated() {

        Student student = createValidStudent();
        Mockito.when(repository.existsByRegistration(Mockito.anyString())).thenReturn(true);

       Throwable exception =   Assertions.catchThrowable( () -> studentService.save(student));
       assertThat(exception)
               .isInstanceOf(BusinessException.class)
               .hasMessage("Registration already created!");

       Mockito.verify(repository, Mockito.never()).save(student);

    }

    @Test
    @DisplayName("Should get an student by studentId")
    public void getByStudentIdTest() {
        Integer studentId = 11;
        Student student = createValidStudent();
        student.setStudentId(studentId);
        Mockito.when(repository.findById(studentId)).thenReturn(Optional.of(student));


        Optional<Student> foundStudent = studentService.getByStudentId(studentId);

        assertThat(foundStudent.isPresent()).isTrue();
        assertThat(foundStudent.get().getStudentId()).isEqualTo(studentId);
        assertThat(foundStudent.get().getStudentName()).isEqualTo(student.getStudentName());
        assertThat(foundStudent.get().getDateOfRegistration()).isEqualTo(student.getDateOfRegistration());
        assertThat(foundStudent.get().getRegistration()).isEqualTo(student.getRegistration());

    }


    @Test
    @DisplayName("Should return empty when get an student by studentId when doesn't exists.")
    public void studentNotFoundByIdTest() {
        Integer studentId = 11;

        Mockito.when(repository.findById(studentId)).thenReturn(Optional.empty());


        Optional<Student> student = studentService.getByStudentId(studentId);

        assertThat(student.isPresent()).isFalse();

    }

    @Test
    @DisplayName("Should delete an student")
    public void deleteStudentTest() {
        Student student = Student.builder().studentId(11).build();

       assertDoesNotThrow(() -> studentService.delete(student));

        Mockito.verify(repository, Mockito.times(1)).delete(student);
    }


    @Test
    @DisplayName("Should throw error when try to delete an student no existent")
    public void deleteInvalidStudentTest() {
        Student student = new Student();

        assertThrows(IllegalArgumentException.class, () -> studentService.delete(student) );

        Mockito.verify(repository, Mockito.never()).delete(student);
    }


    @Test
    @DisplayName("Should throw error when try to update an student no existent")
    public void updateInvalidStudentTest() {
        Student student = new Student();

        assertThrows(IllegalArgumentException.class, () -> studentService.update(student) );

        Mockito.verify(repository, Mockito.never()).save(student);
    }

    @Test
    @DisplayName("Should update an student")
    public void updateStudentTest() {

        Integer studentId = 11;
        Student updatingStudent = Student.builder().studentId(studentId).build();

        Student updatedStudent = createValidStudent();
        updatedStudent.setStudentId(studentId);

        Mockito.when(repository.save(updatingStudent)).thenReturn(updatedStudent);

        Student student =  studentService.update(updatingStudent);

       assertThat(student.getStudentId()).isEqualTo(updatedStudent.getStudentId());
       assertThat(student.getStudentName()).isEqualTo(updatedStudent.getStudentName());
       assertThat(student.getDateOfRegistration()).isEqualTo(updatedStudent.getDateOfRegistration());
       assertThat(student.getRegistration()).isEqualTo(updatedStudent.getRegistration());

    }

    @Test
    @DisplayName("Should filter students must by properties")
    public void findStudentTest() {

        // scenario
        Student student = createValidStudent();
        PageRequest pageRequest = PageRequest.of(0,10);

        List<Student> listStudents = Arrays.asList(student);
        Page<Student> page  = new PageImpl<Student>(Arrays.asList(student), PageRequest.of(0,10), 1);

        // Example class é do spring data
        Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);


        // execute

        // o objeto serve pra comparar com o que esta na base de dados
        Page<Student> result =  studentService.find(student, pageRequest);


        // verify
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(listStudents);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should get a student by registration")
    public void getStudentByRegistration() {
        String registration = "1230";

        Mockito.when(repository.findByRegistration(registration)).thenReturn(Optional.of(Student.builder().studentId(11).registration(registration).build()) );


        Optional<Student> student =  studentService.getStudentByRegistration(registration);

        assertThat(student.isPresent()).isTrue();
        assertThat(student.get().getStudentId()).isEqualTo(11);
        assertThat(student.get().getRegistration()).isEqualTo(registration);

        Mockito.verify(repository, Mockito.times(1)).findByRegistration(registration);
    }
}
