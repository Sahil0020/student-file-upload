package com.sprk.student_file_upload.dto;

import com.sprk.student_file_upload.entity.StatusReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String review;
    private StatusReview status;
}
