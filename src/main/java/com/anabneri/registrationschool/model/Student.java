package com.anabneri.registrationschool.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Student {

    private int studentId;
    private String studentName;
    private String dateOfRegistration;


}
