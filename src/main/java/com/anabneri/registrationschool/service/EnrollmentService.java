package com.anabneri.registrationschool.service;

import com.anabneri.registrationschool.model.EnrollmentDTO;
import com.anabneri.registrationschool.model.entity.Enrollment;

public interface EnrollmentService {

    Enrollment save(Enrollment enrollment);
}
