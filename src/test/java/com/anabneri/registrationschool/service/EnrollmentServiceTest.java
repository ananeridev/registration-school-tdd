package com.anabneri.registrationschool.service;

import com.anabneri.registrationschool.model.entity.Enrollment;
import com.anabneri.registrationschool.repository.EnrollmentRepository;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EnrollmentServiceTest {

    EnrollmentService enrollmentService;

    @MockBean
    EnrollmentRepository repository;

    @BeforeEach
    public void setUp() {
        this.enrollmentService = new EnrollmentServiceImpl(repository);
    }

    @Test
    @DisplayName("Should save an enrollment")
    public void saveEnrollmentTest() {

        Student student = Student.builder()
                .studentId(11)
//                .studentName("Ana")
//                .dateOfRegistration("21/12/2021")
//                .registration("123")
                .build();

        Enrollment savingEnrollment = Enrollment.builder()
                .student(student)
                .course("Database")
                .enrollmentDate(LocalDate.now())
                .build();


        Enrollment savedEnrollment = Enrollment.builder()
                .id(11)
                .enrollmentDate(LocalDate.now())
                .course("Database")
                .student(student)
                .build();

        Mockito.when(repository.save(savingEnrollment)).thenReturn(savedEnrollment);

        Enrollment enrollment = enrollmentService.save(savingEnrollment);

        assertThat(enrollment.getId()).isEqualTo(savedEnrollment.getId());
        assertThat(enrollment.getCourse()).isEqualTo(savedEnrollment.getCourse());
//        assertThat(enrollment.getStudent()).isEqualTo(savedEnrollment.getStudent().getStudentId());
        assertThat(enrollment.getEnrollmentDate()).isEqualTo(savedEnrollment.getEnrollmentDate());
    }
}
