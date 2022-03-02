package com.anabneri.registrationschool.service;

import com.anabneri.registrationschool.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {

     Student save(Student any);


    Optional<Student> getByStudentId(Integer studentId);

    void delete(Student student);

    Student update(Student student);

    Page<Student> find(Student filter, Pageable pageRequest);

    Optional<Student> getStudentByRegistration(String registration);
}
