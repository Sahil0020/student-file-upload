package com.sprk.student_file_upload.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentFileDto {

    private Long rollNo;
    @NotBlank(message = "First Name cannot be empty")
    private String firstName;

    @NotBlank(message = "First Name cannot be empty")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Phone cannot be empty")
    private String phone;

    @NotNull(message = "File cannot be empty")
    private MultipartFile file;
//
//    @NotBlank(message = "review cannot be empty")
//    private String review;
//
//    @NotBlank(message = "Status cannot be empty")
//    private String status;
}
