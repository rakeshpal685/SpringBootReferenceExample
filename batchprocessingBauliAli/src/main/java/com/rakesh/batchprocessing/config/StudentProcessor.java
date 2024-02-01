package com.rakesh.batchprocessing.config;


import com.rakesh.batchprocessing.student.Student;
import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor implements ItemProcessor<Student,Student> {

    @Override
    public Student process(Student student) {
        //ALl the business logic goes here to transform teh data
        return student;
    }
}