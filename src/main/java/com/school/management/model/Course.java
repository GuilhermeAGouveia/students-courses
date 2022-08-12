package com.school.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    public Course() {}
    public Course(Long id) {
        this.id = id;
    }

    public Course(String name, List<Student> students, Timestamp updatedAt, Timestamp createdAt) {
        this.name = name;
        this.students = students;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Course(String name, Timestamp updatedAt, Timestamp createdAt) {
        this(name, null, updatedAt, createdAt);
    }


    public Course(Long id, String name, Timestamp updatedAt, Timestamp createdAt){
        this(name, null, updatedAt, createdAt);
        this.id = id;
    }
    public Course(Long id, String name, List<Student> students, Timestamp updatedAt, Timestamp createdAt){
        this(name, students, updatedAt, createdAt);
        this.id = id;
    }



}
