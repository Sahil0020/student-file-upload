package com.sprk.student_file_upload.repository;

import com.sprk.student_file_upload.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRespository extends JpaRepository<Student,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}
