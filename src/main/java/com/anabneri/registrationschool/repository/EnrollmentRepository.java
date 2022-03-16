package com.anabneri.registrationschool.repository;

import com.anabneri.registrationschool.model.entity.Enrollment;
import com.anabneri.registrationschool.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndNotRegistrated(Student student);
}
