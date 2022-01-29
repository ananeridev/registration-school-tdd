package com.anabneri.registrationschool.controller;

import com.anabneri.registrationschool.model.StudentDTO;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    private StudentService studentService;
    private ModelMapper modelMapper;

    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO create( @RequestBody StudentDTO studentDTO ) {

        Student entity =  modelMapper.map(studentDTO, Student.class);
        entity = studentService.save(entity);

        return modelMapper.map(entity, StudentDTO.class);
    }

}
