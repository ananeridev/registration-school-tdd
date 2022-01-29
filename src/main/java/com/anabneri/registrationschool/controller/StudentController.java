package com.anabneri.registrationschool.controller;

import com.anabneri.registrationschool.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create() {

        Student student = new Student();
        student.setStudentId(99);
        student.setStudentName("Ana Neri");
        student.setDateOfRegistration("10/10/2021");

        return student;
    }

}
