package com.anabneri.registrationschool.service;

import com.anabneri.registrationschool.model.entity.Student;

import java.util.Optional;

public interface StudentService {

     Student save(Student any);

    Optional<Student> getByStudentId(Integer studentId);
}
