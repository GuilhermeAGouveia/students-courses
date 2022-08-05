package com.school.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(
            nullable = false
    )
    private Timestamp createdAt;

    @Column(
            nullable = false
    )
    private Timestamp updatedAt;

    public Course() {}
    public Course(Long id) {
        this.id = id;
    }

    public Course(String name, Timestamp updatedAt, Timestamp createdAt) {
        this.name = name;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Course(Long id, String name, Timestamp updatedAt, Timestamp createdAt){
        this(name, updatedAt, createdAt);
        this.id = id;
    }


}
