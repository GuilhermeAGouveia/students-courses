package com.school.management.model.dto;

import com.school.management.model.Student;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CourseDto {
    private Long id;
    private String name;
    private List<StudentDto> students;
    private Timestamp updatedAt;
    private Timestamp createdAt;

    public CourseDto(){}

    public CourseDto(String name) {
        Timestamp ts = Timestamp.from(Instant.now());
        this.id = 0L;
        this.name = name;
        this.createdAt = ts;
        this.updatedAt = ts;
    }

    public CourseDto(Long id, String name, Timestamp updatedAt, Timestamp createAt){
        this(name);
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createAt;
    }

    public CourseDto(Long id, String name, List<Student> students, Timestamp updatedAt, Timestamp createAt){
        this(id, name, updatedAt, createAt);

        this.students = students.stream()
                .map(student ->
                        new StudentDto(student.getId(), student.getName(), student.getAddress(), student.getUpdatedAt(), student.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
