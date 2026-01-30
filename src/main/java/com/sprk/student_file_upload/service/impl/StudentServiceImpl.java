package com.sprk.student_file_upload.service.impl;

import com.sprk.student_file_upload.dto.ReviewDto;
import com.sprk.student_file_upload.dto.StudentDto;
import com.sprk.student_file_upload.dto.StudentFileDto;
import com.sprk.student_file_upload.entity.StatusReview;
import com.sprk.student_file_upload.entity.Student;
import com.sprk.student_file_upload.mapper.StudentMapper;
import com.sprk.student_file_upload.repository.StudentRespository;
import com.sprk.student_file_upload.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRespository studentRespository;
    private final StudentMapper studentMapper;
    @Value("${file.upload-dir}")
    private String uploadDirectory;

    @Override
    @Transactional
    public StudentDto addStudent(StudentFileDto studentFileDto) throws IOException {
        if (studentRespository.existsByEmail(studentFileDto.getEmail())) {
            throw new RuntimeException(" Email already exits");
        }

        if (studentRespository.existsByPhone(studentFileDto.getPhone())) {
            throw new RuntimeException("Phone already exits");
        }
        String filename = studentFileDto.getFile().getOriginalFilename();

        Student student = studentMapper.mapDtotoInfo(studentFileDto);
        student.setFileName(filename);
        student.setReview("Under Review");
        student.setStatus(StatusReview.SUBMITTED);
        student.setFilePath(uploadDirectory + "/" + filename);

        File dir = new File(uploadDirectory);
        File destination = new File(dir, filename);
        studentFileDto.getFile().transferTo(destination);
        Student savedStudent = studentRespository.save(student);
        StudentDto studentDto = studentMapper.mapstudentInfotostudentDto(student);
        return studentDto;

    }

    @Override
    public List<StudentDto> getallStudent() {
        List<Student> student = studentRespository.findAll();
        return student.stream().map(studentMapper::mapstudentInfotostudentDto).collect(Collectors.toList());
    }

    @Override
    public StudentDto getById(String rollNostr) {
        if (!Pattern.matches("^\\d+$", rollNostr)) {
            throw new RuntimeException("Student with id Not found");
        }
        Long rollno = Long.parseLong(rollNostr);
        Student student = studentRespository.findById(rollno).orElseThrow(() -> new RuntimeException("Student Not found"));

        return studentMapper.mapstudentInfotostudentDto(student);
    }

    @Override
    public String deletedStudent(String rollNostr) {
        if (!Pattern.matches("^\\d+$", rollNostr)) {
            throw new RuntimeException("Student with id Not found");
        }
        Long rollno = Long.parseLong(rollNostr);
        Student student = studentRespository.findById(rollno).orElseThrow(() -> new RuntimeException("Student Not found"));
        studentRespository.delete(student);
        return "Student deleted successfully";

    }

    @Override
    public Resource downloadFile(String rollnoStr) throws IOException {

        if (!Pattern.matches("^\\d+$", rollnoStr)) {
            throw new RuntimeException("Student with id Not found");
        }
        Long rollno = Long.parseLong(rollnoStr);
        Student student = studentRespository.findById(rollno).orElseThrow(() -> new RuntimeException("Student Not found"));

        Path path = Paths.get(uploadDirectory, student.getFileName());
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) {
            ResponseEntity.notFound().build();
        }

        return resource;
    }

    @Override
    public StudentDto updateStudent(String rollnoStr, ReviewDto reviewDto) {
                if (!Pattern.matches("^\\d+$", rollnoStr)) {
            throw new RuntimeException("Student with id Not found");
        }
        Long rollno=Long.parseLong(rollnoStr);
        Student student= studentRespository.findById(rollno).orElseThrow(()-> new RuntimeException("Student Not found"));
        student.setReview(reviewDto.getReview());
        student.setStatus(StatusReview.REJECTED);
        studentRespository.save(student);
        return studentMapper.mapstudentInfotostudentDto(student);
    }

    @Override
    public StudentDto uploadFile(String rollnoStr, MultipartFile file) throws IOException{
        if (!Pattern.matches("^\\d+$", rollnoStr)) {
            throw new RuntimeException("Student with id Not found");
        }
        Long rollno=Long.parseLong(rollnoStr);
        Student student= studentRespository.findById(rollno).orElseThrow(()-> new RuntimeException("Student Not found"));
        student.setFileName(file.getOriginalFilename());
        student.setFilePath(uploadDirectory + "/" + file.getOriginalFilename());
        File dir = new File(uploadDirectory);
        File destination = new File(dir, file.getOriginalFilename());
        file.transferTo(destination);
        student.setStatus(StatusReview.RESUBMITTED);
        student.setReview("Re-submitted assigenement");
        studentRespository.save(student);
        return studentMapper.mapstudentInfotostudentDto(student);
    }

    @Override
    public StudentDto approvedStudent(String rollnoStr, ReviewDto reviewDto) {
        if (!Pattern.matches("^\\d+$", rollnoStr)) {
            throw new RuntimeException("Student with id Not found");
        }
        Long rollno=Long.parseLong(rollnoStr);
        Student student= studentRespository.findById(rollno).orElseThrow(()-> new RuntimeException("Student Not found"));
        student.setReview(reviewDto.getReview());
        student.setStatus(StatusReview.APPROVED);
        studentRespository.save(student);
        return studentMapper.mapstudentInfotostudentDto(student);
    }
}



