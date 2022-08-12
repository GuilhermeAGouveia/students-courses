package com.school.management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "course_student",
			joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id"))
	private List<Course> courses = new ArrayList<>();


	private Timestamp createdAt;
	private Timestamp updatedAt;


	public Student() {
	}

	public Student(Long id) {
		this.id = id;
	}

	public Student(String name, String address, List <Course> courses, Timestamp createdAt, Timestamp updatedAt) {
		this.name = name;
		this.address = address;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.courses = courses;
	}

	public Student(String name, String address, Timestamp createdAt, Timestamp updatedAt) {
		this.name = name;
		this.address = address;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Student(Long id, String name, String address, List <Course> courses, Timestamp createdAt, Timestamp updatedAt) {
		this(name, address, courses, createdAt, updatedAt);
		this.id = id;
	}

	public Student(Long id, String name, String address, Timestamp createdAt, Timestamp updatedAt) {
		this(name, address, createdAt, updatedAt);
		this.id = id;
	}

}
