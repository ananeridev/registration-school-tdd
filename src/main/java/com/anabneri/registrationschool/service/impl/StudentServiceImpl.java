package com.anabneri.registrationschool.service.impl;

import com.anabneri.registrationschool.exception.BusinessException;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.repository.StudentRepository;
import com.anabneri.registrationschool.service.StudentService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl  implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Student save(Student student) {
        if (repository.existsByRegistration(student.getRegistration())) {
            throw new BusinessException("Registration already created!");
        }

        return repository.save(student);
    }

    @Override
    public Optional<Student> getByStudentId(Integer studentId) {
        return this.repository.findById(studentId);
    }

    @Override
    public void delete(Student student) {

        if (student == null || student.getStudentId() == null) {
            throw new IllegalArgumentException("StudentId cant not be null");
        }
        this.repository.delete(student);
    }

    @Override
    public Student update(Student student) {
        if (student == null || student.getStudentId() == null) {
            throw new IllegalArgumentException("StudentId cant not be null");
        }
       return this.repository.save(student);
    }

    @Override
    public Page<Student> find(Student filter, Pageable pageRequest) {
        Example<Student> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        // bater nas propriedades de string tendo de bater com o inicio do parametro
                        // basta ter um pedaco da palavra que vai trazer
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<Student> getStudentByRegistration(String registration) {
        return null;
    }

}
