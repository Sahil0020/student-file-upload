package com.sprk.student_file_upload.controller;

import com.sprk.student_file_upload.dto.ReviewDto;
import com.sprk.student_file_upload.dto.StudentDto;
import com.sprk.student_file_upload.dto.StudentFileDto;
import com.sprk.student_file_upload.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<StudentDto> addStudent(@Valid @ModelAttribute StudentFileDto studentFileDto){
try {

       StudentDto studentDto= studentService.addStudent(studentFileDto);
       return ResponseEntity.status(200).body(studentDto);
} catch (IOException e) {
    System.out.println(e.getMessage());
}
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentDto>> getallStudent(){
        List<StudentDto> exitsStudents=studentService.getallStudent();
        return ResponseEntity.status(200).body(exitsStudents);
    }

    @GetMapping("/student/{rollNo}")
    public ResponseEntity<StudentDto> getById(@PathVariable("rollNo") String rollNostr){
        StudentDto studentDto=studentService.getById(rollNostr);

        return ResponseEntity.status(200).body(studentDto);
    }

    @DeleteMapping("/student/{rollNo}")
    public ResponseEntity<String> deleteStudent(@PathVariable("rollNo") String rollNostr)
    {
        String studentDto=studentService.deletedStudent(rollNostr);

        return ResponseEntity.status(200).body(studentDto);
    }
    @GetMapping("/student/download/{rollNo}")
    public ResponseEntity<Resource>  downloadInfo(@PathVariable("rollNo") String rollnoStr)throws IOException{
        Resource resource=studentService.downloadFile(rollnoStr);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"").body(resource);

    }

    @PutMapping("/student/{rollNo}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("rollNo") String rollnoStr, @RequestBody ReviewDto reviewDto){
        StudentDto student=studentService.updateStudent(rollnoStr,reviewDto);
        return ResponseEntity.status(200).body(student);

    }
}
