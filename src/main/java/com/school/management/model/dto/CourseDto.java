package com.school.management.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
public class CourseDto {
    private Long id;
    private String name;
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
}
