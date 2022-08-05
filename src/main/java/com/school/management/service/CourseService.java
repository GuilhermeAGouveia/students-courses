package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.model.dto.CourseDto;
import com.school.management.repository.CourseRepository;
import com.school.management.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseDto getCourse(Long id) {
        Course c =  courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found."));

        return new CourseDto(c.getId(), c.getName(), c.getUpdatedAt(), c.getCreatedAt());
    }

    public List<CourseDto> getCourses(Boolean withoutCourse){
        return courseRepository.findAll().stream()
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getUpdatedAt(), course.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<CourseDto> createCourses(List<CourseDto> coursesDto) {
        if (coursesDto.size() > 50) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "A request can not contain more than 50 courses.");
        }

        Timestamp ts = Timestamp.from(Instant.now());

        List<Course> l = courseRepository.saveAll(coursesDto.stream()
                        .filter(courseDto -> courseDto.getName() != null && !courseDto.getName().isBlank())
                        .map(courseDto -> new Course(courseDto.getName(), ts, ts))
                        .collect(Collectors.toList())
                );

        return l.stream()
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getUpdatedAt(), course.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllCourses(Boolean confirmDeletion){
        if (confirmDeletion) {
            courseRepository.deleteAll();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "To delete ALL students and students-courses relationships, inform confirm-deletion=true as a query param.");
        }
    }

    @Transactional
    public void deleteCourse(Long id, Boolean confirmDeletion) {
        if (confirmDeletion) {
            Course c = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not %d found"));
            courseRepository.delete(c);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "To delete students and students-courses relationships, inform confirm-deletion=true as a query param."
            );
        }
    }


}
