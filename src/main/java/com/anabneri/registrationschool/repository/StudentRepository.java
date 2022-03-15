package com.anabneri.registrationschool.repository;

import com.anabneri.registrationschool.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByRegistration(String registration);

    // query parameter
    Optional<Student> findByRegistration(String registration);
}
