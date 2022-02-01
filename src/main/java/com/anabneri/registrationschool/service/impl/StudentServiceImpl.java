package com.anabneri.registrationschool.service.impl;

import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.repository.StudentRepository;
import com.anabneri.registrationschool.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl  implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Student save(Student student) {
        return repository.save(student);
    }
}
