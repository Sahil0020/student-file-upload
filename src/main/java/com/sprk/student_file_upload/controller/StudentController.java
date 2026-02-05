package com.sprk.student_file_upload.controller;

import com.sprk.student_file_upload.dto.ReviewDto;
import com.sprk.student_file_upload.dto.StudentDto;
import com.sprk.student_file_upload.dto.StudentFileDto;
import com.sprk.student_file_upload.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/student")
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentDto>> getallStudent(){
        List<StudentDto> exitsStudents=studentService.getallStudent();
        return ResponseEntity.status(200).body(exitsStudents);
    }

    @GetMapping("/student/{rollNo}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<StudentDto> getById(@PathVariable("rollNo") String rollNostr){
        StudentDto studentDto=studentService.getById(rollNostr);

        return ResponseEntity.status(200).body(studentDto);
    }

    @DeleteMapping("/student/{rollNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStudent(@PathVariable("rollNo") String rollNostr)
    {
        String studentDto=studentService.deletedStudent(rollNostr);

        return ResponseEntity.status(200).body(studentDto);
    }
    @GetMapping("/student/download/{rollNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource>  downloadInfo(@PathVariable("rollNo") String rollnoStr)throws IOException{
        Resource resource=studentService.downloadFile(rollnoStr);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"").body(resource);

    }

    @PutMapping("/teacher/reject/{rollNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("rollNo") String rollnoStr, @RequestBody ReviewDto reviewDto){
        StudentDto student=studentService.updateStudent(rollnoStr,reviewDto);
        return ResponseEntity.status(200).body(student);
    }
    @PutMapping("/teacher/approved/{rollNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> approvedStudent(@PathVariable("rollNo") String rollnoStr, @RequestBody ReviewDto reviewDto){
        StudentDto student=studentService.approvedStudent(rollnoStr,reviewDto);
        return ResponseEntity.status(200).body(student);
    }

    @PutMapping("/student/resubmit/{rollNo}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StudentDto> uploadFile(@PathVariable("rollNo") String rollnoStr, @RequestParam MultipartFile file)throws IOException{
        StudentDto studentDto=studentService.uploadFile(rollnoStr,  file);
        return ResponseEntity.status(200).body(studentDto);
    }

}
