package com.sprk.student_file_upload.mapper;

import com.sprk.student_file_upload.dto.StudentDto;
import com.sprk.student_file_upload.dto.StudentFileDto;
import com.sprk.student_file_upload.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

//    StudentFileDto mapInfotoDto(Student student);
    Student mapDtotoInfo(StudentFileDto studentFileDto);
    StudentDto mapstudentInfotostudentDto(Student student);
    Student mapstudDtotoInfo(
            StudentDto studentDto
    );
}
