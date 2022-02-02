package com.anabneri.registrationschool.controller;

import com.anabneri.registrationschool.controller.exceptions.ApiErrors;
import com.anabneri.registrationschool.exception.BusinessException;
import com.anabneri.registrationschool.model.StudentDTO;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

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
    public StudentDTO create( @RequestBody @Valid StudentDTO studentDTO ) {

        Student entity =  modelMapper.map(studentDTO, Student.class);
        entity = studentService.save(entity);

        return modelMapper.map(entity, StudentDTO.class);
    }


    @GetMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO get( @PathVariable Integer studentId ) {

        return studentService
                .getByStudentId(studentId)
                .map(student ->  modelMapper.map(student, StudentDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByStudentId(@PathVariable Integer studentId) {
        Student student = studentService.getByStudentId(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        studentService.delete(student);
    }


    @PutMapping("{studentId}")
    public StudentDTO update(@PathVariable Integer studentId, StudentDTO studentDTO) {
       return studentService.getByStudentId(studentId).map(student -> {
            student.setStudentName(studentDTO.getStudentName());
            student.setDateOfRegistration(studentDTO.getDateOfRegistration());
            student =  studentService.update(student);
            return modelMapper.map(student, StudentDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidateException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        return new ApiErrors(bindingResult);
    }


    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException e) {

        return new ApiErrors(e);
    }
}
