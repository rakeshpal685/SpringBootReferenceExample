package com.rakesh.batchprocessing.student;

import com.rakesh.batchprocessing.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}