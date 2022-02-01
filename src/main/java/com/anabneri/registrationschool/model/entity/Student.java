package com.anabneri.registrationschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer studentId;
    private String studentName;
    private String dateOfRegistration;
    private String registration;
}
