package com.anabneri.registrationschool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EnrollmentDTO {

    private String studentRegistration;
    private String course;
}
