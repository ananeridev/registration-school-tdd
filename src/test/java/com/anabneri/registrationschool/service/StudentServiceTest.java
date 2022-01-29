package com.anabneri.registrationschool.service;


import com.anabneri.registrationschool.model.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StudentServiceTest {

    StudentService studentService;


    @Test
    @DisplayName("Should save an student")
    public void saveStudent() {

        Student student = Student.builder()
                .studentId(101)
                .studentName("Ana Neri")
                .dateOfRegistration("10/10/2021")
                .build();

       Student savedRegistrationStudent = studentService.save(student);

    }

}
