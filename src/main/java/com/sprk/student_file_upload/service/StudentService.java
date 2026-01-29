package com.sprk.student_file_upload.service;

import com.sprk.student_file_upload.dto.ReviewDto;
import com.sprk.student_file_upload.dto.StudentDto;
import com.sprk.student_file_upload.dto.StudentFileDto;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    StudentDto addStudent(@Valid StudentFileDto studentFileDto) throws IOException;

    List<StudentDto> getallStudent();

    StudentDto getById(String rollNostr);

    String deletedStudent(String rollNostr);

    Resource downloadFile(String rollnoStr)throws IOException;

    StudentDto updateStudent(String rollnoStr, ReviewDto reviewDto);

//    StudentDto updateStudent(String rollnoStr, StudentFileDto studentFileDto);
}
