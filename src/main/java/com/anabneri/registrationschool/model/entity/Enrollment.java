package com.anabneri.registrationschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {

    private long id;
    private String course;
    private Student student;
    private LocalDate enrollmentDate;

}
