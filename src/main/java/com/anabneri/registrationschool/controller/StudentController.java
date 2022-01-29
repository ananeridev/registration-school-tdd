package com.anabneri.registrationschool.controller;

import com.anabneri.registrationschool.model.StudentDTO;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO create( @RequestBody StudentDTO studentDTO ) {

        Student entity = Student.builder()
                .studentName(studentDTO.getStudentName())
                .dateOfRegistration(studentDTO.getDateOfRegistration()).build();

        entity = studentService.save(entity);

        return StudentDTO.builder()
                .studentId(entity.getStudentId())
                .studentName(entity.getStudentName())
                .dateOfRegistration(entity.getDateOfRegistration()).build();
    }

}
