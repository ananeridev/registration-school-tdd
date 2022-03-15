package com.anabneri.registrationschool.service.impl;

import com.anabneri.registrationschool.model.entity.Enrollment;
import com.anabneri.registrationschool.repository.EnrollmentRepository;
import com.anabneri.registrationschool.service.EnrollmentService;

public class EnrollmentServiceImpl implements EnrollmentService {

    private EnrollmentRepository repository;

    public EnrollmentServiceImpl(EnrollmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        return repository.save(enrollment);
    }
}
