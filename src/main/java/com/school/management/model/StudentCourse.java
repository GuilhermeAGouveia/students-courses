package com.school.management.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@IdClass(StudentCourseId.class)
public class StudentCourse {
    @Id
    private Long studentId;
    @Id
    private Long courseId;

    private Timestamp createdAt;
}
