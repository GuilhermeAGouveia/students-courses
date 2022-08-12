package com.school.management.service;

import com.school.management.repository.CourseRepository;
import com.school.management.repository.StudentCourseRepository;
import com.school.management.repository.StudentRepository;

import java.util.List;

public class StudentCourseService {
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentCourseService(StudentCourseRepository studentCourseRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentCourseRepository = studentCourseRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }


    public  void insertCourseInStudent(Long studentId, List<Long> idCourses){
        idCourses.stream()
                .forEach(idCourse -> {
                    if (courseRepository.existsById(idCourse)) {

                    }

                });


    }
}
