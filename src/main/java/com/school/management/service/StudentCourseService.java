package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.model.dto.CourseDto;
import com.school.management.model.dto.StudentDto;
import com.school.management.repository.CourseRepository;
import com.school.management.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentCourseService {
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public StudentCourseService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<CourseDto> getCoursesFromStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student not found."));

        return student.getCourses().stream()
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getUpdatedAt(), course.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<StudentDto> getStudentCourses() {
        return studentRepository.findAll().stream()
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getAddress(), student.getCourses(), student.getCreatedAt(), student.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentDto insertCourseInStudent(Long id, List<Long> coursesId) {
        if (coursesId.size() > 5) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "A student dont have more than 5 courses");
        }

        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student not found."));

        List courses = new ArrayList<Course>();
        coursesId.stream().forEach(courseId -> {
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Course with id %d not found.", courseId))
            );
            if (course.getStudents().size() > 50) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, String.format("A course %s have 50 students.", course.getName()));
            }
            courses.add(course);

        });

        student.setCourses(courses);
        student.setUpdatedAt(Timestamp.from(Instant.now()));
        student = studentRepository.save(student);


        return new StudentDto(student.getId(), student.getName(), student.getAddress(), student.getCourses(), student.getCreatedAt(), student.getUpdatedAt());
    }
    public List<StudentDto> getStudentsFromCourse(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Course not found."));

        return course.getStudents().stream()
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getAddress(), student.getUpdatedAt(), student.getCreatedAt()))
                .collect(Collectors.toList());
    }
    public List<CourseDto> getCoursesStudents() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getStudents(), course.getCreatedAt(), course.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseDto insertStudentInCourse(Long id, List<Long> studentsId) {
        if (studentsId.size() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "A course don't have more than 50 students");
        }

        Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Course not found."));

        List students = new ArrayList<Student>();
        studentsId.stream().forEach(studentId -> {
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Student with id %d not found.", studentId))
            );
            if (student.getCourses().size() > 5) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, String.format("A students %s have 5 courses.", student.getName()));
            }
            students.add(student);

        });

        course.setStudents(students);
        course.setUpdatedAt(Timestamp.from(Instant.now()));
        course = courseRepository.save(course);


        return new CourseDto(course.getId(), course.getName(), course.getStudents(), course.getCreatedAt(), course.getUpdatedAt());
    }
}
