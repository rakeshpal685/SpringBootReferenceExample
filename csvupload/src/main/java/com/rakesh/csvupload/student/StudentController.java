package com.rakesh.csvupload.student;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService service;

  @PostMapping(
      value = "/upload",
      consumes = {"multipart/form-data"})
  public ResponseEntity<Integer> uploadStudents(@RequestPart("file") MultipartFile file)
      throws IOException {
    return ResponseEntity.ok(service.uploadStudents(file));
  }
}
