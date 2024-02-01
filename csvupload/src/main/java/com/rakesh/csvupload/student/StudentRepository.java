package com.rakesh.csvupload.student;

import com.rakesh.csvupload.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}