package com.anabneri.registrationschool.repository;

import com.anabneri.registrationschool.model.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
