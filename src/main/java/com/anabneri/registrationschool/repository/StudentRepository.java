package com.anabneri.registrationschool.repository;

import com.anabneri.registrationschool.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByRegistration(String registration);
}
