package com.anabneri.registrationschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Student {

    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "date_of_registration")
    private String dateOfRegistration;

    @Column
    private String registration;
}
