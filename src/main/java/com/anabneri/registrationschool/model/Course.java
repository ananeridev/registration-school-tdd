package com.anabneri.registrationschool.model;


import com.anabneri.registrationschool.domain.CourseTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Course {

    private String courseId;
    private CourseTypeEnum courseTypeEnum;


}
