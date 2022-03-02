package com.anabneri.registrationschool.controller;

import com.anabneri.registrationschool.model.EnrollmentDTO;
import com.anabneri.registrationschool.model.entity.Enrollment;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.EnrollmentService;
import com.anabneri.registrationschool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody EnrollmentDTO dto) {

        Student student = studentService.getStudentByRegistration(dto.getStudentRegistration()).get();
        Enrollment enrollmentEntity = Enrollment.builder()
                                        .course(dto.getCourse())
                                        .student(student)
                                        .enrollmentDate(LocalDate.now()).build();

        enrollmentEntity = enrollmentService.save(enrollmentEntity);
        return enrollmentEntity.getId();
    }

}
