package com.anabneri.registrationschool.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentDTO {

    private Integer studentId;

    @NotEmpty
    private String studentName;

    @NotEmpty
    private String dateOfRegistration;

    @NotEmpty
    private String registration;



}
