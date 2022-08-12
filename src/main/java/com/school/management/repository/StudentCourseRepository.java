package com.school.management.repository;

import com.school.management.model.StudentCourse;
import com.school.management.model.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
}
